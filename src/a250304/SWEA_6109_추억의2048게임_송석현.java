package a250304;
/*
 * SWEA 6109 추억의 2048
 * 
 * 대단한 로직 없이 정직하게 구현했습니다.
 * 4가지 방향이 주어지는데,
 * 2차원 배열의 조작을 하기 위해
 * up, down인 경우와
 * left, right인 경우를 나누었습니다.
 * 
 * 
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class SWEA_6109_추억의2048게임_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();
	

	// N은 주어지는 배열의 길이
	static int N;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
	
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			// 맵의 크기와 맵의 현 상태를 입력받습니다.
			N = Integer.parseInt(st.nextToken());
			String direction = st.nextToken();
			
			map = new int[N][N];
			
			for(int row=0; row<N; row++) {
				StringTokenizer line = new StringTokenizer(br.readLine());
				for(int col=0; col<N; col++) {
					map[row][col] = Integer.parseInt(line.nextToken());
				}
			}
			
			play(direction);

			
			//출력부분
			result.append("#").append(tc).append("\n");
			for(int row=0; row<N; row++) {
				for(int col=0; col<N; col++) {
					result.append(map[row][col]).append(" ");
				}
				result.append("\n");
			}
		}
			
		System.out.println(result);
	}
	
	static void play(String direction) {
		// 명령이 Up 또는 Down 인 경우
		int dRow=0, start=0, dCol=0;
		if(direction.equals("up")) {
			dRow=1; start=0;
			
		} else if(direction.equals("down")) {
			dRow=-1; start=N-1;
		}
		
		
		if(dRow != 0) {
			for(int col=0; col<N; col++) {
				int curRow = start;
				int nextRow = start+dRow;
				int curInt, nextInt;
				
				//curRow: 현재 이터레이션을 통해 값을 확정할 row
				//nextRow: 현재의 Row값과 상호작용이 있을 것으로 기대되는 탐색 row
				
				//현재 열에 대한 이동을 시작한다.
				while(nextRow<N && nextRow>=0){
					nextInt = map[nextRow][col];
					
					// 탐색열을 찾을 경우
					if(nextInt!=0) {
						curInt = map[curRow][col];
						
						//만약 현재 row가 0이라면(비어있다면)
						if(curInt==0) {
							map[curRow][col] = nextInt;
							map[nextRow][col] = 0;
						}
						
						// 만약 현재row와 탐색 row의 숫자가 동일하다면
						else if(nextInt == curInt) {
							map[curRow][col] *=2;
							curRow+=dRow;
							map[nextRow][col] = 0;
						}
						
						// 숫자가 서로 다른경우
						else {
							curRow+=dRow;
							// 탐색을 하자마자 바로 탐색row를 찾을 경우와
							// 1번이상의 iteration 후 탐색row를 찾을 경우를 나누어야 해서 이렇게 코드를 짰습니다.
							if(curRow!=nextRow) {
								map[curRow][col] = map[nextRow][col];
								map[nextRow][col] = 0;
							}
						}
					}
					// 다음 탐색 row
					nextRow+=dRow;
				}
			}
		}

		
		// left 또는 right 인 경우
		// 위와 동일하게 진행했습니다.
		else {
			if(direction.equals("left")) {
				dCol=1; start=0;
				
			} else if(direction.equals("right")) {
				dCol=-1; start=N-1;
			}
			if(dCol !=0) {
				for(int row=0; row<N; row++) {
					int curCol = start;
					int nextCol = start+dCol;
					int curInt, nextInt;
					
					while(nextCol<N && nextCol>=0){
						nextInt = map[row][nextCol];
						if(nextInt!=0) {
							curInt = map[row][curCol];
							if(curInt==0) {
								map[row][curCol] = nextInt;
								map[row][nextCol] = 0;
							}
							else if(nextInt == curInt) {
								map[row][curCol] *=2;
								curCol+=dCol;
								map[row][nextCol] = 0;
							}
							else {
								curCol+=dCol;
								if(curCol!=nextCol) {
									map[row][curCol] = map[row][nextCol];
									map[row][nextCol] = 0;
								}
							}
						}
						nextCol+=dCol;
					}
					
				}
			}
		}
		
	}


}