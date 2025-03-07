package a250227;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* BOJ 3109 빵집
 * 
 * DFS 최대 R^C 시간복잡도
 * 첨엔 그냥 while 문으로 돌렸다.
 * 무조건 위쪽을 우선으로 선택했고, 해당 선택이 틀렸어도 다른 선택지를 돌아보지 않는 로직
 * 안돼서 정답을 찾아뵜다.
 */
public class BOJ_3109_빵집_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	// N은 주어지는 배열의 길이4
	static int Row, Column, count;
	static boolean[][] map;

	public static void main(String[] args)throws IOException{
		StringTokenizer rc = new StringTokenizer(br.readLine());
		Row = Integer.parseInt(rc.nextToken());
		Column = Integer.parseInt(rc.nextToken());
		
		// 탐색시 ArrayOutofBounds 를 피하기 위해 위 아래로 false row 하나씩 붙여준다.
		map = new boolean[Row+2][Column];

		// 입력을 통해 맵 생성
		for(int r=1; r<=Row; r++) {
			String line = br.readLine();
			for(int c=0; c<Column; c++) {
				map[r][c] = line.charAt(c) == '.';
			}
		}

		count =0;

		for(int stRow=1; stRow<=Row; stRow++) {
			if(backTracking(stRow, 0)) count++;
		}
		
		System.out.println(count);

	}

	// 백트랙킹
	static boolean backTracking(int row, int stCol) {
		// 바로 방문처리를 해도 되는 것인지 고민이 좀 있었지만. 해결.
		map[row][stCol] = false;
		if(stCol == Column-1) return true;
		int col =stCol+1;

		boolean result = true;	
		//  위쪽부터 하나씩 해본다.
		if(map[row-1][col] && backTracking(row-1, col)){}
		else if(map[row][col] && backTracking(row, col)){}
		else if(map[row+1][col] && backTracking(row+1, col)){}
		else result = false;
		return result;
	}
}