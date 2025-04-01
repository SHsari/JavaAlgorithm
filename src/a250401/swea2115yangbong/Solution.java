package a250401.swea2115yangbong;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Map;


public class Solution {
	
	static class ValueWindow implements Comparable<ValueWindow>{
		int startR, startC, valueSum;	
		// 정렬시 Value에 대해 내림차순으로 정렬.
		@Override
		public int compareTo(ValueWindow o) {
			return o.valueSum - this.valueSum;
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	// N*N의 벌통 (3 ≤ N ≤ 10)
	// M개의 가로로 연속된 벌통 사용. (1 ≤ M ≤ 5, M ≤ N)
	// 인당 최대 C만큼만 채취 가능. (10 ≤ C ≤ 30)
	// 한개벌통에 들은 꿀의 양은 1~9
	static int N, windowLength, WeightLimit, maxProfit;
	static Map<Point, Integer> valueMap;
	static int[][] weight, profit, weightSum;
	
	/*
	 * 순서대로 접근할 때.
	 * 1. 채취위치 조합 O(N^4) ~ 10^4
	 * 2. 최대 채취 가능량 제한으로 인한 선택
	 * -> knapsack 문제네 ㅁㅊ..선택지가 5개인 knapsack
	 * -> 완탐으로 풀게 된다면 2^5
	 * 3. 해서 최댓값 찾기.
	 * 
	 * 
	 * 슬라이딩 윈도우를 사용했는데, 최대 윈도우의 크기가 5로 충분히 작아보이므로
	 * 사용하지 않아도 될 것 같습니다.
	 */
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");		
			init();
			combination();
			result.append(maxProfit).append("\n");
		}
		System.out.println(result);
	}
	
	
	static class Profit {
		static int maxProfit;
		
		static int getProfit(int r, int c) {
			// r, c로부터 M개의 벌통에 대해서 얻을수 있는 최대 수익을 구합니다.
			int wSum = weightSum[r][c+windowLength] - weightSum[r][c];
			if(wSum<=WeightLimit) {
				int profitSum=0;
				for(int col=c+1; col<=c+windowLength; col++) {
					profitSum += profit[r][col];
				} return profitSum;
			}
			else {
				maxProfit=0;
				profitDFS(r, c+1, 0, 0, 0);
				return maxProfit;
			}
		}
		
		// KnapSack Problem을 DFS 완전탐색으로 해결합니다.
		static void profitDFS(int r, int c, int depth, int weightSum, int profitSum) {
			if(depth==windowLength) {
				if(profitSum > maxProfit) maxProfit = profitSum;
				return;
			}
			
			int nextWeight = weightSum + weight[r][c+depth];
			if(nextWeight <= WeightLimit) {
				profitDFS(r, c, depth+1, nextWeight, profitSum+profit[r][c+depth]);
			}
			profitDFS(r, c, depth+1, weightSum, profitSum);
		}
		
	//	  static int getProfit(int r, int c) {
	//		// r, c로부터 M개의 벌통에 대해서 얻을수 있는 최대 수익을 구합니다.
	//		int sum = sumMap[r][c+M] - sumMap[r][c];
	//		if(sum<=C) return sum;
	//		else {
	//			int[][] knap = new int[M][C+1];
	//			
	//			for(int maxW=weight[r][c]; maxW<=C; maxW++) {
	//				knap[0][maxW] = profit[r][c];
	//			}
	//			
	//			for(int offset=1; offset<M; offset++) {
	//				int curP = profit[r][c+offset];
	//				int curW = weight[r][c+offset];
	//				int maxW=0;
	//				for(; maxW<Math.min(curW, C); maxW++) {
	//					knap[offset][maxW] = knap[offset-1][maxW];
	//				}
	//				for(; maxW<=C; maxW++) {
	//					knap[offset][maxW] = Math.max(knap[offset-1][maxW], curP + knap[offset-1][maxW-curW]);
	//				}
	//			}
	//		}
	//		
	//		return 0;
	//	}
	}
	
	//각 채취자 윈도우의 첫번째 위치를 선정하는 로직.
	static void combination() {
		// 첫번째 윈도우는 완전탐색으로 합니다.
		for(int r1=0; r1<N; r1++) {
			for(int c1=0; c1<=N-windowLength; c1++) {
				// 첫번째 window가 결정남.
					int profit1 = Profit.getProfit(r1, c1);
					
				// 두번재 윈도우는 아래 두가지 경우로 나눕니다.
				//1. 동일한 Row에서 두번째 window 선택
				for(int c2=c1+windowLength; c2<=N-windowLength; c2++) {
					int profit2 = Profit.getProfit(r1, c2);
					int sum = profit1+profit2;
					if(sum > maxProfit) maxProfit = sum;
				}
				
				//2. 더 낮은 Row에서 두번째 window 선택
				for(int r2=r1+1; r2<N; r2++) {
					for(int c2=0; c2<=N-windowLength; c2++) {
						int profit2 = Profit.getProfit(r2, c2);
						int sum = profit1+profit2;
						if(sum > maxProfit) maxProfit = sum;
					}
				}
			}
		}
	}

	
	static void init() throws IOException {
		maxProfit=0;
		N = nextInt();
		windowLength = nextInt();
		WeightLimit = nextInt();
		
		weight = new int[N][N+1];
		profit = new int[N][N+1];
		weightSum = new int[N][N+1];
		
		for(int r=0; r<N; r++) {
			//0번 column은 0으로 둡니다.
			for(int c=1; c<=N; c++) {
				int tmp = nextInt();
				weight[r][c] = tmp;
				profit[r][c] = tmp*tmp;
				weightSum[r][c] = weightSum[r][c-1]+tmp;
			}
		}
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}