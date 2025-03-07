package a250227.swea1952swimmingPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Solution {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	static int[] price = new int[4];
	static int[] monthPlan = new int[12];
	static int[] monthPrice = new int[12];
	static final int DAY =0, MONTH=1, QUARTER=2, YEAR=3;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			
			// 요금 종류 4가지의 요금 입력
			for(int i=0; i<4; i++) price[i] = nextInt();
			
			// 월별 이용 계획 입력
			for(int mon=0; mon<12; mon++) monthPlan[mon] = nextInt();
			
			
			// 일별요금과 월별요금 비교
			for(int mon=0; mon<12; mon++) {
				int daySum = price[DAY] * monthPlan[mon];
				monthPrice[mon] = Math.min(daySum, price[MONTH]);
			}
			
			
			// 3개월 요금을 반영해야 하는데.
			// 딱 DP 문제 처음 봤을 때 이런 기분이었다.
			// 백준에 있던 타일링 문제
			// 또는 백준 RGB 거리
			int[] priceDP = new int[12];
			
			// 초기화
			priceDP[0] = monthPrice[0];
			priceDP[1] = priceDP[0] + monthPrice[1];
			priceDP[2] = Math.min(price[QUARTER], priceDP[1]+monthPrice[2]);
	
			// dp 순회
			for(int mon=3; mon<12; mon++) {
				
				//1. 현재달 싱글
				//2. 최근 3달 쿼터 
				 
				int a = priceDP[mon-3] + price[QUARTER];
				int b = priceDP[mon-1] + monthPrice[mon];
				priceDP[mon] = Math.min(a, b);
			}
			
			int minPrice = Math.min(priceDP[11], price[YEAR]);
			
			result.append("#").append(tc).append(" ");
			result.append(minPrice).append("\n");
		}
			
		System.out.println(result);
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}