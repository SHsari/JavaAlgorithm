package a250306.swea1868popping;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/* SWEA1868 파핑파핑지뢰찾기
 * 
 * 까다로운 BFS 문제로 보입니다.
 * 
 * 지뢰찾기의 룰을 차용하고 있습니다.
 * 
 * 주어진 지뢰 map에 따라 성공할 수 있는 최소 클릭 수를 찾는 문제입니다.
 * 
 * 1. map을 이중for문으로 모두 순회하면서
 * 8방탐색하여 주변에 지뢰가 없는 곳에서 BFS 탐색을 시작하고 Click수에 추가합니다.
 * 
 * 2. 이후 탐색되지 않은 안전한 공간의 갯수를 Click 수에 더해줍니다.
 * 
 */
public class Solution { 

	static BufferedReader br;
	static StringBuilder result;
	
	static final char NOMINE = '.';
	static final char MINE = '*';
	static final char END = '#';
	static final char EXPOSED = 'e';

	static final int[] dRow = {-1,0,1,1,1,0,-1,-1};
	static final int[] dCol = {-1,-1,-1,0,1,1,1,0};
	
	// N은 주어지는 배열의 길이
	static int N;
	static char[][] map;
	
	
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        result = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			// 입력
			init();
		
			// 클릭수 세기
			int clickCount=0;
			
			// 먼저 확장가능한 곳 ( 지뢰가 주변에 없는곳 )우선으로 BFS 탐색 & 클릭수 세기
			for(int row=1; row<=N; row++) {
				for(int col=1; col<=N; col++) {
					if(map[row][col]==NOMINE && isExpandable(row, col)) {
						clickCount++;
						bfs2(row,col);
					}
				}
			}
			
			// 이후 남아있는 곳의 갯수 세기.
			for(int row=1; row<=N; row++) {
				for(int col=1; col<=N; col++) {
					if(map[row][col]==NOMINE) {
						clickCount++;
					}
				}
			}
			
			result.append(clickCount).append("\n");
	
		}
			
		System.out.println(result);
	}
	
	/*
	 * 맵 입력 함수
	 */
	static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new char[N+2][N+2];
		
		//양끝을 지뢰로 채우기
		Arrays.fill(map[0], END);
		Arrays.fill(map[N+1], END);
		
		for(int i=1; i<=N; i++) {
			StringBuilder line = new StringBuilder();
			line.append(END);
			line.append(br.readLine()).append(END);			
			map[i] = line.toString().toCharArray();
		}
	}
	
	// 정답 BFS
	static void bfs2(int row, int col) {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();

		
		rowQ.add(row);
		colQ.add(col);
		map[row][col] = EXPOSED;
		
		while(!rowQ.isEmpty()) {
			int curRow = rowQ.poll();
			int curCol = colQ.poll();
			
			//현위치 주변에 지뢰가 없다면
			// 주변의 8곳의 좌표 모두 Queue에 넣어줍니다. (자동클릭)
			if(isExpandable(curRow, curCol)) {
				for(int dir=0; dir<8; dir++) {
					int nextRow = curRow + dRow[dir];
					int nextCol = curCol + dCol[dir];
					if(map[nextRow][nextCol] == NOMINE) {
						rowQ.add(nextRow);
						colQ.add(nextCol);
						map[nextRow][nextCol] = EXPOSED;
					}
					
				}
			} 
		}
	}
	
	/*
	 * 아주 유사하지만 잘못된 로직입니다.
	 * 정답보다 아주 조금 적게 Click수를 셉니다. 에지 케이스가 존재합니다.
	static void bfs(int row, int col) {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();
		Queue<Boolean> expandableQ = new ArrayDeque<>();
		
		rowQ.add(row);
		colQ.add(col);
		expandableQ.add(isExpandable(row, col));
		map[row][col] = EXPOSED;
		
		while(!rowQ.isEmpty()) {
			int curRow = rowQ.poll();
			int curCol = colQ.poll();
			boolean curExpandability = expandableQ.poll();
			
			if(curExpandability) {
				for(int dir=0; dir<8; dir++) {
					int nextRow = curRow + dRow[dir];
					int nextCol = curCol + dCol[dir];
					if(map[nextRow][nextCol] == NOMINE) {
						rowQ.add(nextRow);
						colQ.add(nextCol);
						expandableQ.add(isExpandable(nextRow, nextCol));
						map[nextRow][nextCol] = EXPOSED;
					}
					
				}
			} else {
				for(int dir=0; dir<8; dir++) {
					int nextRow = curRow + dRow[dir];
					int nextCol = curCol + dCol[dir];
					
					if(map[nextRow][nextCol] == NOMINE) {
						boolean nextExpandability = isExpandable(nextRow, nextCol);
						if(nextExpandability) {
							rowQ.add(nextRow);
							colQ.add(nextCol);
							expandableQ.add(nextExpandability);
							map[nextRow][nextCol] = EXPOSED;
						}
					}
				}
			}
		}
	}
	
	*/
	
	/*
	 * 입력받은 좌표에 대해 8방탐색하여 주변에 지뢰가 있는 지 없는 지 확인하는 함수입니다.
	 */
	static boolean isExpandable(int row, int col) {
		for(int dir=0; dir<8; dir++) {
			int tRow = row + dRow[dir];
			int tCol = col + dCol[dir];
			if(map[tRow][tCol] == MINE) return false;
		}
		return true;
	}
}