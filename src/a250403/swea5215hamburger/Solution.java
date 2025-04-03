package a250403.swea5215hamburger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Solution {
	
	/*
	 * SWEA 5215 햄버거
	 * 
	 * 다시 문제를 읽어보니
	 * 가장 기본적인 KnapSack Problem이네용..
	 * 
	 * 그때보다 쬐금 늘긴 했나 봅니다.
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, L;
	static int[] T, K;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			int maxTaste = dp();
			
			result.append(maxTaste).append("\n");
			
		}
		System.out.println(result);
	}
	
	
	/*
	 * 정석적인 knapsack dp 입니다.
	 * 0~N-1번을 차례대로 사용 고려 대상에 추가합니다.
	 * 해당 재료를 넣을 때와 넣지 않을 때 중 더 큰 편익을
	 * tasteAt[재료idx][weightLimit]
	 * 에 저장합니다.
	 * 
	 * 가능한 모든 weightLimit에 대해 최대 편익을 계산해야 한다는 점이 
	 * 생각하기 어려운 부분입니다.
	 */
	static int dp() {
		int[][] tasteAt = new int[N][L+1];
		// 0번 재료에 대한 셋팅
		Arrays.fill(tasteAt[0], K[0], L+1, T[0]);
		
		// 1번 재료부터 반복문으로.
		for(int src=1; src<N; src++) {
			// src의 칼로리 보다 작은 곳에 대해서는 
			// 무조건 src를 넣지 않았을때의 편익을 넣어줌.
			for(int cal=1; cal<K[src]; cal++) {
				tasteAt[src][cal] = tasteAt[src-1][cal];
			}
			
			// src의 칼로리 보다 큰 곳에 대해서는
			// src를 넣을때, 넣지 않을때의 편익을 비교해야 합니다.
			for(int cal=K[src]; cal<=L; cal++) {
				int tasteWithCur = tasteAt[src-1][cal-K[src]] + T[src];
				tasteAt[src][cal] = Math.max(tasteAt[src-1][cal], tasteWithCur);
			}
		}
		// 모든 재료를 고려했을때 최대 무게에 대한 최대 편익.
		return tasteAt[N-1][L];
	}
	
	static void init() throws IOException {
		
		N = nextInt();
		L = nextInt();
		
		T = new int[N];
		K = new int[N];
		
		for(int i=0; i<N; i++) {
			T[i] = nextInt();
			K[i] = nextInt();
		}
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}