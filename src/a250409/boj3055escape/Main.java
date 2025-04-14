package a250409.boj3055escape;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {

	static int R, C;
	static char[][] map;
	static boolean[][] visited;
	
	static Point S, star;

	public static void main(String[] args) throws IOException {
		init();
		Queue<Point> wtQue = new ArrayDeque<>();
		Queue<Point> sQue = new ArrayDeque<>();
		
		if(star!=null)
			wtQue.add(star);
		sQue.add(S);
		int addCount;
		int iterateCnt=0;
		do {
			addCount = iterate(wtQue, sQue);
			iterateCnt++;
			
		} while(addCount !=0 && addCount != -1);
		
		if(addCount == -1) System.out.println(iterateCnt);
		else System.out.println("KAKTUS");

	}
	
	
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	static int iterate(Queue<Point> waterQue, Queue<Point> sQue) {
		int wtQueSize = waterQue.size();
		for(int i=0; i<wtQueSize; i++) {
			Point wt = waterQue.poll();
			
			for(int dir=0; dir<4; dir++) {
				int nextR = wt.x+dRow[dir];
				int nextC = wt.y+dCol[dir];
				
				if(map[nextR][nextC] == '.') {
					waterQue.add(new Point(nextR, nextC));
					map[nextR][nextC] = '*';
				}
			}
		}
		
		int sQueSize = sQue.size();
		int addCount = 0;
		for(int i=0; i<sQueSize; i++) {
			Point s = sQue.poll();
			
			for(int dir=0; dir<4; dir++) {
				int nextR = s.x+dRow[dir];
				int nextC = s.y+dCol[dir];
				
				if(map[nextR][nextC] == '.' && !visited[nextR][nextC]) {
					visited[nextR][nextC] = true;
					sQue.add(new Point(nextR, nextC));
					addCount ++;
				}
				if(map[nextR][nextC] == 'D') {
					return -1;
				}
			}
		}
		return addCount;
	}

	static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R+2][C+2];
        visited = new boolean[R+2][C+2];
        
        Arrays.fill(map[0], 'X');
        Arrays.fill(map[R+1], 'X');
        
        for(int r=1; r<=R; r++) {
    		StringBuilder row = new StringBuilder();
    		row.append("X");
    		row.append(br.readLine()).append("X");
    		map[r] = row.toString().toCharArray();
        	for(int c=1; c<=C; c++) {
        		if(map[r][c] == 'S') {
        			S = new Point(r, c);
        			map[r][c] = '.';
        		}
        		else if(map[r][c] == '*') {
        			star = new Point(r, c);
        		}
        	}
        }
	}
}
