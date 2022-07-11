package com.jesega.kafka.workingqueue.main;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import com.jesega.kafka.workingqueue.consumers.Consumidor;
import com.jesega.kafka.workingqueue.consumers.processors.Multiplos;
import com.jesega.kafka.workingqueue.consumers.processors.Palindromic;
import com.jesega.kafka.workingqueue.consumers.processors.PerfectSquare;
import com.jesega.kafka.workingqueue.producer.Productor;

public class Main {
	private static Productor prod;
	private static ArrayList<Consumidor> consumidores;
	private static Random random = new Random();

	public static void setUp() {
		Main.prod = new Productor();
		Main.consumidores = new ArrayList<Consumidor>();
		
		consumidores.add(new Consumidor("first-group", "multiples", new Multiplos(), prod, Optional.of("square")));
		consumidores.add(new Consumidor("second-group", "square", new PerfectSquare(), prod, Optional.of("palindromic")));
		consumidores.add(new Consumidor("third-group", "palindromic", new Palindromic(), prod, Optional.empty()));
	}

	public static void main(String[] args) {
		setUp();
		prod.publish("multiples", String.valueOf(random.nextInt(1000)));
		//prod.publish("multiples", String.valueOf(36));
		//prod.publish("multiples", String.valueOf(636));
		for(Consumidor c : consumidores)
		{
			c.poll();
			c.process();
			c.close();
		}
		prod.close();
	}
}
