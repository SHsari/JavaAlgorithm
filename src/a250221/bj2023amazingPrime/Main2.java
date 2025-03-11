package a250221.bj2023amazingPrime;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class Main2 {

	static BufferedReader br;
	static StreamTokenizer st;
	
	static int N;
	static List<Integer> primes;
	static boolean[] isPrime;
	static List<Integer> amazingPrime;
	static final int[] firstPrime = {2, 3, 5, 7};
	
	public static void main(String[] args) throws IOException {
		StringBuilder result = new StringBuilder();
		
		for(int prime : firstPrime) {
			getNthPrime(prime, 1);
		}
			
		for(int aPrime : amazingPrime) {
			result.append(aPrime).append("\n");
		}
		
		System.out.println(result);

	}
	
	static void getNthPrime(int aPrime, int depth) {
		if(depth == N) {
			amazingPrime.add(aPrime);
		} else {
			aPrime*=10;
			for(int adder=1; adder<10; adder+=2) {
				if(isPrime(aPrime+adder)) getNthPrime(aPrime+adder, depth+1);
			}
		}
	}
	
	static boolean isPrime(int num) {
		int sqrt = (int)Math.floor(Math.sqrt(num));
		for(int prime : primes) {
			if(prime<=sqrt) {
				if(num% prime ==0 ) return false;
			} else break;
		}
		return true;
	}
	
	static void eratos() {
		int maxEratos = 1 + (int) Math.floor(Math.sqrt(Math.pow(10, N)));
		dddd
		isPrime = new boolean[maxEratos];
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = true;
		for(int i=2; i<= Math.sqrtmaxEratos)
		
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        
        amazingPrime = new ArrayList<>();
        for(int prime : firstPrime) {
        	amazingPrime.add(prime);
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}