package com.jesega.kafka.workingqueue.consumers.processors;

public class Palindromic extends Procesador {

	@Override
	public String process(String value) {
		int n = getNumber(value);
		
		StringBuilder sb = new StringBuilder(value);
		sb.append(" \n ");
		if(isPalindromic(n))
			sb.append("es capicua");
		else
			sb.append("no es capicua");
			
		return sb.toString();
	}
	
	private boolean isPalindromic(int n)
	{
		int tmp = n;
		int sum = 0;
		int r = 0;
		
		while(tmp > 0)
		{
			r = tmp%10;
			sum = (sum*10) + r;
			tmp = tmp/10;
		}
		return (sum == n);
			
	}

}
