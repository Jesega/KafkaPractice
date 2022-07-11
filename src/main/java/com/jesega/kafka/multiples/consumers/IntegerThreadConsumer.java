package com.jesega.kafka.multiples.consumers;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.jesega.kafka.multiples.producer.IntegerProducer;

public class IntegerThreadConsumer extends Thread {

	private int n;
	private final KafkaConsumer<String, String> consumer;
	private final AtomicBoolean closed = new AtomicBoolean(false);

	public IntegerThreadConsumer(KafkaConsumer<String, String> consumer, int n) {
		this.consumer = consumer;
		this.n = n;
	}

	@Override
	public void run() {
		consumer.subscribe(Arrays.asList("jesega-topic"));
		try {
			while (!closed.get()) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				int m = 0;
				for (ConsumerRecord<String, String> record : records)
				{
					m = Integer.parseInt(record.value());
					if( (m%n) == 0)
						System.out.println(record.value() + " es multiplo de " + n);
					else
						System.out.println(record.value() + " no es multiplo de " + n);
				}
			}
//			IntegerProducer p = new IntegerProducer();
//			p.publish(getName(), getName());
		} catch (WakeupException e) {
			if (!closed.get())
				throw e;
		} finally {
			consumer.close();
		}
	}

//	public void shutdown() {
//		closed.set(true);
//		consumer.wakeup();
//	}

}
