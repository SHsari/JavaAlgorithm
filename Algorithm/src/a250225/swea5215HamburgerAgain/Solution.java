package a250225.swea5215HamburgerAgain;
import java.io.*;
import java.util.StringTokenizer;


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
 * 전형적인 knapSack problem 인가부다
 * 
 * 조합으로 풀되 프루닝을 적극적으로 사용해보자
 */

public class Solution {
	
	static int N, MAXCAL;
	
	//결과값이 될 maxUtilScore
	static int maxUtilScore;
	static int remCal, remUtil;

	static int[] utilities;  // 1000 이하
	static int[] calories;	 // 1000 이하
	
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
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

			// Calorie 기준으로 내림차순으로 정렬.
            // pruning을 빨리 할 수 있다.
            quickSort(0, N-1);

			maxUtilScore=0;

			if(remCal<=MAXCAL) {
				sb.append("#").append(tc).append(" ");
				sb.append(remUtil).append("\n");
				continue;
			} else if (MAXCAL < calories[N-1]) {
				sb.append("#").append(tc).append(" ");
				sb.append("0").append("\n");
			}

			for(int count = 1; count <= N; count ++) {
				//count값 만큼 우측부터 1로 채운다.
				int bitmask = (1<<count) -1;

				// 비트마스크를 쓰려고 문법을 물어보니 너무 간단한 코드를 받아버렸습니다.
				while (bitmask < (1 << N)) {
					calculateCombination(bitmask);
					// Gosper’s Hack: 현재 bitmask의 다음 조합 생성
					// bitmask에서 가장 낮은 1의 자리만 남는다.
					int c = bitmask & -bitmask;
					// 가장 낮은 1 (또는 무리)를 기준으로 상위 비트에 대한 정보를 r 로 보존
					int r = bitmask + c;
					//기준점에서 하위비트의 갯수를 보존하면서 동시에 가장 우측으로 true비트를 몰아줌으로서
					//오름차순으로 바로 다음 조합이 나타남
					bitmask = (((r ^ bitmask) >>> 2) / c) | r;
				}
			}
			
			sb.append("#").append(tc).append(" ");
			sb.append(maxUtilScore).append("\n");
		}
		
		System.out.println(sb);
	}

	static void calculateCombination(int bitmask) {
		int totalCal = 0, totalUtil = 0;
		// 각 비트를 순회하며, 선택된 재료에 대해 처리
		for (int i = 0; i < N; i++) {
			if ((bitmask & (1 << i)) != 0) {
				totalCal += calories[i];
				totalUtil += utilities[i];
			}
		}
		if (totalCal <= MAXCAL) {
			maxUtilScore = Math.max(maxUtilScore, totalUtil);
		}
	}


	 
    static void quickSort(int start, int end) {
        if(start>=end) return;
 
        int poleIndex = (int)(Math.random() * (end-start+0.9)) + start;
        swap(poleIndex, end);
        int pole = calories[end];
        int idxL=start;
        int idxR=end;
        for(int i=start; i<=idxR; i++) {
            if(calories[i] > pole) {
                swap(idxL++, i);
            }
            else if(calories[i] < pole) {
                swap(i--, idxR--);
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
