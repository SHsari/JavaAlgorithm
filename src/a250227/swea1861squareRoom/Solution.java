package a250227.swea1861squareRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Arrays;

/* 숫자로 이뤄진 2차원 맵이 이뤄지고
 * 사방탐색으로 이동이 가능하며
 * 거기에 +1인 곳만 이동이 가능하다는 조건 추가.
 * 가장 많은 이동을 할 수 있는 시작점을 찾는 문제 
 * 
 * 일단 양적 접근이 중요하니까
 * 최적화 없이
 * 간단히 BFS나 DFS 로 풀어보자
 * 
 */

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	// map은 input을 받을 배열
	// roomset은 BFS시, 일종의 visited 배열로 쓸 것이다.
	// set Number를 부여하여 visited 여부를 확인할 것.
	static int[][] map, roomSet;
	
	//사방탐색을 위한 방향벡터
	static final int[] dROW = {0, 1, 0, -1};
	static final int[] dCOL = {1, 0, -1, 0};
	
	// N은 주어지는 배열의 길이.
	static int N, maxRoomCount, maxStartRoomNum;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			N = nextInt();
			
			//사방탐색시 어레이 인덱스 조건을 없애기 위해 N+2 크기의 배열 선언
			map = new int[N+2][N+2];			
			roomSet = new int[N+2][N+2];
			//-1로 초기화 하여 indexOutofBounds 에러 방지
			for(int i=0; i<N+2; i++) Arrays.fill(map[i], -1);
			
			// 입력받기
			for(int row=1; row<=N; row++) {
				for(int col=1; col<=N; col++) {
					map[row][col] = nextInt();
				}
			}
			
			// BFS 전 결과값 초기화
			maxRoomCount = 0;
			maxStartRoomNum = Integer.MAX_VALUE;
			// 방들의 집합 번호를 부여할 것이다.
			int setNum = 1;
			
			for(int row=1; row<=N; row++) {
				for(int col=1; col<=N; col++) {
					//방문 하지 않은 방이 있을 경우
					// 새로운 Set을 찾은 상황이며 BFS 수행하고 setNum++
					if(roomSet[row][col] == 0) {
						roomDivider(row, col, setNum++);
					}
				}
			}
			result.append("#").append(tc).append(" ");
			result.append(maxStartRoomNum).append(" ");
			result.append(maxRoomCount).append("\n");
			
		}
			
		System.out.println(result);
	}
	
	
	/* 
	 * BFS
	 */
	static void roomDivider(int stRow, int stCol, int setNum) {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();
		int roomCount =1;
		rowQ.add(stRow);
		colQ.add(stCol);
		int startNum = map[stRow][stCol];
		roomSet[stRow][stCol] = setNum;
		while(!rowQ.isEmpty()) {
			
			int row = rowQ.poll();
			int col = colQ.poll();
			
			int cur = map[row][col];
			if(startNum > cur) startNum = cur;
			
			// 4방탐색
			int validCount =0;
			for(int dir=0; dir<4; dir++) {
				int nRow = dROW[dir] + row;
				int nCol = dCOL[dir] + col;
				
				// 방문을 안했다면
				if(roomSet[nRow][nCol] == 0) {
					int diff = Math.abs(map[nRow][nCol] - cur);
					
					//차이가 1일때, 즉, 하나의 셋인경우 ( 4방탐색 중 유이하다. )
					if(diff == 1) { 
						// 방문하지 않은 곳이라면
						if(roomSet[nRow][nCol] == 0) {
							roomSet[nRow][nCol] = setNum;
							rowQ.add(nRow);
							colQ.add(nCol);
							roomCount++;
						}
						//두 지점을 모두 찾았다면
						if(++validCount == 2) break;
					}
				}
			}
		}
		if(roomCount>maxRoomCount) {
			maxRoomCount = roomCount;
			maxStartRoomNum = startNum;
		}
		else if(roomCount == maxRoomCount)  {
			maxStartRoomNum = (startNum < maxStartRoomNum)? startNum : maxStartRoomNum;
		}
	}
	
	
	
	/*
	 * dfs 코드..
	 * 근데 재귀 깊이가 너무 깊어질 것 같다. BFS로 바꾸는게 좋을 듯.
	 */
	@Deprecated
	static void roomDividerDFS(int row, int col, int setNum) {

		int cur = map[row][col];
		for(int dir=0; dir<4; dir++) {
			int nRow = dROW[dir] + row;
			int nCol = dCOL[dir] + col;
			
			int diff = Math.abs(map[nRow][nCol] - cur);
			//차이가 1로, 하나의 셋인경우 ( 4방탐색 중 유이하다. )
			if(diff == 1) {
				// 방문하지 않은 곳이라면
				if(roomSet[nRow][nCol] == 0) {
					roomSet[nRow][nCol] = setNum;
					roomDivider(nRow, nCol, setNum);
				}
				
			}
		}
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}