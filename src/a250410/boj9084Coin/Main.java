package a250410.boj9084Coin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Main {
	/*
	 * BOJ 9084 동전.
	 * 초딩때 지독하게 풀었던 것으로 기억한다.
	 * 동전 값이 이미 정렬된 상태로 주어진다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, coin[], subject, caseCntAt[]; 
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			init();
	
			result.append().append("\n");	
		}
		System.out.println(result);
	}
	
	static void dpdp() {
		// 첫번째 줄.
		for(int sub=0; sub<=subject; sub++) {
			caseCntAt[sub] = sub%coin[0]==0? 1 : 0;
		}
		
		for(int i=1; i<N; i++) {
			for(int sub=0; sub<=subject; sub++) {
				int count =0;
				int currValue=0;
				while(currValue<=subject) {
					
					
				}
			}
		}
	}
	
	static void init() throws IOException{
		N = nextInt();
		coin = new int[N];
		for(int i=0; i<N; i++) coin[i] = nextInt();
		subject = nextInt();
		caseCntAt = new int[subject+1];
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}