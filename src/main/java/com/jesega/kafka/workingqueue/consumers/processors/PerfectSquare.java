package com.jesega.kafka.workingqueue.consumers.processors;

public class PerfectSquare extends Procesador {

	@Override
	public String process(String value) {
		int n = this.getNumber(value);
		StringBuilder sb = new StringBuilder(value);
		
		int r = findPerfectSquare(n);
		if(r == 0)
			sb.append(" \n no es un cuadrado perfecto");
		else
			sb.append(" \n es el cuadrado de " + r);
		
		return sb.toString();
	}
	
	//Returs 0 if is not a perfect square
	private int findPerfectSquare(int n) {
		boolean isPerfectSquare = false;
		int base = 0;
		int i = 1;
		while ((!isPerfectSquare) && (i * i <= n)) {
			isPerfectSquare = ((n % i == 0) && (n / i == i));
			if(isPerfectSquare)
				base = i;
			i++;
		}
		return base;
	}

}
