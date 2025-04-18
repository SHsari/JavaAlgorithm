package a250410.boj9084Coin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Main_TIMEOUT {
	/*
	 * BOJ 9084 동전.
	 * 초딩때 지독하게 풀었던 것으로 기억한다.
	 * dfs를 이용한 완탐이고,
	 * 동전 값이 이미 정렬된 상태로 주어진다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, value[], subject, count; 
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			init();
			dfs(0, subject);
			result.append(count).append("\n");	
		}
		System.out.println(result);
	}
	
	static void dfs(int depth, int localSubject) {
		if(localSubject==0) {
			count++; return;
		}
		if(depth==N) return;
		
		int coin = value[depth];
		int curValue=0;
		while(curValue <= localSubject) {
			dfs(depth+1, localSubject - curValue);
			curValue += coin;
		}
	}
	
	static void init() throws IOException{
		N = nextInt();
		value = new int[N];
		for(int i=1; i<=N; i++) value[N-i] = nextInt();
		subject = nextInt();
		count=0;
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}