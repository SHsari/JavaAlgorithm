package a250306.swea7733cheeseThief;
/*
 * SWEA 7733 치즈도둑
 * 
 * 정석적인 BFS문제라고 생각합니다.
 * 매일매일 요정에 의하여 CheeseMap의 현황이 변하고,
 * 매일 BFS를 통해 CheeseMap의 Area 갯수를 구합니다.
 * 
 * 한가지 특이한 점은
 * 맛 점수를 통해서 좌표 리스트를 받아올 수 있도록 하였습니다.
 * eg.
 * tasteToRC[점수] : 점수에 해당하는 좌표 List
 * 좌표는 Pair class를 선언하여 사용했습니다.
 * 
 */

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;

public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

	// N*N 크기의 치즈맵
	static int N;
	static boolean[][] cheese;
	// 맛 점수 -> 좌표 리스트 접근하는 List<Pair> 배열
	static List<Pair>[] tasteToRC;
	
	//방향 배열
	static final int[] dRow = {0,1,0,-1};
	static final int[] dCol = {1,0,-1,0};
	
	//맛 점수의 최대, 최소값을 통해 전체 BFS 횟수를 줄입니다.
	
	static int tasteMax, tasteMin;
	
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			// 입력완료
			
			// 기본상태에서 Area 갯수는 1입니다.
			int maxArea = 1;
			for(int day=tasteMin; day<=tasteMax; day++) {
				dayIter(day);
				int area = countArea();
				if(area>maxArea) maxArea = area;
			}		
			
			result.append(maxArea).append("\n");
		}
			
		System.out.println(result);
	}
	
	
	
	// 입력받는 함수, init
	// List<Pair> 타입의 배열을 선언하고 초기화 하는 과정으로 인해
	// SuppressWarnings 해줬습니다.
	@SuppressWarnings("unchecked")
	static void init() throws IOException {
		N = nextInt();
		
		tasteMax = 0;
		tasteMin = Integer.MAX_VALUE;
		
		cheese = new boolean[N+2][N+2];
		
		tasteToRC = (List<Pair>[]) new List[101];
		for(int i=0; i<101; i++) {
			tasteToRC[i] = new ArrayList<>();
		}
		
		for(int row=1; row<=N; row++) {
			Arrays.fill(cheese[row], true);
			cheese[row][0] = false;
			cheese[row][N+1] = false;
			for(int col=1; col<=N; col++) {
				 int tasteRC = nextInt();
				 tasteToRC[tasteRC].add(new Pair(row, col));		
				 if(tasteRC < tasteMin) tasteMin = tasteRC;
				 if(tasteRC > tasteMax) tasteMax = tasteRC;
			}
		}
	}
	
	/*
	 * 모든 가능한 지점에 대해 BFS 탐색을 통해 AREA의 갯수를 반환하는 함수
	 */
	static int countArea() {
		int count=0;
		boolean [][] visited = new boolean[N+2][N+2];
		for(int row=1; row<=N; row++) {
			for(int col=1; col<=N; col++) {
				if(!visited[row][col] && cheese[row][col]) {
					count++;
					bfs(row, col, visited);
				}
			}
		}
		return count;
	}
	
	
	/* 오직 CountArea 에서만 호출되는 함수 BFS
	 * BFS
	 */
	static void bfs(int row, int col, boolean[][] visited) {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();
		
		rowQ.add(row);
		colQ.add(col);
		visited[row][col] = true;
		
		while(!rowQ.isEmpty()) {
			int curRow = rowQ.poll();
			int curCol = colQ.poll();
			
			// 4방탐색
			for(int dir=0; dir<4; dir++) {
				int nextRow = curRow + dRow[dir];
				int nextCol = curCol + dCol[dir];
				
				// 방문하지 않고, 요정이 갉아먹지 않은 구역이라면
				if(!visited[nextRow][nextCol] && cheese[nextRow][nextCol]) {
					rowQ.add(nextRow);
					colQ.add(nextCol);
					visited[nextRow][nextCol] = true;
				}
			}
		}
	}
	
	/*
	 * day에 대해서 요정이 갉아먹었음을 cheeseMap에 표시해주는 함수
	 */
	static void dayIter(int day) {
		for(Pair p : tasteToRC[day]) {
			cheese[p.row][p.col] = false;
		}
	}
	
	
	/*
	 * 좌표를 저장하기 위한 Pair Class
	 */
	static class Pair {
		int row, col;
		Pair(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}