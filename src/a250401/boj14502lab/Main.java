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
	 *  24*24*24= 13824
	 *
	 *
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	
	static int N, M;
	static int[][] map;
	static int wallCnt, virusCnt;
	static int[] vx = new int[10];
	static int[] vy = new int[10];
	
	static int[] dRow = {0,1,0,-1};
	static int[] dCol = {1,0,-1,0};
	
	public static void main(String[] args) throws IOException {

			

	}
	
	static int simulate(int w1, int w2, int w3) {
		int[] c1 = getPairFromCode(w1);
		int[] c2 = getPairFromCode(w2);
		int[] c3 = getPairFromCode(w3);
		
		int virusArea = virusCnt;
		
		for(int vIdx=0; vIdx<virusCnt; vIdx++) {
			boolean[][] visited = new boolean[N+2][M+2];
			visited[c1[0]][c1[1]] = true;
			visited[c2[0]][c2[1]] = true;
			visited[c3[0]][c3[1]] = true;
			
			virusArea += bfs(visited, vx[vIdx], vy[vIdx]);
		}
		
		int safeArea = 
	}
	
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
				}
			}
		}
		return bfsArea;
	}
	
	static void combination() {
		for(int i1=1; i1<=N*M; i1++) {
			if(!isWallPossible(i1)) continue;
			for(int i2=i1+1; i2<=N*M; i2++) {
				if(!isWallPossible(i2)) continue;
				for(int i3=i2+1; i3<=N*M; i3++) {
					if(!isWallPossible(i3)) continue;
					simulate(i1, i2, i3);
				}
			}
		}
	}
	

	
	static boolean isWallPossible(int code) {
		int[] pair = getPairFromCode(code);
		return map[pair[0]][pair[1]] == 0;
	}
	
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
        
        N = nextInt();
        M = nextInt();
        
        map = new int[N+2][M+2];
        
        Arrays.fill(map[0], 1);
        Arrays.fill(map[N+1], 1);
        
        virusCnt =0;
        wallCnt =0;
        for(int r=1; r<=N; r++) {
        	map[r][0] = map[r][M+1] = 1;
        	for(int c=1; c<=M; c++) {
        		int tmp = nextInt();
        		map[r][c] = tmp;
        		if(tmp==0) continue;
        		else if(tmp==1) wallCnt++;
        		else {
        			vx[virusCnt] = r;
        			vy[virusCnt] = c;
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