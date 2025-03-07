package a250305.swea1247best;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/*
 * SWEA 1247 최적경로
 * 0 <= x, y <= 100
 * 
 * 좌표를 이용하며 
 * 이동시 좌표 사이의 거리는 Manhattan 거리로 한다.
 *
 * 2 <= 고객 수 <= 10 
 * 
 * 외판원 문제
 * 음 DP로 함풀어보자
 */

public class Solution_unsolved {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	// N은 주어지는 배열의 길이
	static int N;
	static int stx, sty, endx, endy;
	
	static int[] nx, ny;
	static int[] startDist, endDist;
	static int[][] distArr;
	static Status[][] visitedStatus;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			N = nextInt();
			
			nx = new int[N];
			ny = new int[N];
			
			distArr = new int[N][N];
					
			stx = nextInt();
			sty = nextInt();
			
			for(int i=0; i<N; i++) {
				nx[i] = nextInt();
				ny[i] = nextInt();
			
				for(int j=0; j<i; j++) {
					distArr[i][j] = getMDist(nx[i], ny[i], nx[j], ny[j]);
					distArr[j][i] = distArr[i][j];
				}
			}
			
			endx = nextInt();
			endy = nextInt();
			// 입력완료
	
			visitedStatus = new Status[N][N];
			memoStart();
			for(int iter=1; iter<N; iter++) {
				memoMinDist(iter);
			}
			int minDist = memoEnd();
			
			result.append(minDist).append("\n");
			
		}
			
		System.out.println(result);
	}
	
	static void memoStart() {
		for(int firstNode=0; firstNode<N; firstNode++) {
			Status tmp = new Status();
			tmp.distSum = getMDist(stx, sty, nx[firstNode], ny[firstNode]);
			tmp.visited[firstNode] = true;
			visitedStatus[0][firstNode] = tmp;
		}
	}
	
	static int memoEnd() {
		int min = Integer.MAX_VALUE;
		for(int endNode=0; endNode<N; endNode++) {
			int newDist = visitedStatus[N-1][endNode].distSum + getMDist(endx, endy, nx[endNode], ny[endNode]);
			if(newDist<min) min = newDist;
		}
		return min;
	}
	
	static void memoMinDist(int iter) {
		System.out.println(iter);
		for(int newEnd=0; newEnd<N; newEnd++) {
			
			int minDist = Integer.MAX_VALUE;
			Status newSt = null;
			
			for(int oldEnd=0; oldEnd<N; oldEnd++) {
				Status old = visitedStatus[iter-1][oldEnd];
				if(!old.visited[newEnd]) {
					
					int newDist = old.distSum + distArr[oldEnd][newEnd];
					if(newDist<minDist) {
						minDist = newDist;
						newSt = new Status(old);
						newSt.visited[newEnd] = true;
						newSt.distSum += newDist;
					}
				}
			}
			if(newSt!=null) {
				visitedStatus[iter][newEnd] = newSt;
			}
		}
	}
	
	
	
	static int getMDist(int x1, int y1, int x2, int y2) {
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
	
	static class Status {
		boolean[] visited;
		int distSum;
		
		Status() {
			distSum=0;
			visited = new boolean[N];
		}
		
		Status(Status other) {
			this.visited = new boolean[N];
			for(int i=0; i<N; i++) {
				this.visited[i] = other.visited[i];
			}
			this.distSum = other.distSum;
		}
	}

}