package a250409.boj9205beerWalking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Main {
	/* BOJ 9205 맥주마시며 걸어가기
	 * 
	 * 한번 이동 가능한 최대 거리: 1000M
	 * 
	 * 맨하탄 디스탄스 1000M 이내인 좌표로만 이동 가능.
	 * 
	 * 집과, N개의 편의점과, 페스티벌의 좌표가 주어졌을 때
	 * 
	 * 위의 조건을 이용해서 페스티벌까지 갈 수 있는 지 구하여라
	 * 
	 * -> 최대 거리 이내에 있는 곳에 대한 인접 리스트를 작성 후
	 * 집에서부터 BFS했습니다.
	 */
	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	//배열을 인접리스트로 사용하기 위한 인접리스트의 크기를 저장하는 adjCnt 배열
	static int[] XCoor, YCoor, adjCnt;
	static int N;

	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			init();
			int adjList[][] = getAdjList();
			String theyAre = bfs(adjList);
			result.append(theyAre).append("\n");
		}
		System.out.println(result);
	}
	
	// 인접리스트 기반으로 하는 BFS 코드
	static String bfs(int[][] adjList) {
		boolean visited[] = new boolean[N+2];
		int[] queue = new int[N+2];
		int start = 0; int end =1;
		queue[end++] = 0;
		visited[0] = true;
		while(start<end) {
			int curr = queue[start++];
			for(int adj=0; adj<adjCnt[curr]; adj++) {
				int next = adjList[curr][adj];
				// 도달했다면 happy 반환.
				if(next==N+1) return "happy";
				else if(!visited[next]) {
					queue[end++] = next;
					visited[next] = true;
				}
			}
		}
		// 결국 도달 못했으면 Sad 반환
		return "sad";
	}
	
	// 최초 인접리스트 만드는 코드
	static int[][] getAdjList() {
		adjCnt = new int[N+2];
		int adjList[][] = new int[N+2][N+2];
		
		for(int i=0; i<N+2; i++) {
			for(int j=i+1; j<N+2; j++) {
				int mDist = getMdistBetween(i, j);
				if(mDist <= 1000) {
					adjList[i][adjCnt[i]++] = j;
					adjList[j][adjCnt[j]++] = i;
				}
			}
		}
		return adjList;
	}
	
	// 맨하탄 디스턴스 구하는 코드
	static int getMdistBetween(int idx1, int idx2) {
		return Math.abs(XCoor[idx1] - XCoor[idx2]) + Math.abs(YCoor[idx1] - YCoor[idx2]);
	}
	
	
	static void init() throws IOException {
		N = nextInt();
		XCoor = new int[N+2];
		YCoor = new int[N+2];
		
		for(int i=0; i<N+2; i++) {
			XCoor[i] = nextInt();
			YCoor[i] = nextInt();
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}
