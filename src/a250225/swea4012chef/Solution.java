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
 * 발생할 수 있는 맛 시너지 최대값은 다음과 같다.
 * 20000 * 8 = 160000
 * 
 * 
 */

public class Solution {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();

	// 순회할 배열 Synergy
	static int[][] synergy;

	// 결과값을 담는 MinDifference
	static int minDifference;
	static int N;
	static int Ndiv2;

	//첫번째 요리에 사용한 재료인지, 두번재 요리에 사용한 재료인지 구분하기 위한 배열
	//Combination을 표시하는 배열이라 보아도 무방.
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
					
					// 상단 역 피라미드를 유효하게 만들기.
					if(i>j) synergy[j][i] += sij;
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

		// isDish1 boolean 배열을 순회하며
		// 1, 2번 요리의 리스트에 재료 인덱스를 추가한다.
		int[] dish2List = new int[Ndiv2];
		int[] dish1List = new int[Ndiv2];
		
		int dish2Idx=0;
		int dish1Idx=0;
		for(int idx =0; idx<N; idx++) {
			if(!isDish1[idx]) dish2List[dish2Idx++]=idx;
			else dish1List[dish1Idx++]=idx;
		}
		
		// N/2 개의 재료들 중 2개를 뽑아 시너지 값을 얻어와야 한다.
		// 간단히 이중포문을 사용하여 2개 인덱스 조합을 순회한다.
		// 항상 first가 second보다 작다.
		// dish List 에도 index가 오름차순으로 저장되어 있으므로 (조합 함수 특징)
		// 항상 i가 j보다 작다.
 		for(int first=0; first<Ndiv2; first++) {
			for(int second=first+1; second<Ndiv2; second++) {
				int i1 = dish1List[first];
				int j1 = dish1List[second];
				dish1Score += synergy[i1][j1];
				
				int i2 = dish2List[first];
				int j2 = dish2List[second];
				dish2Score += synergy[i2][j2];
			}
		}
		
		// 조합을 바탕으로 계산한 맛 시너지 최종값의 차를 구하고
		// Min diff 값을 업데이트
		int diff = Math.abs(dish1Score - dish2Score);
		if(diff < minDifference) {
			minDifference = diff;
			
		}
	}
}
