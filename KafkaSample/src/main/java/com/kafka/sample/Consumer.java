package com.kafka.sample;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class Consumer {
	private static final String BOOTSTRAP_SERVER = "localhost:9092";
	private static final String FIRST_TOPIC = "first_topic";
	private static final Duration POLL_DURATION = Duration.ofSeconds(5);
	private static KafkaConsumer<String, String> consumer;
	
	public static void main(String[] args) {
		// create consumer properties
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "first-consumer-group");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//latest, earliest, none
		
		consumer = new KafkaConsumer<String, String>(properties);
		
		// subscribe consumer to a topic
		consumer.subscribe(Collections.singleton(FIRST_TOPIC));
		
		// poll for new messages
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

class StopTimer extends Thread {
	long delay;
	public StopTimer(Duration duration) {
		this.delay = duration.toMillis();
		this.setDaemon(true);
	}
	@Override
	public synchronized void start() {
	    TimerTask task = new TimerTask() {
	        public void run() {
	        	System.err.println("Poll duration is over - shutting down!");
	        	System.exit(MAX_PRIORITY);
	        }
	    };
	    Timer timer = new Timer();
	    timer.schedule(task, delay);
	}
}