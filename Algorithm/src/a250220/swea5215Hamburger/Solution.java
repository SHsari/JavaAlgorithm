package a250220.swea5215Hamburger;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.HashMap;
import java.util.Map;

/*
 * SWEA 5215. 햄버거 다이어트
 * 
 * T : testCase
 * N : 재료의 수 ( 1 <= N <= 20 )
 * L : 제한 칼로리 ( 1 <= L <= 10^4 )
 * 
 * Ti : 만족도 
 * Ki : 칼로리
 * 
 * 전형적인 knapSack problem 인가
 * 우째 풀어야 할까
 * 
 * DP 로 풀면 좋다고 했던 거 같은데,
 * 효용이 그래프의 노드가 되고
 * 효용 점수로 접근시 비용이 나오게 되면..?
 * 그리고 매 iteration 마다 새로운 선택지를 통해 효용에 도달하는 최소 비용 업데이트?
 * 
 * 반대로 할 수도 있겠다
 * 칼로리(비용)으로 접근해서 얻을 수 잇는 최대 효용이 나오는...
 * 이거 맵으로 구현해도 좋긴 하겠다.
 * 
 * -> 재료를 한번밖에 사용이 불가능한 것으로 보이므로
 * 위의 방식은 적합하지 않을 것으로 보임.
 * 걍 모든 경우의수를 무지성탐색 하는게 좋을 것 같은 느낌이..
 *
 * 
 * 1. Greedy
 * - 만족도가 큰 것부터 담기 ? X
 * - 비용이 적은 것 부터 담기? X
 * 
 * 2. 
 */

public class Solution {
	
	static Map<Integer, Integer> calUtilMap;
	
	static int N, MAXCAL;
	
	//결과값이 될 maxUtilScore
	static int maxUtilScore;
	
	static int[] utilities;  // 1000 이하
	static int[] calories;	 // 1000 이하
	
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			maxUtilScore=0;
			StringTokenizer nl = new StringTokenizer(br.readLine());
			N = Integer.parseInt(nl.nextToken());
			MAXCAL = Integer.parseInt(nl.nextToken());
			
			utilities = new int[N];
			calories = new int[N];
			//단순 입력을 위한 For문
			//N개의 utility, Calorie 쌍을 입력받는다.
			for(int i=0; i<N; i++) {
				StringTokenizer ucPair = new StringTokenizer(br.readLine());
				utilities[i] = Integer.parseInt(ucPair.nextToken());
				calories[i] = Integer.parseInt(ucPair.nextToken());
			}
			// 입력 완료
			
			// 최대 utility 업데이트
			findCombination(0, 0, 0);
			
			sb.append("#" + tc + " ");
			sb.append(maxUtilScore + "\n");
		}
		
		System.out.println(sb);
	}
	
	// 조합을 사용해서 풀어봤는데,
	// 오류부분을 확인하지 못하고 로직 변경.
	// index 0부터 재료를 순회하면서 사용할 때, 하지 않을 때에 따라 분기한다.
	
	static void findCombination(int index, int utilitySum, int calorieSum) {
		//기저조건
		if(index == N) {
			if(calorieSum <= MAXCAL) {
				if(maxUtilScore<utilitySum) maxUtilScore = utilitySum;
			}
			return;
		}
		
		if(calorieSum > MAXCAL) { return;}
		
		//안쓸때
		findCombination(index+1, utilitySum, calorieSum);
		
		//썼을때
		int newUtility = utilitySum + utilities[index];
		int newCalorie = calorieSum + calories[index];
		findCombination(index+1, newUtility, newCalorie);
	}
}
