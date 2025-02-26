package a250226.swea2805harvest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();

	static int N;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			N = Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			
			for(int row=0; row<N; row++) {
				String line = br.readLine();
				for(int col=0; col<N; col++) {
					map[row][col] = line.charAt(col) - '0';
				}
			}
			
			int mid = N/2;
			int sum = map[mid][mid];
			
			for(int offset=1; offset<=mid; offset++) {
				for(int rowOffset=1; rowOffset<offset; rowOffset++) {
					int colOffset = offset-rowOffset;
					sum += map[mid+rowOffset][mid+colOffset];
					sum += map[mid+rowOffset][mid-colOffset];
					sum += map[mid-rowOffset][mid+colOffset];
					sum += map[mid-rowOffset][mid-colOffset];
				}
				sum += map[mid+offset][mid];
				sum += map[mid-offset][mid];
				sum += map[mid][mid+offset];
				sum += map[mid][mid-offset];
			}
			
			
			
			result.append("#").append(tc).append(" ");
			result.append(sum).append("\n");
		}
		System.out.println(result);
	}

}
