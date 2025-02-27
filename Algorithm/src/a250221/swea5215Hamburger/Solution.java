package a250221.swea5215Hamburger;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Map;

/*
 * SWEA 5215. 햄버거 다이어트
 * 부분집합에 프루닝 적용해보자
 * 
 * T : testCase
 * N : 재료의 수 ( 1 <= N <= 20 
 * L : 제한 칼로리 ( 1 <= L <= 10^4 )
 * 
 * Ti : 만족도 
 * Ki : 칼로리
 * 
 * 부분집합으로 풀어보자.
 * 
 */

public class Solution {
	
	static Map<Integer, Integer> calUtilMap;
	
	static int N, MAXCAL;
	
	//결과값이 될 maxUtilScore
	static int maxUtilScore;

	//매 재귀 호출마다 업데이트 될 값.
	static int utilSum;
	static int calSum;
	static int remCal;
	static int remUtil;
	
	static int[] utilities;  // 1000 이하
	static int[] calories;	 // 1000 이하
	
	static StringBuilder sb;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			maxUtilScore=0;
			remUtil = 0;
			remCal = 0;
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
				remCal += calories[i];
				remUtil += utilities[i];
			}
			// 입력 완료


			// 재료를 다 넣어도 제한값을 만족한다면.
			if(remCal < MAXCAL) {
				sb.append("#" + tc + " ");
				sb.append(remUtil + "\n");
				continue;
			}

			// Calorie 기준으로 내림차순으로 정렬.
			// pruning을 빨리 할 수 있다.
			quickSort(0, N-1);

			// System.out.println();
			// for(int i=0; i<N; i++) {
			// 	System.out.println(utilities[i] + ", " + calories[i]);
			// }
			// System.out.println();

			
			// 최대 utility 업데이트
			findCombination(0);
			
			sb.append("#" + tc + " ");
			sb.append(maxUtilScore + "\n");
		}
		
		System.out.println(sb);
	}
	
	// 조합을 사용해서 풀기.
	// index 0부터 재료를 순회하면서 사용할 때, 하지 않을 때에 따라 분기한다.
	// pruning을 적극적으로 써보자.


	// false return시 더 이상 탐색할 필요가 없음
	// true return시 일반적으로 진행한다.

	static void findCombination(int depth){

		// 마지막 선택 +1에 도달
		if(depth==N) {
			updateMaxUtil();
			return;
		}
		
		else if(MAXCAL < calSum) {
			return;
		}
		
		// 여유 칼로리가 너무 적어 어떤 재료도 추가 불가능
		else if(MAXCAL-calSum < calories[N-1]) {
			updateMaxUtil();
			return;
		}
		// 남은걸 다 사용해도 이전 최고값을 갱신하지 못할 때 {
		else if(utilSum + remUtil < maxUtilScore) {
			return;
		}

		// 남은걸 다 사용해도 제한 칼로리 안일 때
		else if(calSum + remCal <= MAXCAL) {
			utilSum += remUtil;
			updateMaxUtil();
			utilSum -= remUtil;
			return;
		}

		remCal -= calories[depth];
		remUtil -= utilities[depth];

		// 선택할 때
		calSum += calories[depth];
		utilSum += utilities[depth];
		findCombination(depth+1);
		utilSum -= utilities[depth];

		calSum -= calories[depth];

		//		
		//선택하지 않을 때
		findCombination(depth+1);

		remCal += calories[depth];
		remUtil += utilities[depth];

	}

	static void updateMaxUtil() {
		if(utilSum>maxUtilScore) {
			maxUtilScore = utilSum;
		}
	}

	static void quickSort(int start, int end) {
		if(start>=end) return;

		int poleIndex = (int)(Math.random() * (end-start+1)) + start;

		swap(poleIndex, end);
		int pole = calories[end];
		int idxL=start;
		int idxR=end;
		for(int i=start; i<=idxR; i++) {
			if(calories[i] > pole) {
				swap(idxL++, i);
			}
			else if(calories[i] < pole) {
				swap(idxR--, i--);
			}
		}
		
		quickSort(start, idxL-1);
		quickSort(idxR+1, end);
	}
	
	static void swap(int idx1, int idx2) {
		int tmp = calories[idx1];
		calories[idx1] = calories[idx2];
		calories[idx2] = tmp;
		tmp = utilities[idx1];
		utilities[idx1] = utilities[idx2];
		utilities[idx2] = tmp;
	}
}
