package com.jesega.kafka.workingqueue.consumers.processors;

public abstract class Procesador {
	public abstract String process(String value);
	
	public int getNumber(String value) {
		return Integer.parseInt(value.split(" \n", 2)[0]);
	}
}
