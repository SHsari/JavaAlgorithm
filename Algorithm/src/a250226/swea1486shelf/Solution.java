package a250226.swea1486shelf;

/* SWEA 1468 선반문제
 * 
 * knapSack 문제의 변형이라 느껴졌는데, 
 * 결론적으로는 조합으로 완전탐색 했다.
 * 
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();
	
	// N은 동원가능한 사람 명수
	// B는 도달해야할 선반의 높이
	// minDifference 는 결과값이다.
	static int N, B, minDifference;
	static int[] heights;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {


			// 입력 및 초기화
			StringTokenizer nb = new StringTokenizer(br.readLine());
			N = Integer.parseInt(nb.nextToken());
			B = Integer.parseInt(nb.nextToken());

			heights = new int[N];
			int heightRemainder =0;
			minDifference = Integer.MAX_VALUE;

			// 초기 변수들 입력 완료

			// 이후 주어지는 배열 입력.
			StringTokenizer heightStr = new StringTokenizer(br.readLine());

			for(int idx=0; idx<N; idx++) {
				heights[idx] = Integer.parseInt(heightStr.nextToken());
				heightRemainder +=  heights[idx];
			}
			// 입력완료

			// 정렬시 프루닝이 더 빨리 될 것이다.
			Arrays.sort(heights);
			
			// 모든 경우의 수
			combination(0, 0, 0, heightRemainder);
			
			result.append("#").append(tc).append(" ");
			result.append(minDifference).append("\n");
		}
		System.out.println(result);
		
	}
	
	static int combination(int depth, int start, int sum, int remainder) {
		/* 리턴값 별 의미
		 * 1: 추가탐색 필요
		 * 0: 직전노드까지 추가탐색 불필요
		 * -1: 바로 종료
		 */
		if(depth == N) return 1; // 최대 깊이에 도달시

		// 남은 선택을 모두 모아도 조건에 부합하지 않는 경우
		else if(sum+remainder < B) return 0;
		

		// 그 외 일반적인 경우
		for(int i=start; i<N; i++) {
			
			remainder -= heights[i];
			sum += heights[i];

			// 기저조건에 이것저것 넣으려다 보니 프루닝이 정확히 안되는 것들이 있어서
			// 원소 선택하고 바로 조건 만족여부 확인
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
