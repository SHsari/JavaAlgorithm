package a250225.swea4012chef;

import java.io.*;

import java.util.StringTokenizer;

/*
 * SWEA 4012 요리사
 * 
 * 결론은 또 조합
 * N개중에 N/2개를 뽑는다.
 * 다만  1, 2, 3, 4 중
 * 1, 2	를 뽑는 경우와  3, 4를 뽑는 경우가 동일하다.
 * 그럼 첫번째 재료를 고정해놓아야 겠다.
 * 
 * N C N/2
 * +
 * (N/2) ^ 2
 * 
 * sij는 20000 이하의 자연수
 * 
 * N은 최대 16 이므로, 최대 재료 갯수는 8
 * 
 * 20000 * 8 = 160000
 * 
 */

public class Solution {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();
	static int[][] synergy;

	static int minDifference;
	static int N;
	static int Ndiv2;
	static boolean[] isDish1;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int tc =1; tc <= T; tc ++) {

			minDifference = Integer.MAX_VALUE;
			
			N = Integer.parseInt(br.readLine());
			Ndiv2 = N/2;
			
			isDish1 = new boolean[N];
			synergy = new int[N][N];

			for(int i=0; i<N; i++) {
				StringTokenizer line =  new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					int sij = Integer.parseInt(line.nextToken());
					synergy[i][j] = sij;
				}
			}
			//1번 요리는 0번 재료를 무
			isDish1[0] = true;
			getCombination(Ndiv2-1, 1);
			result.append("#").append(tc).append(" ");
			result.append(minDifference).append("\n");
		}
		System.out.println(result);
	}
	
	static void getCombination(int toSelect, int start) {
		if(toSelect == 0) {
			getSumAndUpdateMinValue();
			return;
		}
		
		// dish1에 쓰기, i가 재료 index 이다.
		for(int i=start; i<=N-toSelect; i++) {
			isDish1[i] = true;
			getCombination(toSelect-1, i+1);
			isDish1[i] = false;
		}
		
		// dish2에 쓰기
	}
	
	static void getSumAndUpdateMinValue() {
		int dish1Score=0, dish2Score = 0;
		int[] dish2List = new int[Ndiv2];
		int[] dish1List = new int[Ndiv2];
		
		int dish2Idx=0;
		int dish1Idx=0;
		for(int idx =0; idx<N; idx++) {
			if(!isDish1[idx]) dish2List[dish2Idx++]=idx;
			else dish1List[dish1Idx++]=idx;
		}
		
		
 		for(int first=0; first<Ndiv2; first++) {
			for(int second=first+1; second<Ndiv2; second++) {
				int i1 = dish1List[first];
				int j1 = dish1List[second];
				dish1Score += synergy[i1][j1];
				dish1Score += synergy[j1][i1];
				
				int i2 = dish2List[first];
				int j2 = dish2List[second];
				dish2Score += synergy[i2][j2];
				dish2Score += synergy[j2][i2];
				

			}
		}
		
		
		int diff = Math.abs(dish1Score - dish2Score);
		if(diff < minDifference) {
			minDifference = diff;
			
		}
	}
}
