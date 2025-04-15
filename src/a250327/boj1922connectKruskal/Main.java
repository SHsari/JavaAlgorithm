package a250327.boj1922connectKruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Main {

	/*
	 * boj 1922 Kruskal을 사용해서 풉니다.
	 * 컴퓨터의 수 N (1 ≤ N ≤ 1000)
	 * 연결할 수 있는 선의 수 M (1 ≤ M ≤ 100,000)
	 * 간선이 상당히 많아질 수 있으므로 프림이 적합한 것 같습니다.
	 */
	
	//연결 노드와 비용을 저장하는 Edge 클래스.
	static class Edge implements Comparable<Edge>{
		int node1, node2, expanse;
		Edge(int from, int to, int exp) {
			node1= from; node2 = to; expanse = exp;
		}
		@Override
		public int compareTo(Edge o) {
			return this.expanse - o.expanse;
		}
	}
	
	// 필요한 변수들
	static int N, parent[], rank[], edgeCnt;
	
	
	public static void main(String[] args) throws IOException {

		Edge[] edges = init();
		
		// 크루스칼의 핵심. 비용 오름차순 간선정렬
		Arrays.sort(edges, 0, edgeCnt);
		
		int selectedEdgeCount =0;
		int expanse =0;
		
		// 간선 순회하며 union & find;
		for(int i=0; i<edgeCnt; i++) {
			if(union(edges[i].node1, edges[i].node2)) {
				expanse += edges[i].expanse;
				if(++selectedEdgeCount == N-1) break;
			}
		}
		
		System.out.println(expanse);
	}
	
	static int find(int node) {
		if(parent[node] == node) return node;
		return parent[node] = find(parent[node]);
	}
	
	static boolean union(int node1, int node2) {
		int root1 = find(node1);
		int root2 = find(node2);
		if(root1 == root2) return false;
		
		if(rank[root1] > rank[root2]) parent[root2] = root1;
		else if(rank[root1] < rank[root2]) parent[root1] = root2;
		else {
			// 동일한경우
			parent[root1] = root2;
			rank[root2] ++;
		}
		
		return true;
	}
	
	static Edge[] init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer st = new StreamTokenizer(br);
		
		st.nextToken();
		N = (int) st.nval;
		parent = new int[N+1];
		for(int i=1; i<=N; i++) parent[i] = i;
		rank = new int[N+1];
		
		st.nextToken();
		int edgeNum = (int) st.nval;
		edgeCnt=0;
		Edge[] edges = new Edge[edgeNum];
		
		for(int i=0; i<edgeNum; i++) {
			st.nextToken(); int a = (int) st.nval;
			st.nextToken(); int b = (int) st.nval;
			st.nextToken(); int exp = (int) st.nval;
			
			if(a!=b) {
				edges[edgeCnt++] = new Edge(a, b, exp);
			}
		}
		
		return edges;
	}
}
