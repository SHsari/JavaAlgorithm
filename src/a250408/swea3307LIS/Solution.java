package a250408.swea3307LIS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Solution {
	/*
	 * LIS 기본 문제입니다.
	 * O(NlogN) 으로 풀었습니다.
	 *
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			int lis = solve();
			result.append(lis).append("\n");			
		}
		System.out.println(result);
	}
	
	
	static int solve() throws IOException {
		int N = nextInt();
		int[] A = new int[N];
		//int[] D = new int[N];
		int[] minEnd = new int[N+1];
		Arrays.fill(minEnd, Integer.MAX_VALUE);

		int lis = 1;
		A[0] = nextInt();
		//D[0] = 1;
		minEnd[0] = 0; minEnd[1] = A[0];
		for(int i=1; i<N; i++) {
			A[i] = nextInt();
			int max =0;
			int hik = lis, lok = 1;
			int mid;
			while(hik>=lok) {
				mid = (hik+lok)/2;
				if(minEnd[mid] < A[i]) {
					lok = mid+1;
					max = mid;
				} else {
					hik = mid-1;
				}
			}
			max++;
			if(lis<=max) lis=max;
			if(minEnd[max] > A[i]) minEnd[max] = A[i];
		}
		
		//System.out.println(Arrays.toString(R));
		return lis;
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}