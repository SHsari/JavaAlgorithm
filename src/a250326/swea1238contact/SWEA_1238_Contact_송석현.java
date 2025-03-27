package a250326.swea1238contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Queue;


public class SWEA_1238_Contact_송석현 {
/*
 * SWEA 1238 Contact
 * 기본적인 BFS 이다. 
 * BFS를 깊이별로 돌린 후 마지막 깊이에서 방문한 최대 노드번호를 반환하는 문제.
 */
	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int start;
	static boolean[] visited;
	// 노드번호의 범위는 1~100이며
	// 해당범위안에 존재하지 않는 노드 번호들이 있다고 합니다.
	static Map<Integer, List<Integer>> adjList;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = 10;
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			
			result.append(bfs(start)).append("\n");
		}
		System.out.println(result);
	}
	
	/*
	 * 기본적인 BFS 함수.
	 */
	static int bfs(int start) {
		Queue<Integer> q = new ArrayDeque<>();
		
		q.add(start);
		visited[start] = true;
		
		while(!q.isEmpty()) {
			// addCnt는 이번 깊이의 BFS 탐색에서
			// 큐에 추가한 새로운 노드의 갯수입니다.
			// addCnt==0일때, 마지막BFS임을 알 수 있습니다.
			int addCnt=0, max=0;
			int qSize = q.size();
			for(int i=0; i<qSize; i++) {
				int current = q.poll();
				if(max<current) max =current;
				if(adjList.containsKey(current)) {
					List<Integer> adjs = adjList.get(current);
					for(int node : adjs) {
						if(!visited[node]) {
							q.add(node);
							visited[node] = true;
							addCnt++;
						}
					}
				}
			}
			
			if(addCnt==0) {
				return max;
			}
		}
		return -1;
	}
	
	static void init() {
		int N = nextInt()/2;
		start = nextInt();
		adjList = new HashMap<>();
		visited = new boolean[101];
		
		for(int i=0; i<N; i++) {
			int from = nextInt();
			int to = nextInt();
			
			if(!adjList.containsKey(from)) {
				adjList.put(from, new ArrayList<>());
			}
			adjList.get(from).add(to);
		}
	}

	
	static int nextInt() {
		try { st.nextToken(); }
		catch(IOException e) { 
			e.printStackTrace();
		}
		return (int) st.nval;
	}
}