package a250409.swea4014mkAirstrip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, X, map[][];
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			int count = solve();
			result.append(count).append("\n");
			
		}
		System.out.println(result);
	}
	
	/*
	 * 경사로 설치 가능 조건
	 * 1: 높이 차이가 1
	 * 2: 설치 위치가 평탄.
	 * 
	 */
	
	static int solve() {
		
		int count = 0;
		
		// 우선 Horizontal 확인
		for(int r=0; r<N; r++) {
			boolean[] occupied = new boolean[N];
			
			int curCol = 1;
			for(; curCol<N; curCol++) {
				
				int diff = map[r][curCol] - map[r][curCol-1];
				if(diff == 0) continue;
				
				// 우상향하는 것을 놓아야 할 때.
				if(diff == 1) {
					//가장 좌측 자리부터 확인
					int stCol = curCol-X;
					if(stCol >= 0 && !occupied[stCol]) {
						while(map[r][stCol] == map[r][curCol-1] && stCol < curCol) {
							stCol++;
						}
					}
					// 확인했더니 가능한 경우
					if(stCol == curCol) Arrays.fill(occupied, curCol-X, curCol, true);
					// 불가능시 버리기
					else break;
				}
				
				// 좌상향 하는 것을 놓아야 할 때.
				else if (diff == -1){
					// 가장 우측 자리부터 확인
					int endCol = curCol+X-1;
					if(endCol < N) {
						while(map[r][endCol] == map[r][curCol] && endCol > curCol) {
							endCol--;
						}
					}
					// 확인했더니 가능한 경우
					if(endCol == curCol) {
						Arrays.fill(occupied,  curCol, curCol+X, true);
						curCol+=X;
					}
					// 불가능시 버리기.
					else break;
				}
				
				// 아예 불가능할 경우 이번 행은 버린다.
				else break;	
			}
			// 순회 후 활주로 건설이 가능한 경우
			if(curCol >= N) {
				

				count ++;
			}
		}
		
		
		
		// Vertical 확인
		for(int c=0; c<N; c++) {
			boolean[] occupied = new boolean[N];
			
			int curRow = 1;
			for(; curRow<N; curRow++) {
				
				int diff = map[curRow][c] - map[curRow-1][c];
				if(diff == 0) continue;
				
				// 우상향하는 것을 놓아야 할 때.
				if(diff == 1) {
					//가장 좌측 자리부터 확인
					int stRow = curRow-X;
					if(stRow >= 0 && !occupied[stRow]) {
						while(map[stRow][c] == map[curRow-1][c] && stRow < curRow) {
							stRow++;
						}
					}
					// 확인했더니 가능한 경우
					if(stRow == curRow) Arrays.fill(occupied, curRow-X, curRow, true);
					// 불가능시 버리기
					else break;
				}
				// 좌상향 하는 것을 놓아야 할 때.
				else if (diff == -1){
					// 가장 우측 자리부터 확인
					int endRow = curRow+X-1;
					if(endRow < N) {
						while(map[endRow][c] == map[curRow][c] && endRow > curRow) {
							endRow--;
						}
					}
					// 확인했더니 가능한 경우
					if(endRow == curRow) {
						Arrays.fill(occupied,  curRow, curRow+X, true);
						curRow += X;
					}
					// 불가능시 버리기.
					else break;
				}
				
				// 아예 불가능할 경우 이번 행은 버린다.
				else break;	
			}
			// 순회 후 활주로 건설이 가능한 경우
			if(curRow >= N) {
				count ++;
			}
		}
		
		return count;
	}
	
	static void init() throws IOException {
		N = nextInt();
		X = nextInt();
		
		map = new int[N][N];
		
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				map[r][c] = nextInt();
			}
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}