package com.jesega.kafka.multiples.consumers;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class IntegerMultithreadConsumer {
	static int nThreads = 3;
	
	public static void main(String[] args) {
		//Properties configuration
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("enable.auto.commit", "true");
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		
		int array[] = {2,3,5};
		for (int i = 0; i < nThreads; i++) {
			//A group for each thread
			props.setProperty("group.id", "jesega-group-" + i);
			IntegerThreadConsumer consumer = new IntegerThreadConsumer(new KafkaConsumer<String, String>(props), array[i]);
			executor.execute(consumer);
		}
		while (!executor.isTerminated());
	}
}
