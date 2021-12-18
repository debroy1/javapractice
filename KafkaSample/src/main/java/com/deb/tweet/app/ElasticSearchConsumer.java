package com.deb.tweet.app;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.google.gson.JsonParser;

public class ElasticSearchConsumer {
	private static final String BOOTSTRAP_SERVER = "localhost:9092";
	private static final String TWITTER_TOPIC = "twitter_tweets";
	private static final String TWITTER_GROUP = "twitter_group_elastic";
	private static final Duration POLL_DURATION = Duration.ofSeconds(5);
	private static final long SLEEP_TIME_MILLISEC = 1000;
	private static final int MAX_RECORDS_PER_POLL = 5;
	private static final String TWITTER_INDEX = "twitter";

	public static void main(String[] args) throws IOException, InterruptedException {
		RestHighLevelClient elasticClient = getElasticClient();
		KafkaConsumer<String, String> kafkaConsumer = createKafkaConsumer();
		
		// read records from kafka and send them to elastic search
		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(POLL_DURATION);
			System.out.println("Received " + records.count() + " records");
			for (ConsumerRecord<String, String> record : records) {
				String message = record.value();
				// Identify or form a key so that the message can be identified as unique and elastic search can handle it as idempotent
				String uniqueId = extractIdFromTweet(message);
				//String uniqueId = record.topic() + "-" + record.partition() + "-" + record.offset();
				System.out.println("Sending to elastic. Id: " + uniqueId);
				
				// Index request to send the message along with an id - to make elastic search as idempotent, not to add same message again but to update with version id
				IndexRequest request = new IndexRequest(TWITTER_INDEX).id(uniqueId).source(message, XContentType.JSON);
				IndexResponse response = elasticClient.index(request, RequestOptions.DEFAULT);
				String elasticId = response.getId();
				System.out.println("Sent to elastic. Id: " + uniqueId + " received Elastic Id: " + elasticId);
				// FIXME - remove below
				Thread.sleep(SLEEP_TIME_MILLISEC);
			}
			System.out.println("About to commit offsets");
			kafkaConsumer.commitSync();
			System.out.println("Offsets are committed");
			Thread.sleep(SLEEP_TIME_MILLISEC * 10);
		}
		
		//elasticClient.close();
	}
	
	private static RestHighLevelClient getElasticClient() {
		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
	
	private static KafkaConsumer<String, String> createKafkaConsumer() {
		// set consumer properties
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, TWITTER_GROUP); // group for consumer - elastic
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // latest, earliest, none
		properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // disable auto commit
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, String.valueOf(MAX_RECORDS_PER_POLL)); // maximum number of records in a poll
		
		// create consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		// subscribe consumer to a topic or an array of topics
		consumer.subscribe(Collections.singleton(TWITTER_TOPIC));
		
		return consumer;
	}
	
	private static String extractIdFromTweet(String tweetJson) {
		// gson library
		return JsonParser.parseString(tweetJson).getAsJsonObject().get("id_str").getAsString();		
	}
	
}
