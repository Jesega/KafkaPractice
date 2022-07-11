package com.jesega.kafka.workingqueue.consumers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.jesega.kafka.workingqueue.consumers.processors.Procesador;
import com.jesega.kafka.workingqueue.producer.Productor;

public class Consumidor {
	private Properties props;
	private Consumer<String, String> consumer;
	private ConsumerRecords<String, String> records;
	private Procesador procesador;
	private Optional<String> nextTopic;
	private Productor prod;
	
	
	public Consumidor(String groupId, String topic, Procesador procesador, Productor prod, Optional<String> nextTopic) {
		this.procesador = procesador;
		this.nextTopic = nextTopic;
		this.prod = prod;
		this.props = new Properties();
		configProperties(groupId);
		this.consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(topic));
	}

	private void configProperties(String groupId) {
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("enable.auto.commit", "true");
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("group.id", groupId);
	}
	
	public void poll() {
		records = consumer.poll(Duration.ofMillis(100));
	}
	
	public void process() {
		String msg;
		for(ConsumerRecord<String, String> r : records)
		{
			msg = procesador.process(r.value());
			if(this.nextTopic.isPresent())
				prod.publish(nextTopic.get(), msg);
			else
				System.out.println(msg);
		}	
	}
	
	public void close() {
		this.consumer.close();
	}
}
