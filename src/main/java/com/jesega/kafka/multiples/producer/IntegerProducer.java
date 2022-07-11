package com.jesega.kafka.multiples.producer;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerProducer {
	static Random random = new Random();
	
	public static void main(String[] args) {

		final Logger log = LoggerFactory.getLogger(IntegerProducer.class);

		//Properties configuration
		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		int n = random.nextInt(1000);
		try (Producer<String, String> producer = new KafkaProducer<>(props);) { //Try with resources
				producer.send(new ProducerRecord<>("jesega-topic", "Entero ", 
						String.valueOf(n))).get();
			System.out.println("El entero es " + n);
			producer.flush();
		} catch (Exception e) {
			log.error("Message producer interrupted " + e);
		}
	}

}
