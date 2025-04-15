package a250409.boj3055escape;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;


public class Solution {
	/*
	 * BOJ 3055	탈출
	 * 
	 * 물이 확산하기 전에 출발지에서 목적지에 도착할 수 있는지,
	 * 도착한다면 그 시간을 출력해야 한다.
	 * 
	 * 물은 매 분만다 4방으로 확장.
	 * 초기 물의 위치가 0개일수도, 여러개일 수도 있습니다. ***
	 * 
	 * 위의 조건때문에 한참 시간소요 했습니다.
	 */

	static int R, C;
	static char[][] map;
	static boolean[][] visited;
	
	static Point S;
	static Queue<Point> waterPoints;

	public static void main(String[] args) throws IOException {
		init();
		Queue<Point> sQue = new ArrayDeque<>();
		
		sQue.add(S);
		int addCount; // 고슴도치가 현 iterate에 대해 이동 가능한 좌표의 수
		int iterateCnt=0; // 1분당 1회 iterate
		
		do {
			addCount = iterate(waterPoints, sQue);
			iterateCnt++;
			
		} while(addCount !=0 && addCount != -1);
		
		// -1이라면 목적지에 도착한 것입니다.
		if(addCount == -1) System.out.println(iterateCnt);
		// 0이라면 물이나 장애물에 막혀 이동이 불가능한 경우입니다.ㄴ
		else System.out.println("KAKTUS");

	}
	
	
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	static int iterate(Queue<Point> waterQue, Queue<Point> sQue) {
		// 물의 확장.
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
		
		// 고슴도치의 이동(모든 가능성에 대해)
		int sQueSize = sQue.size();
		int addCount = 0;
		for(int i=0; i<sQueSize; i++) {
			Point s = sQue.poll();
			for(int dir=0; dir<4; dir++) {
				int nextR = s.x+dRow[dir];
				int nextC = s.y+dCol[dir];
				// 방문하지 않았고, 이동할 수 있는 곳이라면,
				if(map[nextR][nextC] == '.' && !visited[nextR][nextC]) {
					visited[nextR][nextC] = true;
					sQue.add(new Point(nextR, nextC));
					addCount ++;
				}
				// 목적지에 도착했다면 바로 종료.
				if(map[nextR][nextC] == 'D') {
					return -1;
				}
			}
		}
		// 이동 가능한 곳이 없었다면 0이 될 겁니다.
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
        
        waterPoints = new ArrayDeque<>();
        
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
        			waterPoints.add(new Point(r, c));
        		}
        	}
        }
	}
}
/*

10 10
.........D
..........
..........
..........
..........
..........
..........
..........
..........
S.........


10 10
.........D
..........
..........
..........
XXXXXXXXXX
..........
..........
..........
..........
S.........


10 10
.........D
..........
..........
..........
..........
..........
..........
..........
.*........
S.........

10 10
..D.......
..........
..........
..........
..........
..........
..........
..........
..........
S*........
*/