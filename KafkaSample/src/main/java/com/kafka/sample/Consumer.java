package com.kafka.sample;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.deb.tweet.app.StopTimer;

public class Consumer {
	private static final String BOOTSTRAP_SERVER = "localhost:9092";
	private static final String FIRST_TOPIC = "first_topic";
	private static final Duration POLL_DURATION = Duration.ofSeconds(5);
	private static KafkaConsumer<String, String> consumer;
	
	public static void main(String[] args) {		
		// invoke appropriate consumer
		consumerWithSubscription();
		//consumerWithAssignAndSeek();
	}

	static void consumerWithSubscription() {
		// set consumer properties
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "first-consumer-group");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //latest, earliest, none

		// create consumer
		consumer = new KafkaConsumer<String, String>(properties);
		// subscribe consumer to a topic
		consumer.subscribe(Collections.singleton(FIRST_TOPIC));
		
		// Add a shutdown hook for proper shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down Kafka consumer...");
			consumer.close();
			System.out.println("Shutdown complete!");
		}));
		
		// poll for new messages and wait for timer to expire
		StopTimer timer = new StopTimer(POLL_DURATION);
		timer.start();
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(POLL_DURATION);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("Received message -"
						+ " Key: " + record.key()
						+ " Value: " + record.value()
						+ " Partition: " + record.partition()
						+ " Offset: " + record.offset()
						+ " Timestamp: " + record.timestamp());
			}
		}
	}
	
	// assign and seek to replay messages from specific partition and offset
	static void consumerWithAssignAndSeek() {
		// set consumer properties
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //latest, earliest, none

		// create consumer
		consumer = new KafkaConsumer<String, String>(properties);
		
		// assign
		TopicPartition partitionToReadFrom = new TopicPartition(FIRST_TOPIC, 2);
		consumer.assign(Arrays.asList(partitionToReadFrom));
		long offsetToReadFrom = 5l;
		
		// seek
		consumer.seek(partitionToReadFrom, offsetToReadFrom);
		
		// Add a shutdown hook for proper shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down Kafka consumer...");
			consumer.close();
			System.out.println("Shutdown complete!");
		}));
		
		// poll for new messages and wait for timer to expire
		StopTimer timer = new StopTimer(POLL_DURATION);
		timer.start();
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(POLL_DURATION);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("Received message -"
						+ " Key: " + record.key()
						+ " Value: " + record.value()
						+ " Partition: " + record.partition()
						+ " Offset: " + record.offset()
						+ " Timestamp: " + record.timestamp());
			}
		}
	}
}
