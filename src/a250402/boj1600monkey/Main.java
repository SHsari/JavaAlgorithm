package a250402.boj1600monkey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Main {
	
	/*
	 * 3차원 배열을 이용한 Dijkstra 탐색으로 풀었습니다.
	 * 모든 원숭이 jump의 횟수에 대해서도 탐색을 해주어야 하고
	 * 
	 * 다음 탐색 노드에 대해서 Queue에 넣어야만 탐색이 가능하기 때문에,
	 * 배열에 존재하지 않는 jump의 경우의 수가 있더라 하더라도 상관이 없습니다.
	 * 
	 * 1. int expanseMap[H][W][K] 로 선언 (Height, Width, maxJump);
	 * 	1-1. 모든 위치를 Integer.MAX_VALUE 로 초기화
	 * 
	 * 2. expanseMap[0][0][0] = 0 부터 Dijkstra 탐색 시작.
	 * 
	 * 
	 * 아니 jump는 최대로 많이하는게 좋은건데..
	 * 이걸 어떻게 해야돼 정말로..??
	 */
	
	// 상태를 나타내는 class Point
	static class Point implements Comparable<Point> {
		static int[][][] expanse = Main.expanse;
		int r, c, jump;

		public Point(int r, int c, int jump) {
			super();
			this.r = r;
			this.c = c;
			this.jump = jump;
		}
		
		@Override
		public int compareTo(Point o) {
			return expanse[this.r][this.c][this.jump] - expanse[o.r][o.c][o.jump];
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	
	static final int[] dRow4 = {0, 1, 0, -1};
	static final int[] dCol4 = {1, 0, -1, 0};
	static final int[] dRow8 = {-2, -1, 1, 2, 2, 1, -1, -2};
	static final int[] dCol8 = {1, 2, 2, 1, -1, -2, -2, -1};
	
	static boolean[][][] isWall;
	static int[][][] expanse;
	static int jumpLimit, W, H;
	static PriorityQueue<Point> nextNodes;


	public static void main(String[] args) throws IOException {
		
		init();
		expanse[0][0][0]=0;
        nextNodes.offer(new Point(0,0,0));
        
        while(!nextNodes.isEmpty()) {
        	Point p = nextNodes.poll();
        	if(p.r == H-1 && p.c == W-1) {
        		System.out.println(expanse[p.r][p.c][p.jump]);
        		return;
        	}
        	else if(!isWall[p.r][p.c][p.jump]) {
        		dijkstra(p.r, p.c, p.jump);
        	}
        }
        
        int result = Integer.MAX_VALUE;
        for(int exp : expanse[H-1][W-1]) {
        	if(result>exp) result = exp;
        }
        if(result == Integer.MAX_VALUE) result = -1;
        System.out.println(result);
	}
	
	/*
	 * 탐색 시 조건들
	 * 1. 맵위에 있는 좌표인지
	 * 2. 벽 유무  -
	 * 3. 방문여부 -> 벽 유무의 배열로 공유
	 */
	static void dijkstra(int r, int c, int jump) {
		isWall[r][c][jump] = true;
		
		int nextExp = expanse[r][c][jump] + 1;
		
		if(jump<jumpLimit) {
			int nextJump = jump+1;
			for(int dir=0; dir<8; dir++) {
				int nextR = r + dRow8[dir];
				int nextC = c + dCol8[dir];
				if(isOnMap(nextR, nextC) && !isWall[nextR][nextC][nextJump]) {
					if(expanse[nextR][nextC][nextJump] > nextExp) {
						expanse[nextR][nextC][nextJump] = nextExp;
						nextNodes.offer(new Point(nextR, nextC, nextJump));
					}
				}
			}
		}
		
		for(int dir=0; dir<4; dir++) {
			int nextR = r + dRow4[dir];
			int nextC = c + dCol4[dir];
			if(isOnMap(nextR, nextC) && !isWall[nextR][nextC][jump]) {
				if(expanse[nextR][nextC][jump] > nextExp) {
					expanse[nextR][nextC][jump] = nextExp;
					nextNodes.offer(new Point(nextR, nextC, jump));
				}
			}
		}
	}
	
	// 해당 좌표가 맵 안에 있는 점인지 알려주는 함수
	static boolean isOnMap(int r, int c) {
		return (0<=r && 0<=c && r<H && c<W);
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);

        nextNodes = new PriorityQueue<>();
        
        jumpLimit = nextInt();
        W = nextInt();
        H = nextInt();
        
        isWall = new boolean[H][W][jumpLimit+1];
        expanse = new int[H][W][jumpLimit+1];
        
        for(int r=0; r<H; r++) {
        	for(int c=0; c<W; c++) {
            	Arrays.fill(expanse[r][c], Integer.MAX_VALUE);
        		Arrays.fill(isWall[r][c], nextInt()==1);
        	}
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}