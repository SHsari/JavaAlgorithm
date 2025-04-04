package a250404.boj1010mkBridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class Main {

	static BufferedReader br;
	static StreamTokenizer st;
	
	public static void main(String[] args) throws IOException {
		/*
		 * boj 1010 다리놓기.
		 * 파스칼의 삼각형으로 풀이했습니다.
		 */
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        StringBuilder result = new StringBuilder();
        
        int[][] pascal = new int[30][30];
        pascal[0][0] = 1;
        pascal[1][0] = pascal[1][1] = 1;
        int count=2;
        
		int T = nextInt();
		
		for(int tc=0; tc<T; tc++) {
			int n = nextInt();
			int m = nextInt();
			// 이미 계산된 값이 있다면
			if(m<count) {
				result.append(pascal[m][n]).append("\n");
			}
			// 계산 범위 밖이라 새로 해야한다면
			else {
				for(; count<=m; count++) {
					pascal[count][0]=1;
					for(int c=1; c<=count; c++) {
						pascal[count][c] = pascal[count-1][c-1] + pascal[count-1][c];
					}
				}
				result.append(pascal[m][n]).append("\n");
			}
		}
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}