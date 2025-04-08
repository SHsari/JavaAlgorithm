package a250408.boj1194TheMoonIsRising;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Objects;


public class Main {
	/*
	 * map에 대해서
	 * row, column, keyStatus 로 이뤄진 3차원 공간에 대한 BFS 탐색을 합니다.
	 * 
	 * BFS시에 visited 관리는
	 * boolean 배열 대신 HashSet을 사용했습니다.
	 * 
	 * row, column, keyStatus 세가지 정보를 담는 State 클래스를 정의,
	 * HashCode를 Override 해서 HashSet을 이용한 visit 관리가 유효하도록 했습니다.
	 * 
	 */
	
	
	static class State {
		/* row, column, keyStatus
		 * 미로에서의 상태를 나타내는 클래스
		 * hashcode Override 하여, HashSet으로 visit 여부를 판단할 수 있도록 함. 
		 * keyStatus는 6종류의 key에 대해 bitmask로 표현.
		 */
		int row, col, key;
		State(int row, int col, int key) {
			this.row = row;
			this.col = col;
			this.key = key;
		}
		
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        State other = (State) obj;
	        return row == other.row && col == other.col && key == other.key;
	    }
	    
	    @Override
	    public int hashCode() {
	        return Objects.hash(row, col, key);
	    }

		@Override
		public String toString() {
			return "State [row=" + row + ", col=" + col + ", key=" + key + "]";
		}	 
	}
	
	/*
	 * 
	 * 무지성 완전탐색을 해도 괜찮지 않을까 싶었는데. 그러면..
	 * 
	 * visited 배열에 열쇠 보유 현황도 있어야 한다. 
	 */
	
	static char[][] map;
	static int N, M, startRow, startCol;
	
	public static void main(String[] args) throws IOException {
		init();
		int moveCnt = bfs();
		System.out.println(moveCnt);
	}
	
	
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	
	static int bfs() {
		Queue<State> queue = new ArrayDeque<>();
		Set<State> visited = new HashSet<>();
		// key 보유 현황은 a, b, c, d, e, 6개 이므로, 비트마스크로 표현한다.
		
		State state = new State(startRow, startCol, 0);
		queue.add(state);
		visited.add(state);
		
		int moveCnt=0;
		while(!queue.isEmpty()) {
			int qSize = queue.size();
			moveCnt++;
			for(int i=0; i<qSize; i++) {
				State curr = queue.poll();
				
				for(int dir=0; dir<4; dir++) {
					int nextR = curr.row + dRow[dir];
					int nextC = curr.col + dCol[dir];
					
					State nextState = new State(nextR, nextC, curr.key);

					char nextMap = map[nextR][nextC];
					
					// 다음 방문 위치에 대한 확인.
					if(nextMap == '.') {}
					else if(nextMap == '#') continue;
					else if(nextMap == '1') return moveCnt;
					// 키를 줍게 되는 상황이면, keyStatus 비트마스크에 반영
					else if(nextMap >= 'a' && nextMap <= 'f')
						nextState.key = setKeyStatus(nextMap, curr.key);
					// 문을 마주치면 keyStatus에 해당 문에 대한 key 가지고 있는 지 확인.
					else if(nextMap >= 'A' && nextMap <= 'F')
						if(!hasKeyOf(nextMap, curr.key)) continue;
					
					// 방문확인, 방문 표시, 큐 추가
					if(!visited.contains(nextState)) {
//						System.out.print("map: "+ nextMap + " || ");
//						System.out.println(nextState);
	
						queue.add(nextState);
						visited.add(nextState);
					}
				}
			}
		}
		return -1;
	}
	
	//문을 마주쳤을때 해당 문에대한 key를 갖고 있는 지 확인
	private static boolean hasKeyOf(char door, int keyStatus) {
		int keyIdx = door - 'A';
		return (keyStatus & (1<<keyIdx)) != 0; 
	}
	
	// 키를 주웠을때 bitmask status에 반영
	private static int setKeyStatus(char key, int keyStatus) {
		int keyIdx = key - 'a';
		return keyStatus | 1<<keyIdx;
	}
	

	static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = nextInt(st);
        M = nextInt(st);
        map = new char[N+2][M+2];
        
        Arrays.fill(map[0], '#');
        Arrays.fill(map[N+1], '#');
        
        for(int r=1; r<=N; r++) {
        	String row = br.readLine();
    		map[r] = ("#" + row + "#").toCharArray();
    		if(row.contains("0")) {
    			startCol = row.indexOf('0')+1;
    			startRow = r;
    			map[r][startCol] = '.';
    		}
        	//System.out.println(Arrays.toString(map[r]));
        }
	}
	
	static int nextInt(StringTokenizer st) throws IOException {
		return Integer.parseInt(st.nextToken());
	}
}