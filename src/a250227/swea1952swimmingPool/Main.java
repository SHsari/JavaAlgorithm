package a250227.swea1952swimmingPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Main {
	
	/*
	 * SWEA 1952 수영장 문제.
	 * 처음 풀때 DP로 풀었습니다.
	 * 
	 * 일단 매달의 
	 * 3달금액이 복병입니다 증말루.
	 * 
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	static int[] price = new int[4];
	static int[] monthPlan = new int[12];
	static int[] monthPrice = new int[12];
	static final int DAY=0, MONTH=1, QUARTER=2, YEAR=3;
	
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
			priceDP[0] = monthPrice[0];// 첫달에 대한 최솟값
			priceDP[1] = priceDP[0] + monthPrice[1]; // 둘째 달 까지의 최솟값
			 // 셋째 달 까지의 최솟값.
			priceDP[2] = Math.min(price[QUARTER], priceDP[1]+monthPrice[2]);
	
			// dp 순회
			for(int mon=3; mon<12; mon++) {
				
				//1. 최근 3달을 쿼터금액으로 결제할 때,
				int a = priceDP[mon-3] + price[QUARTER];
				//2. 현재달을 월별 금액으로 결제할 때
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