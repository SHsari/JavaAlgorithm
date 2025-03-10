package a250307.boj10026colorblind;

import java.io.*;
import java.util.Queue;
import java.util.ArrayDeque;
import java.awt.Point;
import java.util.Arrays;

/*
 * 백준 10026 적록색약
 * 간단히 두번의 BFS를 돌아야 하는 문제입니다.
 * 
 * 1안.
 * 첫번째 색약이 아닌 사람의 기준으로 구역을 구분할 때,
 * 빨간색을 초록색으로 치환합니다.
 * 두번째 색약인 사람이 구역 구분 시, 
 * 동일한 BFS 함수로 다른 값을 얻을 수 있습니다.
 * 
 * 2안.
 * 색약 유무 여부에 따라 두개의 BFS 함수를 짜고 두번 돌립니다.
 * 
 * 
 * 2안을 선택했습니다.
 */

public class BOJ_10026_적록색약_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;

    static int N;
    static char[][] map;

    static final int dRow[] = {0, 1, 0, -1};
    static final int dCol[] = {1, 0, -1, 0};
	public static void main(String[] args) throws IOException {

        init();
        startBFS();
	}

    // visited 배열을 초기화하고
    // 맵을 순회하면서 BFS 를 호출합니다.
    static void startBFS() {
        boolean[][] visited = initVisited();

        int normalCount =0;
        for(int row=1; row<=N; row++) {
            for(int col=1; col<=N; col++) {
                if(!visited[row][col]) {
                    normalBFS(row, col, visited);
                    normalCount ++;
                }
            }
        }
        
        visited = initVisited();
        int blindCount =0;
        for(int row=1; row<=N; row++) {
            for(int col=1; col<=N; col++) {
                if(!visited[row][col]) {
                    blindBFS(row, col, visited);
                    blindCount ++;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        result.append(normalCount).append(" ").append(blindCount);

        System.out.println(result);
    }

    // 색약이 아닌자의 구역구분
    static void normalBFS(int row, int col, boolean[][] visited) {
        Queue<Point> pointQ = new ArrayDeque<>();
        pointQ.add(new Point(row, col));
        visited[row][col] = true;
        char startColor = map[row][col];

        while(!pointQ.isEmpty()) {
            Point curP = pointQ.poll();
            int curRow = curP.x;
            int curCol = curP.y;
            
            for(int dir=0; dir<4; dir++) {
                int nextRow = curRow + dRow[dir];
                int nextCol = curCol + dCol[dir];
            
                if(!visited[nextRow][nextCol] && map[nextRow][nextCol] == startColor) {
                    pointQ.add(new Point(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;
                }
            }
        }
    }


    // 색약인 자의 구역구분
    static void blindBFS(int row, int col, boolean[][] visited) {
        Queue<Point> pointQ = new ArrayDeque<>();
        pointQ.add(new Point(row, col));
        visited[row][col] = true;
        boolean isBlue = map[row][col] == 'B';

        while(!pointQ.isEmpty()) {
            Point curP = pointQ.poll();
            int curRow = curP.x;
            int curCol = curP.y;
            
            for(int dir=0; dir<4; dir++) {
                int nextRow = curRow + dRow[dir];
                int nextCol = curCol + dCol[dir];

                boolean isNextBlue = map[nextRow][nextCol] == 'B';
                if(!visited[nextRow][nextCol] && isNextBlue == isBlue) {
                    pointQ.add(new Point(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;       
                }
            }
        }
    }

    // 방문함수를 초기화합니다.
    static boolean[][] initVisited() {
        boolean[][] visited = new boolean[N+2][N+2];
        Arrays.fill(visited[0], true);
        Arrays.fill(visited[N+1], true);
        for(int r=1; r<=N; r++) {
            visited[r][0] = visited[r][N+1] = true;
        }
        return visited;
    }

    // 입력 및 맵 초기화 함수
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);

        N = nextInt();
        map = new char[N+2][N+2];
        Arrays.fill(map[0], '#');
        Arrays.fill(map[N+1], '#');
        StringBuilder line;
        for(int i=1; i<=N; i++) {
            line = new StringBuilder("#");
            line.append(nextString()).append("#");
            map[i] = line.toString().toCharArray();
        }
	}
	
    static String nextString() throws IOException {
        st.nextToken();
        return st.sval;
    }

	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}
