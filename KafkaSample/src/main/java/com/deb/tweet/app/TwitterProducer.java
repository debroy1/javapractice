package com.deb.tweet.app;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static com.deb.tweet.app.Util.*;

public class TwitterProducer {
	private static final Duration POLL_DURATION = Duration.ofSeconds(10);

	public static void main(String[] args) {
		// load all config from properties using util class
		Util.loadProperties();

		// get input topics as args or else pass default values
		if(args != null && args.length > 0) {
			getTwits(Arrays.asList(args));			
		} else {
			String[] searchTopics = { "kafka", "springboot", "india" };
			getTwits(Arrays.asList(searchTopics));
		}
	}

	static void getTwits(List<String> searchTopics) {
		// Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(10000);
		// BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

		// Create the Twitter client
		List<String> terms = Lists.newArrayList(searchTopics);
		//List<Long> followings = Lists.newArrayList(1234L, 566788L);// optional
		Client twitterClient = createTwitterClient(msgQueue, terms, null);
	
		// Create the Kafka Producer
		KafkaProducer<String, String> kafkaProducer = createKafkaProducer();

		// Attempts to establish a connection
		twitterClient.connect();

		// Add a shutdown hook for proper shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down Twitter client...");
			twitterClient.stop();
			System.out.println("Shutting down Kafka producer...");
			kafkaProducer.close();
			System.out.println("Shutdown complete!");
		}));

		// On a different thread, or multiple different threads....
		// poll for new messages and wait for timer to expire
		StopTimer timer = new StopTimer(POLL_DURATION);
		timer.start();
		
		// Start getting tweets
		while (!twitterClient.isDone()) {
			try {
				String msg = msgQueue.poll(5, TimeUnit.SECONDS);
				if(msg != null && !msg.isEmpty()) {
					System.out.println("Sending message: " + msg);
					ProducerRecord<String, String> record = new ProducerRecord<String, String>(TWITTER_TOPIC, msg);
					kafkaProducer.send(record, new Callback() {
						public void onCompletion(RecordMetadata metadata, Exception exception) {
							if(metadata != null) {
								System.out.println("Sent successful! Received metadata -" 
										+ " Topic: " + metadata.topic()
										+ " Partition: " + metadata.partition()
										+ " Offset: " + metadata.offset()
										+ " Timestamp: " + metadata.timestamp());
							} else {
								System.out.println("Exception occurred: " + exception);
							}
						}
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		twitterClient.stop();
	}
	
	static Client createTwitterClient(BlockingQueue<String> msgQueue, List<String> terms, List<Long> followings) {
		// Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

		// Optional: set up some followings and track terms
		if(followings != null && followings.size() > 0) {
			hosebirdEndpoint.followings(followings);
		}
		if(terms != null && terms.size() > 0) {
			hosebirdEndpoint.trackTerms(terms);
		}

		// These secrets should be read from a config file
		Authentication hosebirdAuth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, SECRET);

		ClientBuilder builder = new ClientBuilder()
				.name("Hosebird-Client-debtweetapp") // optional: mainly for the logs
				.hosts(hosebirdHosts)
				.authentication(hosebirdAuth)
				.endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue));
		// .eventMessageQueue(eventQueue); // optional: use this if you want to process client events

		Client hosebirdClient = builder.build();
		return hosebirdClient;
	}

	static void usingTwit4J() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(CONSUMER_KEY)
		.setOAuthConsumerSecret(CONSUMER_SECRET)
		.setOAuthAccessToken(TOKEN)
		.setOAuthAccessTokenSecret(SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Query query = new Query("india");
		try {
			QueryResult result = twitter.search(query);
			List<Status> results = result.getTweets();
			for(Status status : results) {
				System.out.println(status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	static KafkaProducer<String, String> createKafkaProducer() {
		// set producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// for safe producer
		properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
		properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
		properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
		
		// for high-throughput producer
		properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
		properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));// 32 kb batch size
		
		// create kafka producer
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
		return kafkaProducer;
	}
}
