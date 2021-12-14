package com.kafka.sample;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {
	private static final String BOOTSTRAP_SERVER = "localhost:9092";
	private static final String FIRST_TOPIC = "first_topic";

	public static void main(String[] args) {
		// create producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		// create a producer record without any key
		//ProducerRecord<String, String> record = new ProducerRecord<String, String>(FIRST_TOPIC, "Message with id");
		// send record using producer - asynchronous without callback
		//producer.send(record);

		// create a producer record with key
		ProducerRecord<String, String> recordWithKey = new ProducerRecord<String, String>(FIRST_TOPIC, "id_1", "Message with id");
		// send record using producer - asynchronous with callback
		producer.send(recordWithKey, new Callback() {
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if(metadata != null) {
					System.out.println("Received metadata -" 
							+ " Topic: " + metadata.topic()
							+ " Partition: " + metadata.partition()
							+ " Offset: " + metadata.offset()
							+ " Timestamp: " + metadata.timestamp());
				} else {
					System.out.println("Exception occurred: " + exception);
				}
			}
		});
		
		// flush and close producer
		producer.flush();
		producer.close();
	}
}
