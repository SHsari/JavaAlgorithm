package a250401.boj14502lab;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;


public class Main {
	/*
	 * BOJ 14502 연구소.
	 * 
	 * BFS 적극사용?인가?
	 * 
	 * 지도의 세로 크기 N과 가로 크기 M
	 *  (3 ≤ N, M ≤ 8)
	 *  
	 *  0은 빈 칸, 1은 벽, 2는 바이러스가 있는 위치
	 *  
	 *  최대 24칸의 지도.
	 *  64*64*64= 2^18  ~~ 10^6
	 * 
	 *  bfs -> 64
	 *
	 * 1. 지도에서 벽을 세울 3점을 정함
	 * 	1-1 3개의 점에 Visited[r][c] = true 통해서 BFS 불가능하도록 설정
	 * 2. 각 바이러스의 위치에서부터 BFS
	 *  2-1	BFS를 통해 퍼지는 공간의 너비 구하기
	 * 3. 전체 너비에서 바이러스의 너비를 빼고 해당 벽 위치에 대한 safeArea를 구함.
	 * 4. maxSafeArea 업데이트하기
	 *
	 * 모든 벽의 위치의 경우의 수에 대해 1~4 과정 반복. 
	 *
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	
	static int N, M, minVirusArea;
	static int[][] map;
	static int wallCnt, virusCnt;
	static int[] virusR = new int[10];
	static int[] virusC = new int[10];
	
	static int[] dRow = {0,1,0,-1};
	static int[] dCol = {1,0,-1,0};
	
	public static void main(String[] args) throws IOException {
		init();
		selectWallAndSimulate();
		int maxSafeArea = N*M - wallCnt - minVirusArea;
		System.out.println(maxSafeArea);
	}
	
	/* 3중for문으로 벽을 세울 좌표를 정합니다.
	// N*M개의 좌표 중 3개의 점을 정하는 조합입니다.
	// 벽의 위치는 하나의 정수로 특정합니다.
	// int wall = row*M + column;
	*/
	static void selectWallAndSimulate() {
		for(int i1=1; i1<=N*M; i1++) {
			if(!isWallPossible(i1)) continue;
			for(int i2=i1+1; i2<=N*M; i2++) {
				if(!isWallPossible(i2)) continue;
				for(int i3=i2+1; i3<=N*M; i3++) {
					if(!isWallPossible(i3)) continue;
					int virusArea = simulate(i1, i2, i3);
					if(minVirusArea > virusArea) minVirusArea = virusArea;
				}
			}
		}
	}

	// w1, w2, w3의 값은 새로 벽을 세울 위치의 좌표입니다. 
	// N*M의 맵에서 row, column 값을 정수 하나로 인코딩? 한 것입니다.
	static int simulate(int w1, int w2, int w3) {
		// c1, c2, c3는 row, column 값 페어입니다.
		// w1~w3의 값으로부터 디코딩하여 얻어냅니다.
		int[] c1 = getPairFromCode(w1);
		int[] c2 = getPairFromCode(w2);
		int[] c3 = getPairFromCode(w3);
		
		int virusArea = virusCnt;
		
		boolean[][] visited = new boolean[N+2][M+2];
		visited[c1[0]][c1[1]] = true;
		visited[c2[0]][c2[1]] = true;
		visited[c3[0]][c3[1]] = true;
		for(int vIdx=0; vIdx<virusCnt; vIdx++) {
			virusArea += bfs(visited, virusR[vIdx], virusC[vIdx]);
		}
		return virusArea;
	}
	
	// BFS 넓이를 반환하는 BFS 함수.
	static int bfs(boolean[][] visited, int vRow, int vCol) {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();
		
		rowQ.add(vRow); colQ.add(vCol);
		
		int bfsArea=0;
		while(!rowQ.isEmpty()) {	
			int curRow = rowQ.poll();
			int curCol = colQ.poll();
			for(int dir=0; dir<4; dir++) {
				int nextRow = curRow + dRow[dir];
				int nextCol = curCol + dCol[dir];
				if(map[nextRow][nextCol] == 0 && !visited[nextRow][nextCol]) {
					visited[nextRow][nextCol] = true;
					bfsArea++;
					rowQ.add(nextRow);
					colQ.add(nextCol);
				}
			}
		}
		return bfsArea;
	}
	
	// 해당코드의 좌표가 벽을 세울 수 있는 빈공간인지 확인
	static boolean isWallPossible(int code) {
		int[] pair = getPairFromCode(code);
		return map[pair[0]][pair[1]] == 0;
	}
	
	// 좌표코드(int) -> 좌표(int, int)
	static int[] getPairFromCode(int code) {
		code --;
		int row = code/M + 1;
		int col = code%M + 1;
		int[] pair = {row, col};
		return pair;
	}


	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
		minVirusArea = Integer.MAX_VALUE;
        N = nextInt();
        M = nextInt();
        
        map = new int[N+2][M+2];
        
        Arrays.fill(map[0], 1);
        Arrays.fill(map[N+1], 1);
        
        virusCnt =0;
        wallCnt =3;
        for(int r=1; r<=N; r++) {
        	map[r][0] = map[r][M+1] = 1;
        	for(int c=1; c<=M; c++) {
        		int tmp = nextInt();
        		map[r][c] = tmp;
        		if(tmp==0) continue;
        		else if(tmp==1) wallCnt++;
        		else {
        			virusR[virusCnt] = r;
        			virusC[virusCnt] = c;
        			virusCnt++;
        		}
        	}
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}