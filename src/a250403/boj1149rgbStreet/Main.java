package a250403.boj1149rgbStreet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class Main {

	/*
	 * A형 화분문제를 푸는데 크나 큰 도움을 주었던 문제
	 * 두두등장
	 * 
	 * i=0일때,
	 * price[i][r] = nextInt();
	 * price[i][g] = nextInt();
	 * price[i][b] = nextInt();
	 * 
	 * 
	 * 이후 i에서 r, g, b를 각각 선택한 경우에 대해서
	 * price[i][r] = nextInt() + min(price[i-1][g], price[i-1][b]);
	 * 같은 식으로 진행합니다.
	 */
	static BufferedReader br;
	static StreamTokenizer st;
	
	static int N;
	static int prices[][];
	
	public static void main(String[] args) throws IOException {
		init();
		
		int minPrice = Integer.MAX_VALUE;
		
		// 모든 입력과 누적값에 대한 비교를 마친 후
		// 마지막 n-1 지점에서 모든 누적합 중 가장 적은 것을 출력합니다.
		for(int color=0; color<3; color++) {
			int price = prices[N-1][color];
			if(minPrice > price) minPrice = price;
		}

		System.out.println(minPrice);
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        prices = new int[N][3];
    	for(int color=0; color<3; color++) {
    		prices[0][color] = nextInt();
    	}
        
        for(int i=1; i<N; i++) {
        	for(int color=0; color<3; color++) {
        		int price = nextInt();
        		int diffColor1 = (color+1)%3;
        		int diffColor2 = (color+2)%3;
        		prices[i][color] = price +
        				Math.min(prices[i-1][diffColor1], prices[i-1][diffColor2]);
        	}
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}