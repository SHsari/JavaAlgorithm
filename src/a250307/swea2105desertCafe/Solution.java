package a250307.swea2105desertCafe;
/*
 * SWEA_2105_디저트카페
 * 
 * 
 * 맵의 크기는 N*N;
 * 맵의 각 칸마다 숫자가 써있고,
 * 겹치지 않는 숫자를 밟고 사각형 경로를 완성해라.
 * 숫자의 범위는 1~100
 * visited 1~100 불리언 배열을 통해서 관리.
 * 
 * 같은 지점을 밟는다면 경로의 순서는 상관이 없기 때문에
 * 한 방향으로만 경로 탐색을 진행합니다.
 * 
 * 저는 (오른쪽, 하향) 으로 시작하여  시계 방향으로 도는 경로만을 탐색합니다.
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

	// N은 주어지는 배열의 길이
	static int N;
	static boolean[] visited;
	//static boolean[][] visitedMap;
	static int[][] map;

	static int maxTravelCount;
	static int startRow, startCol;
	
	//우하, 우상, 좌상, 좌하
	static int[] dRow = {1, -1, -1, 1}; 
	static int[] dCol = {1, 1, -1, -1};
	

	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
     
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			maxTravelCount=-1;

			N = nextInt();
			map = new int[N+2][N+2];
			
			for(int row=1; row<=N; row++) {
				for(int col=1; col<=N; col++) {
					map[row][col] = nextInt();
				}
			}
			
			for(startRow=2; startRow<N; startRow++) {
				for(startCol=1; startCol<=N-2; startCol++) {
					travel1(startRow, startCol);
				}
			}
			
			result.append(maxTravelCount).append("\n");
		}
			
		
		System.out.println(result);
	}
	
	

/*
 *  우 하향하는 첫번째 경로 탐색.
 */
	static void travel1(int curRow, int curCol) {
		boolean[] visited = new boolean[101];
		visited[map[curRow][curCol]] = true;
		
		int dr = dRow[0];
		int dc = dCol[0];
		
		int nextRow = curRow+dr;
		int nextCol = curCol+dc;
		
		// 우 하향으로 이동할 수 있는 최대 거리를 계산합니다.
		while(nextRow<=N && nextCol<N) {
			if(visited[map[nextRow][nextCol]]) break;
			else {
				visited[map[nextRow][nextCol]] = true;
				nextRow += dr;
				nextCol += dc;
			}
		}
		nextRow -= dr;
		nextCol -= dc;
		
		// 최대 지점부터 하나씩 줄여가면서 다음 방향으로 탐색을 진행합니다.
		while(nextRow>curRow) {
			travel2(nextRow, nextCol, Arrays.copyOf(visited, 101));

			visited[map[nextRow][nextCol]] = false;

			nextRow -= dr;
			nextCol -= dc;
		}
	}
	
	/* 
	 * 우 상향하는 두번째 경로.
	 * 특이점은 두번째 경로에 의해서 사각형이 완성되기 때문에, 
	 * 마지막 경로가 될 좌 하향하는 경로도 동시에 visited 검사를 합니다.
	 */
	static void travel2(int curRow, int curCol, boolean[] visited) {
		int dr = dRow[1];
		int dc = dCol[1];
		
		int nextRow = curRow+dr;
		int nextCol = curCol+dc;
		int symRow = startRow+dr;
		int symCol = startCol+dc;
		
		// 최대 사각형의 너비를 계산합니다.
		while(symRow>=1 && nextCol<=N) {
			int next = map[nextRow][nextCol];
			int sym = map[symRow][symCol];
			if(next==sym || visited[next] || visited[sym]) break;
			else {
				visited[next] = true;
				visited[sym] = true;

				nextRow += dr;
				nextCol += dc;
				symRow += dr;
				symCol += dc;
			}
		}
		nextRow -= dr;
		nextCol -= dc;
		symRow -= dr;
		symCol -= dc;	

		while(nextCol>curCol) {
			// 최대 사각형 지점에서 하나씩 줄여가면서 탐색합니다.
			// 이 두번째 지점에 의해서 사각형이 결정되기 때문에 경로 길이값을 구할 수 있습니다.
			// 최대 탐색거리보다 예측경로길이가 짧다면 더이상 탐색을 진행하지 않습니다.
			int estimatedTravelCount = 2 * (nextCol - startCol);
			if(estimatedTravelCount <= maxTravelCount) return;
			travel3(nextRow, nextCol, symRow, symCol, Arrays.copyOf(visited, 101));
			visited[map[nextRow][nextCol]] = false;
			visited[map[symRow][symCol]] = false;

			nextRow -= dr;
			nextCol -= dc;
			symRow -= dr;
			symCol -= dc;
		}

	}
	
	/*	첫번째 travel의 반대편에 위치하는 경로 travel 3 입니다.
	 *  열린사각형의 마지막 변을 채웁니다. 뚜껑을 닫는 느낌
	 */
	static void travel3(int curRow, int curCol, int symRow, int symCol, boolean[] visited) {
		int dr = dRow[2];
		int dc = dCol[2];
		
		int nextRow = curRow+dr;
		int nextCol = curCol+dc;
		while(nextRow > symRow) {
			if(visited[map[nextRow][nextCol]]) return;
			else {
				visited[map[nextRow][nextCol]] = true;
				nextRow += dr;
				nextCol += dc;
			}
		}
		
		// 탐색이 완료되었다면 값 업데이트.
		// 이미 예측 경로로 pruning 된 결과이기 때문에 검사 없이 바로 업데이트 합니다.
		maxTravelCount = 2 * (curCol - startCol);

	}
	
//
//	static void print() {
//		for(int i=1; i<=N; i++) {
//			for(int j=1; j<=N; j++) {
//				System.out.print((visitedMap[i][j]? 1 : 0 ) + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}