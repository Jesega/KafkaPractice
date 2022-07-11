package com.jesega.kafka.workingqueue.consumers.processors;

import java.util.Collections;
import java.util.Vector;

public class Multiplos extends Procesador {

	@Override
	public String process(String value) {
		int n = this.getNumber(value);

		Vector<Integer> v = getMultiples(n);
		
		StringBuilder result = new StringBuilder(value);
		result.append("\n sus multiplos son ");
		for(Integer i: v)
			result.append(i + " ");
		
		return result.toString();
	}
	
	private Vector<Integer> getMultiples(int n) {
		Vector<Integer> v = new Vector<Integer>();
		for (int i = 1; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				v.add(i);
				if (n / i != i)
					v.add(n / i);
			}
		}
		Collections.sort(v);
		return v;
	}

}
