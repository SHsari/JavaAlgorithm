package a250226.swea2805harvest;

/* SWEA 2805 수확
 * 어렵지 않은 문제라 느꼈지만 어렵게 풀었따.
 * 
 * 밭은 무조건 정사각형이고
 * 밭의 한 변의 길이가 주어진다.
 * 
 * 정사각형에 들어가는 최대 마름모에 해당하는 칸에서 수확이 가능하다.
 * 
 */

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
			

			// 입력에서 바로 처리해보려 했으나 
			// 그냥 일단 다 받고
			for(int row=0; row<N; row++) {
				String line = br.readLine();
				for(int col=0; col<N; col++) {
					map[row][col] = line.charAt(col) - '0';
				}
			}
			
			int mid = N/2;
			int sum = map[mid][mid];
			
			// 이후 일종의 8방탐색을 통해서 Solve.
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
