package a250227;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();


	// N은 주어지는 배열의 길이
	static int Row, Column;
	static boolean[][] map;
	
	public static void main(String[] args) throws IOException {
		Row = nextInt();
		Column = nextInt();
		
		map = new boolean[Row+2][Column+2];
		
		for(int r=1; r<=Row; r++) {
			// streamTokenizer을 이용한 String 읽기가 처음이다.
			// 오류가 날 가능성이 높은 부분 추후 파싱하기.
			st.nextToken();
			String line = st.
			System.out.println(line);
			for(int c=1; c<=Column; c++) {
				map[r][c] = line.charAt(c-1) == '.';
				if(!map[r][c]) { 
					System.out.println(r+", "+c);
				}
			}
		}
		// 입력을 통해 맵 생성
		
		
		
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}