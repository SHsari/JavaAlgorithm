package a250408.swea5650pinball;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Solution {
	/*
	 * swea 5650 핀볼
	 * 
	 * 1. 비어있는 모든 좌표에 대해서 모든 방향으로 시뮬레이션 하여 최대 점수를 구합니다.
	 * 
	 * 
	 * - 시뮬레이션 -
	 * 매순간 row, column, direction 세개의 변수로 상태가 결정됩니다.
	 *  -> 메모이제이션 가능하네..
	 * dir 변수는 0, 1, 2, 3값을 가지며 
	 * 각각  우, 하, 좌, 상에 대응됩니다.
	 * 
	 *  - 벽에 부딫히는 경우
	 *  벽은 1~5번까지의 종류가 있습니다.벽은 방향을 바꿉니다.
	 *  변환된 방향 nextDirection 은 다음과 같이 구할 수 있습니다.
	 *  nextDirection = wall[벽번호][진입Direction]
	 * 
	 *  - 이외의 경우 뭐 없습니다.
	 */
	
	static class WormHole {
		int row, col;
		WormHole(int r, int c) {
			this.row = r; this.col = c;
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N;
	static int[][] map;
	
	// memo[row][column][dir] 로
	// 해당 상태에 대한 최종점수를 알 수 있으면 좋겠따 추후 구현.
	static int[][][] memo;
	
	// 웜홀입니다.
	static WormHole[] wHoles;
	
	// 0, 1, 2, 3 : 우, 하, 좌, 상
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0 ,-1, 0};
	
	// 벽의 번호를 i라고 할때,
	// int nextDir = wall[i][진입dir];
	// 을 통해서 벽의 부딫힌 이후의 방향을 얻을 수 있습니다.
	static int[][] wall = {
			{},
		{2, 0, 3, 1},
		{2, 3, 1, 0},
		{1, 3, 0, 2},
		{3, 2, 0, 1},
		{2, 3, 0, 1}
	};
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			init();
			int maxScore = 0;
			// 모든 맵 위의 좌표에 대해서
			for(int r=1; r<=N; r++) {
				for(int c=1; c<=N; c++) {
					// 해당 좌표가 빈공간이라면
					if(map[r][c] == 0) {
						// 모든 방향에 대해서 시뮬레이션
						for(int dir=0; dir<4; dir++) {
							int score = simulateAt(r, c, dir);
							if(score > maxScore) maxScore = score;
						}
					}
				}
			}
			result.append(maxScore).append("\n");
		}
		System.out.println(result);
	}
	
	/*
	 *  최대 100*100*4 만큼 호출
	 */
	static int simulateAt(int startRow, int startCol, int startDir) {
		int score=0;
		int r = startRow, c = startCol, dir = startDir;
		
		while(true) {
			// 방향에 따라서 다음 위치로 한칸 이동
			r += dRow[dir];
			c += dCol[dir];
			int next = map[r][c];
			
			//1. 출발한 곳으로 돌아왔다면 종료
			if(r==startRow && c==startCol) return score;
			//2. 0이라면 그대로 진행
			else if(next==0) continue;
			//3. 블랙홀이라면 종료
			else if(next < 0) { return score; }
			//4. 벽인 경우 방향전환, 점수 추가.
			else if(next < 6) {
				dir = wall[next][dir];
				score++;
			}
			//5. 웜홀이라면, 짝 웜홀로 위치 변경
			// 짝 웜홀에 next-1	로 접근.
			// 6~10번 -> 5~9번
			else if(next<11){
				WormHole wh = wHoles[next-1];
				r=wh.row; c=wh.col;
			} 
			//6. 5번과 동일. 짝 웜홀에 next-11로 접근
			// 11~15번 -> 0~4번
			else if(next<16) {
				WormHole wh = wHoles[next-11];
				r=wh.row; c=wh.col;
			}
		}
	}
	
	static void init() throws IOException {
		N = nextInt();
		map = new int[N+2][N+2];
		wHoles = new WormHole[10];
		Arrays.fill(map[0], 5);
		Arrays.fill(map[N+1], 5);
		
		
		for(int r=1; r<=N; r++) {
			map[r][0] = map[r][N+1] = 5;
			for(int c=1; c<=N; c++) {
				int tmp = nextInt();
				map[r][c] = tmp;
				
				// 웜홀인경우
				if(tmp > 5) {
					// 웜홀인덱스
					int whIdx = tmp-6;
					// 해당 인덱스 자리가 비어있다면
					if(wHoles[whIdx] == null) {
						wHoles[whIdx] = new WormHole(r, c);
					}
					// 이미 등록이 되어있다면, 짝 index에 새로운 웜홀 등록.
					else {
						whIdx += 5;
						wHoles[whIdx] = new WormHole(r,c);
						map[r][c] += 5;
					}
				}
			}
		}
		
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}