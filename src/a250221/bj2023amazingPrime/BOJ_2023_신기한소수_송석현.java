package a250221.bj2023amazingPrime;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
/*
 * 1. 방법 1
 * 비트마스크를 이용해 10^7까지 에라토스테네스의 체로 소수를 미리 판별
 * 
 * 10^7 까지는 비트마스크를 이용한 소수 여부에 따라서
 * 아주 빠르게 신기한 소수 여부를 판별 가능.
 * 
 * 2. 방법 2
 * 일반적인 DFS 사용.
 * 소수 판별에 기본적으로 sqrt(n) 까지 숫자까지 나누어보는 기본적인 소수 판별 로직 사용.
 * 신기한 소수는 자릿수가 높아질 수록 적어지는 경향을 보이는 것 같다.
 * 따라서 DFS(자동 백트래킹)이 효율적이다.
 * 
 * 3. 두개를 섞었따.
 * 근데 코드 찾아보니까 방법 2가 가장 빠른 것 같다.
 * 구현 복잡도도 낮고. "신기한 소수" 자체의 특성을 진지하게 고민해 봐야 정확한 풀이 방법을 끄집어 낼 수 있는 문제
 * 
 */

public class BOJ_2023_신기한소수_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	
	static int N, maxEratos;
	static List<Integer> primes;
	static boolean[] isPrime;
	static List<Integer> amazingPrime;
	static final int[] firstPrime = {2, 3, 5, 7};
	
	public static void main(String[] args) throws IOException {
		StringBuilder result = new StringBuilder();
		
		init();
		eratos();

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
	
	/*
	 * 에라토스테네스의 채로 이미 소수판별한 숫자는 바로 결과를 보여주고
	 * 그렇지 않은 sqrt(10^N) 보다 큰 수의 경우 일반적인 소수 판별 로직을 사용한다.
	 * 
	 */
	static boolean isPrime(int num) {
		if(num<=maxEratos) return isPrime[num];
		else {
			int sqrt = (int)Math.floor(Math.sqrt(num));
			for(int prime : primes) {
				if(prime<=sqrt) {
					if(num% prime ==0 ) return false;
				} else break;
			}
			return true;
		}
	}
	
	/*
	 * eratos 소수판별은
	 * sqrt(10^N) 까지만 해놓는다.
	 */
	static void eratos() {
		maxEratos = (int) Math.floor(Math.sqrt(Math.pow(10, N)));
		primes = new ArrayList<>();
		isPrime = new boolean[maxEratos+1];
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;
		int maxCandidate = (int) Math.ceil(Math.sqrt(maxEratos));
		for(int candidate=2; candidate<= maxCandidate; candidate++) {
			if(isPrime[candidate]) {
				primes.add(candidate);
				for(int notPrime=candidate+candidate; notPrime<=maxEratos; notPrime+=candidate) {
					isPrime[notPrime] = false;
				}
			}
		}

		//primes 리스트에 소수를 모두 추가하여 순회 비용을 줄인다.
		for(int i=maxCandidate+1; i<=maxEratos; i++) {
			if(isPrime[i]) primes.add(i);
		}
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        
        amazingPrime = new ArrayList<>();
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}