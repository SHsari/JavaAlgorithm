package a250402.swea1953prisoner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import java.util.Queue;
import java.util.ArrayDeque;


public class Solution {
	/*
	 * SWEA 1953 탈주범 검거.
	 * 
	 * 지하 터널을 이동하는 탈주범이 어디 있을 수 있는지,
	 * 존재 가능한 장소의 갯수(범위)를 구하는 문제입니다.
	 * 
	 * BFS로 풀었습니다.
	 * 
	 * BFS 탐색의 조건:
	 * 1. map[nextRow][nextColumn] > 0 (다음 칸에 파이프가 놓여 있는 지)
	 * 2. isConnected(enterDirection, nextStructure)
	 * 	-> 다음칸의 하수도 구조를 고려했을 때, 진입 방향이 유효한지 확인합니다.
	 * 		direction 배열을  0:상, 1:좌, 2:하, 3:우 순서로 정의함으로써
	 *  structure가 포함하는 direction 중, 다음을 만족하는 것이 있으면 true를 반환합니다.
	 * 	abs(enterDirection - structureDirection) == 2
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	// 상좌하우 순서로 direction 정의
	static int[] dRow = {-1, 0, 1, 0};
	static int[] dCol = {0, -1, 0, 1};
	static int[][] structure;
	static{
		structure = new int[8][];
		structure[1] = new int[]{0, 1, 2, 3};
		structure[2] = new int[]{0, 2};
		structure[3] = new int[]{1, 3};
		structure[4] = new int[]{0, 3};
		structure[5] = new int[]{2, 3};
		structure[6] = new int[]{1, 2};
		structure[7] = new int[]{0, 1};
	}

	
	static int N, M, startRow, startColumn, passedTime;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			result.append(bfs()).append("\n");

		}
		System.out.println(result);
	}
	
	// 깊이와 탐색 너비를 고려하는 BFS 로직.
	static int bfs() {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();
		
		boolean[][] visited =  new boolean[N+2][M+2];
		//와나 1번점 visit 처리를 하지 않아서 계속 오류가 났군요.
		visited[startRow][startColumn] = true;
		rowQ.add(startRow); colQ.add(startColumn);
		
		int areaNum = 1;
		int depth = 1;
		while(!rowQ.isEmpty() && depth<passedTime) {
			// 깊이 count
			int qSize = rowQ.size();
			depth++;
			for(int i=0; i<qSize; i++) {
				
				int curRow = rowQ.poll();
				int curCol = colQ.poll();
				
				int curStr = map[curRow][curCol];
				
				//System.out.println(curRow + " " + curCol +"->"+ curStr);
				
				for(int dir : structure[curStr]) {
					int nextRow = curRow + dRow[dir];
					int nextCol = curCol + dCol[dir];
					int nextStr = map[nextRow][nextCol];
					
					// 다음칸이 미방문이고, 하수도가 놓여 있다면
					if(nextStr > 0 && !visited[nextRow][nextCol]) {
						// 다음칸의 구조가 진입 가능하다면
						if(isConnected(dir, nextStr)) {
							rowQ.add(nextRow); colQ.add(nextCol);
							visited[nextRow][nextCol] = true;
							// 탐색 너비 count
							areaNum++;
							//System.out.println(nextRow + " "+ nextCol);
						}
					}
				}
			}	
		}
		return areaNum;
	}
	
	
	//다음칸의 하수도 구조를 통해 진입방향이 유효한 지 확인합니다.
	static boolean isConnected(int entDir, int nextStr) {
		for(int dir : structure[nextStr]) {
			if(Math.abs(dir-entDir)==2) return true;
		}
		return false;
	}
	
	static void init() throws IOException {
		N = nextInt();
		M = nextInt();
		startRow = nextInt()+1;
		startColumn = nextInt()+1;
		passedTime = nextInt();
		
		map = new int[N+2][M+2];
		
		for(int r=1; r<=N; r++) {
			for(int c=1; c<=M; c++) {
				map[r][c] = nextInt();
			}
		}
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}