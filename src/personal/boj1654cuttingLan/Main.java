package personal.boj1654cuttingLan;

import java.io.*;

public class Main {
	/*
	 * boj 1654 랜선 자르기.
	 * 이분탐색을 이용해서푸는 유명한 문제,
	 * Aggressive Cows 문제이며
	 * Long 타입을 사용해야 하는 것이 함정.
	 */

	static int N, K;
	static int maxLen, sumLen;
	static int maxLan;
	static int[] lans;
	static BufferedReader br;
	static StreamTokenizer st;
	
	public static void main(String[] args) throws IOException {
		init();
		binarySearch();
		System.out.println(maxLan);
		
	}
	
	private static void binarySearch() {
		maxLan = 0;
		int lo=1, hi=maxLen;
		int mid;
		while(lo<=hi) {
			mid = (lo+hi)/2;
			
			if(isPossible(mid)) {
				maxLan = mid;
				lo = mid+1;
			}else {
				hi = mid-1;
			}	
		}
	}
	
	private static boolean isPossible(long len) {
		long result = 0;
		for(int i=0; i<K; i++) {
			result += (long)lans[i] / len;
		}
		return result >= N;
	}
	
	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StreamTokenizer(br);
		
		
		K = nextInt();
		N = nextInt();
		
		lans = new int[K];
		
		maxLen=0;
		
		for(int i=0; i<K; i++) {
			lans[i] = nextInt();
			if(maxLen<lans[i]) maxLen=lans[i];
		}
	}
	
	private static int nextInt() throws IOException {
		st.nextToken();
		return (int)st.nval;
	}
}
