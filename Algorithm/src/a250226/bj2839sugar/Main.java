package a250226.bj2839sugar;

import java.io.*;
/* 8 이상의 자연수는 모두 3a+5b 형태로 나타낼 수 있다.
 * N%5 = 1인 경우
 * N%5 = 2인 경우 
 * N%5 = 3인 경우 5*(N/5) + 3*1 
 */


public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static int N;
	static final boolean[] isPossible = new boolean[8];
	static final int[] possibleList = {3, 5, 6};
	static {
		for(int posIdx : possibleList) {
			isPossible[posIdx] = true;
		}
	}
	
	public static void main(String[] args) throws IOException {
		N = nextInt();
		if(N<8 && !isPossible[N]) {
			System.out.println("-1");
			return;
		}
		
		
		
		int remainder = N%5;
		int count=0;
		if(remainder == 1 || remainder == 3) {
			count = N/5 +1;
		} else if(remainder == 2 || remainder == 4) {
			count = N/5 +2;
		}
		
		System.out.println(count);
		
	}
	
	

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int)st.nval;
	}

}
