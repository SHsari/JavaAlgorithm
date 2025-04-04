package a250404.swea5643heightGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;


public class Solution {
	/*
	 * 처음 봤던 A형 2번문제 시험이라 생각해서
	 * 기억을 되살려서 그대로 풀어봤습니다.
	 * 노드별로 BFS 돌려서, 총 인접노드 방문횟수가 전체 노드의 갯수와 같은가 확인함으로써
	 * 결과를 냈던 거로 기억하는데, 이상하게 BFS 한번으로 해결이 안되네요..
	 * 
	 * 나보다 더 작은 사람들 인접리스트
	 * 나보다 더 큰 사람들 인접 리스트 
	 * 이렇게 총 2개를 만들어서 BFS 했습니다.
	 * 
	 * 2차원 배열이라 행렬같지만 리스트입니다.
	 * *(각 노드별로 리스트의 사이즈를 저장하는 배열 adjCnt를 따로 선언했습니다.)
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, M;
	static int[][] adjList01, adjList02;
	static int[] adjCnt01, adjCnt02;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			init();
			
			int count=0;
			for(int start=1; start<=N; start++) {
				// 더 작은사람 세기
				int cnt = bfs(start, adjList01, adjCnt01);
				// 더 큰 사람 세기
				cnt += bfs(start, adjList02, adjCnt02);
				// 작은사람 수 + 큰사람 수 == N-1명(나를 제외)
				if(cnt== N-1) count++;
			}
			
			result.append(count).append("\n");
			
		}
		System.out.println(result);
	}
	
	// 시작노드와 인접리스트를 받아 bfs하고
	// 전체 노드 방문 횟수를 리턴.
	static int bfs(int start, int[][] adjList, int[] adjCnt) {
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] visited = new boolean[N+1];
		q.add(start);
		visited[start] = true;
		int count=0;
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(int i=0; i<adjCnt[cur]; i++) {
				int next = adjList[cur][i];
				if(!visited[next]) {
					visited[next] = true;
					count++;
					q.add(next);
				}
			}
		}
		return count;
	}
	
	
	static void init() throws IOException {
		N = nextInt();
		M = nextInt();
		
		adjList01 = new int[N+1][N+1];
		adjList02 = new int[N+1][N+1];
		adjCnt01 = new int[N+1];
		adjCnt02 = new int[N+1];
		for(int i=0; i<M; i++) {
			int a = nextInt();
			int b = nextInt();
			// 더 작은사람 리스트
			adjList01[a][adjCnt01[a]++] = b;
			// 더 큰 사람 리스트
			adjList02[b][adjCnt02[b]++] = a;
		}
	}
	
	// 디버깅을 위한 프린트 함수
	static void print() {
		StringBuilder st = new StringBuilder();
		
		for(int i=1; i<=N; i++) {
			st.append(i + ", cnt: ");
			st.append(adjCnt01[i]).append(", List: ");
			st.append(Arrays.toString(adjList01[i])).append("\n");
		}
		System.out.println(st);
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}