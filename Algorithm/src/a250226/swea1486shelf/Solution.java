package a250226.swea1486shelf;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();
	
	static int N, B, minDifference;
	static int[] heights;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		
		for(int tc=1; tc<=T; tc++) {
			
			StringTokenizer nb = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(nb.nextToken());
			B = Integer.parseInt(nb.nextToken());
			heights = new int[N];
			int heightRemainder =0;
			minDifference = Integer.MAX_VALUE;
			StringTokenizer heightStr = new StringTokenizer(br.readLine());

			for(int idx=0; idx<N; idx++) {
				heights[idx] = Integer.parseInt(heightStr.nextToken());
				heightRemainder +=  heights[idx];
			}
			
			Arrays.sort(heights);
			// 입력완료
			combination(0, 0, 0, heightRemainder);
			
			result.append("#").append(tc).append(" ");
			result.append(minDifference).append("\n");
		}
		System.out.println(result);
		
	}
	
	static int combination(int depth, int start, int sum, int remainder) {
		
		/*
		 * 1: 추가탐색 필요
		 * 0: 직전노드에서 추가탐색 불필요
		 * -1: 바로 종료
		 */
		if(depth == N) return 1;
		else if(sum+remainder < B) return 0;
		
		for(int i=start; i<N; i++) {
			
			remainder -= heights[i];
			sum += heights[i];
			if(sum>=B) {
				if(sum-B < minDifference) minDifference = sum-B;
				if(minDifference==0) return -1;
				return 1;
			}
			
			int needMoreSearch = combination(depth+1, i+1, sum, remainder);
			if(needMoreSearch < 0) return -1;
			else if (needMoreSearch == 0) return 1;
			
			sum -= heights[i];
		}
		return 1;
	}
}
