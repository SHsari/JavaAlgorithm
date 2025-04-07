package liveClass;

import java.util.Scanner;

public class divConquer {
	
	static int callCnt;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
	}
	
	
	private static long exp1(long x, long n) {
		++callCnt;
		if(n==1) return x;
		return x * exp1(x, n-1);
	}
	
	private static long exp2(long x, long n) {
		++callCnt;
		
		if(n==1) return x;
		
		long y = exp2(x, n/2);
		return n%2==0? y*y :y*y*x;
	}
	
}
