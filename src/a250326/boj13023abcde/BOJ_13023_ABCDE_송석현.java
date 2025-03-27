package a250326.boj13023abcde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class BOJ_13023_ABCDE_송석현 {
	/*
	 * baekJoon 13023 abcde
	 * 무향 그래프에서
	 * 깊이가 5 이상의 탐색이 가능한지 묻는 문제.
	 * 가능할 경우 1을, 그렇지 않은 경우 0을 출력한다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static int N, M, adjList[][], adjNum[];
	static boolean[] visited;
	
	public static void main(String[] args) {
		init();
		if(M<4) {
			System.out.println("0");
			return;
		}
		for(int node=0; node<N; node++) {
			if(dfs(node, 0))  {
				System.out.println("1");
				return;
			}
		}
		System.out.println("0");
	}
	
	/*
	 * dfs코드 작성을 많이 안했더니 확실히 정확도가 떨어집니다.
	 * 현재 노드에 대한 탐색이 끝난 후 visited를 다시 되돌려 놓지 않아서 틀렸습니다.
	 * 
	 */
	static boolean dfs(int curNode, int depth) {
		if(depth == 4) return true;
		visited[curNode] = true;
		for(int ith=0; ith<adjNum[curNode]; ith++) {
			int ithAdj = adjList[curNode][ith];
			if(!visited[ithAdj]) {
				if(dfs(ithAdj, depth+1)) return true;
			}
		}
		visited[curNode] = false;
		return false;
	}
	
	/* 
	 * 코드의 특징중 하나:
	 * 인접 리스트를 구현할 때, 리스트를 사용하지 않고
	 * 배열을 사용하고, 해당 배열의 길이를 따로 저장했습니다.
	 */
	static void init() {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        N = nextInt();
        M = nextInt();
        visited = new boolean[N];
        // 인접 리스트
        adjList = new int[N][N];
        //인접 리스트의 길이 저장
        adjNum = new int[N];
        
        for(int i=0; i<M; i++) {
        	int a = nextInt();
        	int b = nextInt();
        	// 인접 리스트에 추가
        	adjList[a][adjNum[a]] = b;
        	adjList[b][adjNum[b]] = a;
        	// 각 노드의 인접 리스트 길이정보 업데이트
        	adjNum[a] ++;
        	adjNum[b] ++;
        }
	}
	
	static int nextInt() {
		try { st.nextToken(); }
		catch(IOException e) { e.printStackTrace(); }
		return (int) st.nval;
	}
}