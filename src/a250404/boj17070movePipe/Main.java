package a250404.boj17070movePipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Main {
	/*
	 * boj 17070 파이프 옮기기
	 * 
	 * 노골적인 DP 문제입니다.
	 * 
	 * 벽의 위치에 대해서 boolean isWall[][] 2차원 배열에 저장합니다.
	 * 
	 * 좌표 (r, c)에서, 
	 * Orientation H(Horizontal), V(Vertical), D(Diagonal)각각 의 상태로
	 * 진입할 수 있는 경로의 수
	 * 를 map[orientation][r][c]로 접근합니다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static int[][][] map;
	static int N;
	static boolean[][] isWall;
	static final int H=0, V=1, D=2;
	
	public static void main(String[] args) throws IOException {
		init();
		int result = move();
		System.out.println(result);
	}
	
	// 디버깅 위한 print 함수
	static void print() {
		for(int orient=0; orient<3; orient++) {
			for(int i=1; i<=N; i++) {
				System.out.println(Arrays.toString(map[orient][i]));
			}
			System.out.println();
		}
	}
	
	// 실수: map[H][r][c] = map[H][r][c-1] + map[D][r][c-1];
	// 여기서 덧셈할 좌표를 잘못써서 계속 틀렸습니다.
	static int move() {
		// 초기 위치 설정
		map[H][1][2] = 1;
		int startCol = 3;
		for(int r=1; r<=N; r++) {
			for(int c=startCol; c<=N; c++) {
				// 벽이 있는 곳이라면
				if(isWall[r][c]) continue;
				// 벽이 없다면
				else {
					// 가로, 세로 진행 가능
					map[H][r][c] = map[H][r][c-1] + map[D][r][c-1];
					map[V][r][c] = map[V][r-1][c] + map[D][r-1][c];
					
					// 대각선 진행시 추가로 확인.
					if(!isWall[r-1][c] && !isWall[r][c-1]) {
						map[D][r][c] = map[H][r-1][c-1]
								+ map[D][r-1][c-1] + map[V][r-1][c-1];
					}
				}
			}
		}
		
		int sumHVD =0;
		for(int i=0; i<3; i++) {
			sumHVD += map[i][N][N];
		} return sumHVD;
	}

	// 입력
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        map = new int[3][N+2][N+2];
        
        isWall = new boolean[N+2][N+2];
        Arrays.fill(isWall[0], true);
        Arrays.fill(isWall[N+1], true);
        
        for(int r=1; r<=N; r++) {
        	for(int c=1; c<=N; c++) {
        		isWall[r][c] = nextInt() == 1;
        	}
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}