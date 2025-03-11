package a250221.bj2023amazingPrime;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/*
 * 1. 방법 1
 * 비트마스크를 이용해 10^7까지 에라토스테네스의 체로 소수를 미리 판별
 * 
 * 10^7 까지는 비트마스크를 이용한 소수 여부에 따라서
 * 아주 빠르게 신기한 소수 여부를 판별 가능.
 * 
 * 2. 방법 2
 * 일반적인 DFS 사용.
 * 소수 판별에 기본적으로 sqrt(n) 까지 숫자까지 나누어보는 기본적인 로직을 사용하여
 * 신기한 소수로 판별 된 숫자에서 *=10emde
 * 
 */


public class Main {

	static BufferedReader br;
	static StreamTokenizer st;
	
	static final int TEN7 = 10000000;
	static final int[] FIRSTPRIME = {2, 3, 5, 7};
	static int[] isPrime;
	static int N, startInt, endInt;
	static List<Integer> amazingList;

	
	public static void main(String[] args) throws IOException {
		init();
		StringBuilder result = new StringBuilder();
		if(N<8) {
			eratos(endInt);
			for(int p : FIRSTPRIME) {
				dfs(p, 1);
			}
			
		} else {
			eratos(TEN7);
			for(int p : FIRSTPRIME) {
				dfs(p, 1);
			}
		}
		for(int aPrime : amazingList) {
			result.append(aPrime).append("\n");
		}
		
		System.out.println(result);
	}
	
	static void dfs(int aPrime, int depth) {
		if(depth==N) {
			amazingList.add(aPrime);
		}else if(N < 7){
			aPrime*=10;
			for(int add=1; add<10; add+=2) {
				int candidate = aPrime+add;
				if(isPrime(candidate)) dfs(candidate, depth+1);
			}
		} else {
			for(int add=1; add<10; add+=2) {
				int candidate = aPrime+add;
				if(isPrimeLarge(candidate)) dfs(candidate, depth+1);
			}
		}
	}
	
	static void lastAmazingList() {
		List<Integer> newList = new ArrayList<>();
		for(int aPrime : amazingList) {
			aPrime*=10;
			for(int adder=1; adder<10; adder+=2) {
				if(isPrimeLarge(aPrime+adder)) newList.add(aPrime+adder);
			}
		}
		amazingList = newList;
	}

	static void nextAmazingList() {
		List<Integer> newList = new ArrayList<>();
		for(int aPrime : amazingList) {
			aPrime*=10;
			for(int adder=1; adder<10; adder+=2) {
				if(isPrime(aPrime+adder)) newList.add(aPrime+adder);
			}
		}
		amazingList = newList;
	}
	
	static boolean isPrimeLarge(int num) {
		int end = (int) Math.floor(Math.sqrt(num));
		for(int prime=2; prime<=end; prime++) {
			if(isPrime(prime)) {
				if(num%prime == 0) return false;
			}else break;
		}
		return true;
	}
	
	
	static void eratos(int max) {
		isPrime = new int[max/32 + 1];
		Arrays.fill(isPrime, -1);
		setNotPrime(1);
		int end = (int) Math.floor(Math.sqrt(max));
		for(int seed=2; seed<=end; seed++) {
			if(isPrime(seed)) {
				for(int notPrime=seed+seed; notPrime<=max; notPrime+=seed) {
					setNotPrime(notPrime);
				}
			}
		}
	}
	
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        amazingList = new ArrayList<>();  
		N = nextInt();
		startInt = (int) Math.pow(10, N-1);
		endInt = startInt * 10;
	}
	
	static boolean isPrime(int num) {		
		int rem = num%32;
		int index = num/32;
		return (isPrime[index] & (1 << rem)) != 0;
	}
	
	static void setNotPrime(int num) {
		int rem = num%32;
		int index = num/32;
		isPrime[index] &= ~(1<<rem);
	}


	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}