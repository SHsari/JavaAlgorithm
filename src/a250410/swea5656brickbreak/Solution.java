package a250410.swea5656brickbreak;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Arrays;



public class Solution {
	/*
	 * SWEA5656 벽돌깨기
	 */
	
	// BFS 시 queue에 좌표를 넣기 위한 클래스.
	static class Point {
		int r, c;
		Point(int c, int r) {
			this.c = c; 
			this.r = r;
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, W, H, firstIdxAt[], ogFirstIdxAt[];
	static int curMap[][], ogMap[][];
	static int shootColumns[];
	static int brickCnt;
	 /*
	  *공을 쏘는 횟수 N : 1 ≤ N ≤ 4
	  *맵의 가로너비  W : 2 ≤ W ≤ 12
	  *맵의 세로 길이 H : 2 ≤ H ≤ 15
	  * 
	  * 공을 쏘는 횟수 N이 너무 작다.
	  * bruteForce를 시도할 수 밖에 없는걸까.
	  * 
	  * 
	  */
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			
			// 최대 제거 블록 값을 저장
			int maxRemoved = 0;
			do {
				// 오리지널 맵을 사용할 맵에 복사
				mapCopy();
				
				// N회만큼 발사
				int removed =0;
				for(int n=0; n<N; n++) {
					removed += hitAt(shootColumns[n]);
					dropBlocks();
				}
				if(removed > maxRemoved)
					maxRemoved =removed;
				//쏘는곳을 다음 순열로 갱신.
			} while(nextPermutation());
			
			result.append(brickCnt - maxRemoved).append("\n");
		}
		System.out.println(result);
	}
	
	// 디버깅을 위한 printMap()
	static void printMap() {

		for(int col=0; col<W; col++) {
			System.out.println(Arrays.toString(curMap[col]));
		}
		System.out.println();
	}
	
	static boolean nextPermutation() {
		for(int n=0; n<N; n++) {
			while(shootColumns[n]++ < W-1) {
				if(0<n) Arrays.fill(shootColumns, 0, n, 0);
				return true;
			}
		}
		return false;
	}
	

	/*
	 * BFS를 이용한 부서질 블록 탐색.
	 */
	static int hitAt(int hitCol) {
		
		if(firstIdxAt[hitCol] == H) return 0;
		
		int removeCnt =0;
		// 탄환이 처음 맞은 row, col을 시작으로 BFS 전파.
		boolean[][] visited = new boolean[W][H];
		Queue<Point> q = new ArrayDeque<>();
		
		q.add(new Point(hitCol, firstIdxAt[hitCol]));
		visited[hitCol][firstIdxAt[hitCol]] = true;
		
		while(!q.isEmpty()) {
			Point cur = q.poll(); 
			int value = curMap[cur.c][cur.r];
			curMap[cur.c][cur.r] = 0;
			removeCnt++;
			
			
			//1. 값에 따른 범위만큼 수평 순회
			int startCol = Math.max(0, cur.c-value+1);
			int endCol = Math.min(W-1, cur.c+value-1);
			
			for(int col=startCol; col<=endCol; col++) {
				if(!visited[col][cur.r] && curMap[col][cur.r] > 0) {
					visited[col][cur.r] = true;
					q.add(new Point(col, cur.r));
				}
			}
			
			//2. 범위만큼 수직 순회
			int startRow = Math.max(firstIdxAt[cur.c], cur.r-value+1);
			int endRow = Math.min(H-1, cur.r+value-1);
			
			for(int row=startRow; row<=endRow; row++) {
				if(!visited[cur.c][row] && curMap[cur.c][row] > 0) {
					visited[cur.c][row] = true;
					q.add(new Point(cur.c, row));
				}
			}
		}		
		return removeCnt;
	}
	
	
	/*
	 * 와씨
	 * 초기화시에 zeroRow[0] = -1 안해줄 경우
	 * column에 모든 숫자가 꽉 차있는 경우 문제가 생깁니다.
	 * 
	 * 한 column에 대해서 아래쪽 부터 올라오면서
	 * 0인 곳의 idx를 기록합니다.
	 * 
	 * 떨어뜨릴 블록을 발견하면 기록된 0인곳의 idx에서부터 순차적으로 채웁니다.(Queue)
	 * 
	 */
	static void dropBlocks() {
		// 블럭이 사라진 곳을 매꿔줘야 함.
		// 모든 column에 대해서.
		for(int col=0; col<W; col++) {
			int nextIdx = 0;
			int curIdx= 0;
			int zeroRow[] = new int[H];
			zeroRow[0] = -1;
			for(int row=H-1; row>=firstIdxAt[col]; row--) {
				if(curMap[col][row]==0) {
					zeroRow[nextIdx++] = row;
				} else if(zeroRow[curIdx] > 0){
					curMap[col][zeroRow[curIdx++]] = curMap[col][row];
					curMap[col][row] = 0;
					zeroRow[nextIdx++] = row;
				}
			}
			firstIdxAt[col] = zeroRow[curIdx] + 1;
		}
	}
	
	static void mapCopy() {
		firstIdxAt = Arrays.copyOf(ogFirstIdxAt, W);
		for(int c=0; c<W; c++)
			curMap[c] = Arrays.copyOf(ogMap[c], H);
	}
	
	static void init() throws IOException {
		brickCnt=0;
		N = nextInt();
		W = nextInt();
		H = nextInt();
		
		shootColumns = new int[N];
		
		ogMap = new int[W][H];
		curMap = new int[W][H];
		ogFirstIdxAt = new int[W];
		firstIdxAt = new int[W];
		Arrays.fill(ogFirstIdxAt, H);
		
		for(int h=0; h<H; h++) {
			for(int w=0; w<W; w++) {
				int value = nextInt();
				ogMap[w][h] = value;
				if(value>0) {
					ogFirstIdxAt[w]--; 
					brickCnt++;
				}
			}
		}
		

	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}