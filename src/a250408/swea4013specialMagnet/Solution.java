package a250408.swea4013specialMagnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class Solution {
	/*
	 * SWEA 4013 특이한 자석.
	 * 간단한 구현 문제로 파악했습니다.
	 * 
	 * 특정 조건에서만 회전이 전달되는 톱니바퀴 문제.
	 * 
	 * 주어진대로 특정 바퀴를  특정 방향, 횟수만큼 회전시켰을때,
	 * 결과를 출력하라.
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
			int tscore = solve();
			result.append(tscore).append("\n");
			
		}
		System.out.println(result);
	}
	
	static int solve() throws IOException {
		
		boolean[][] gears = new boolean[4][8];
		// 빨간 화살표의 index 저장.
		int[] redIdx = new int[4];
		int K = nextInt();
		
		for(int i=0; i<4; i++) {
			for(int t=0; t<8; t++) {
				gears[i][t] = nextInt()==1;
			}
		}
		for(int k=0; k<K; k++) {
			int gearIdx = nextInt()-1;
			int clockWise[] = new int[4];
			clockWise[gearIdx] = nextInt();
			
			//1. 휠의 좌측으로 회전 전파
			int left = gearIdx-1;
			while(left>=0) {
				// redIdx는 모듈러 없이 바로사용할 수 있는 idx를 저장.
				// 따라서 다음과 같이 idx 계산
				int toothIdxL = (redIdx[left] + 2)%8;
				int toothIdxR = (redIdx[left+1] + 6)%8;
				if(gears[left][toothIdxL]^gears[left+1][toothIdxR]) {
					clockWise[left] = -clockWise[left+1];
				} else break;
				left--;
			}
			
			//2. 휠의 우측으로 회전 전파
			int right = gearIdx+1;
			while(right<4) {
				int toothIdxL = (redIdx[right-1] + 2)%8;
				int toothIdxR = (redIdx[right] + 6)%8;
				if(gears[right-1][toothIdxL]^gears[right][toothIdxR]) {
					clockWise[right] = -clockWise[right-1];
				} else break;
				right++;
			}
			
			//3. 회전 실행
			for(int i=0; i<4; i++) {
				redIdx[i] -= clockWise[i];
				redIdx[i] += 8;
				redIdx[i] %= 8;
			}
		}
		
		// 모든 과정을 마치고 점수계산.
		int[] scoreAt = {1, 2, 4, 8};
		int totalScore=0;
		for(int i=0; i<4; i++) {
			if(gears[i][redIdx[i]]) totalScore+=scoreAt[i];
		}
		//System.out.println(Arrays.toString(redIdx));
		return totalScore;
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}