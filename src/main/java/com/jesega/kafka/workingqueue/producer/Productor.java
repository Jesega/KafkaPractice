package com.jesega.kafka.workingqueue.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class Productor {

	private Properties props;
	private Producer<String, String> producer;

	public Productor() {
		props = new Properties();
		configProperties();
		producer = new KafkaProducer<>(props);
	}

	private void configProperties() {
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	}

	public void publish(String topic, String value) {
		producer.send(new ProducerRecord<>(topic, value));
		producer.flush();
	}
	
	public void close() {
		producer.close();
	}

}
