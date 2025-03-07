package a250304;

/* BOJ 1074 Z
 *
 * 4등분 -> 재귀
 * 4등분시 1, 2, 3, 4사분면에 대한 값을 계산해서 더해줍니다.
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class BOJ_1074_Z_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();


	// N은 주어지는 배열의 길이
	static int N, R, C;
	
	public static void main(String[] args) throws IOException {
        N = nextInt();
        R = nextInt();
        C = nextInt();

		result.append(getZth(N, R, C));
			
		System.out.println(result);
	}

	/*
	 * 
	 */
    static int getZth(int n, int r, int c) {
		//기저조건
        if(n==0) return 0;

		// 2^(n-1) 을 통해서 4등분한 사각형의 변의 길이를 구해줍니다
        int diviend = (int)Math.pow(2, n-1);

		// 4등분 사각형이 포함하는 칸의 갯수를 구해줍니다.
		int block = diviend*diviend;

		// 4등분 사각형에서의 row와 column 값, 그리고 구해 재귀로 넘겨줍니다.(n-1에 대해 동일한 진행)
        int zth = getZth(n-1, r%diviend, c%diviend);
        
		// 4등분 시 사분면의 위치에 따라 값을 더해줍니다.
        if(r/diviend == 1) zth+=block*2;
        if(c/diviend == 1) zth+=block;
		
		return zth;
    }
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}