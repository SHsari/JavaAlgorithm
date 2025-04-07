package a250407.swea3282_01knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Solution {
	
	/*
	 * SWEA 3282 01 Knapsack
	 * N개의 물건,
	 * 부피 제한 K,
	 * N개의 물건에 대한 가격과 부피가 주어짐.
	 * 
	 * 1차원 배열로 knapsack 진행합니다.
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
			
			
			// 1차원 배열 1개로 진행하고자 합니다.
			int N = nextInt();
			int vLimit = nextInt();
			int[] profit = new int[vLimit+1];
			
			// 첫번째 진행
			int volume = nextInt();
			int price = nextInt();
			Arrays.fill(profit, 0, volume, 0);
			Arrays.fill(profit, volume, vLimit+1, price);
			
			// 이후 두번째 진행
			for(int i=1; i<N; i++) {
				volume = nextInt();
				price = nextInt();
				
				for(int lim=vLimit; lim>=volume; lim--) {
					profit[lim] = Math.max(profit[lim], profit[lim-volume]+price);
				}
			}
			
			result.append(profit[vLimit]).append("\n");
		}
		System.out.println(result);
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}