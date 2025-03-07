package a250306.boj2636Cheese;
import java.io.*;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Arrays;

/* boj 2663 치즈
 * 
 * 출력: 
 * 치즈가 모두 녹아 없어지는 데 걸리는시간 과
 * 모두 녹기 한시간 전 남아있는 치즈조각 칸의 갯수
 * 
 * map을 탐색하며 cheese가 없는경우 현재의 Queue 위치를 추가
 * cheese가 존재하는 곳일 경우 nextQueue에 위치를 추가.
 * 
 * 현재큐에 대한 탐색이 끝나면 nextQueue를 현재큐로 복사하고
 * nextQueue초기화.
 * 
 * 다시 현재큐 탐색부터 반복.
 */

public class Main {

	static BufferedReader br;
	static StreamTokenizer st;
	
	static int R, C;
	// 치즈의 현황판 : map
	static boolean map[][];
	static boolean visited[][];
	static int resultHour, resultCount;
	
	static final int[] dRow = {0, 1, 0, -1};
	static final int[] dCol = {1, 0, -1, 0};
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
			
        R = nextInt();
        C = nextInt();
        map = new boolean[R+2][C+2];
        visited = new boolean[R+2][C+2];
        Arrays.fill(visited[0], true);
        Arrays.fill(visited[R+1], true);
        
        for(int row=1; row<=R; row++) {
        	visited[row][0] = true;
        	visited[row][C+1] = true;
        	for(int col=1; col<=C; col++) {
        		 map[row][col] = nextInt()==1;
        	}
        }
        // 입력완료
        
        // BFS 돌리기
        firstBFS();
        
        System.out.println(resultHour + "\n" + resultCount);
	}
	
	/*
	 * 기존 큐와 새 큐를 가진다.
	 * 
	 * 방문시 Cheese 현황을 통해 치즈가 있는 경우 
	 * 위치를 새 큐에 넣고, 위치에 Cheese 제거
	 * 
	 * Cheese가 없는 경우 
	 * 위치를 기존큐에 넣기.
	 * 
	 * 기존 큐의 위치를 모두 탐색한 후
	 * 새 큐가 비어있을 경우
	 * 
	 * 더 이상 치즈가 있는 곳이 없다는 뜻이므로.
	 * 결과값을 리턴한다. 
	 * 
	 */
	
	static void firstBFS() {
		Queue<Integer> rowQ = new ArrayDeque<>();
		Queue<Integer> colQ = new ArrayDeque<>();
		Queue<Integer> nextRowQ = new ArrayDeque<>();
		Queue<Integer> nextColQ = new ArrayDeque<>();
	
		rowQ.add(1); colQ.add(1);
		
		int hour = 0;
		int areaSize=0;
		
		// 첫번째 탐색 0인 모든 곳에 대해서 탐색하고
		// 새로운 큐에 경계 블럭의 좌표를 넣는다.
		while(!rowQ.isEmpty()) {
			areaSize = rowQ.size();
			//printVisited();
			while(!rowQ.isEmpty()) {
				int curRow = rowQ.poll();
				int curCol = colQ.poll();
				
				for(int dir=0; dir<4; dir++) {
					int nextRow = curRow + dRow[dir];
					int nextCol = curCol + dCol[dir];
					
					if(!visited[nextRow][nextCol]) {
						if(!map[nextRow][nextCol]) {
							rowQ.add(nextRow);
							colQ.add(nextCol);
							visited[nextRow][nextCol] = true;
						}
						else {
							nextRowQ.add(nextRow);
							nextColQ.add(nextCol);
							visited[nextRow][nextCol] = true;
							map[nextRow][nextCol] = false;
						}
					}
				}
			}
			
			if(nextRowQ.isEmpty()) {
				resultCount = areaSize;
				resultHour = hour;
			}
			hour++;
			rowQ = nextRowQ;
			colQ = nextColQ;
			nextRowQ = new ArrayDeque<>();
			nextColQ = new ArrayDeque<>();
		}
	}
	
	// 디버깅을 위한 프린트 함수입니다.
	static void printVisited() {
		StringBuilder sb = new StringBuilder("\n");
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				sb.append(map[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}