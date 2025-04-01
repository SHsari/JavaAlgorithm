package a250327.swea3124mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Solution {
	/* 
	 * SWEA 3124 MST
	 * from, to, weight으로 이뤄진 간선들이 주어지면
	 * MST를 구하여라
	 * 
	 * Kruskal로 풉니다.
	 * 
	 * 정점의 개수 V(1≤V≤100,000)
	 * 간선의 개수 E(1≤E≤200,000)
	 * 
	 * 간선의 가중치 abs(weigth) <= 1,000,000
	 * 
	 * 따라서 MST의 간선 가중치 합의 범위는
	 * abs(totalWeight) < 10^6 * 10^5  ~~ 2^33
	 * long으로 선언 필요.
	 */

	static class Edge implements Comparable<Edge> {
		int from, to, weight;
		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}
		public Edge(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
	
	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int V, E;
	static Edge[] edges;
	static int[] parents;
	
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			//입력
			init();
			//간선정렬
			Arrays.sort(edges);
			
			//간선을 오름차순으로 순회하며 Union & Find
			int selectedEdgeNum=0;
			long totalWeight = 0;
			for(Edge edge : edges) {
				if(union(edge.from, edge.to)) {
					totalWeight += edge.weight;
					if(++selectedEdgeNum == V-1) break;
				}
			}
			
			result.append(totalWeight).append("\n");
		}
		System.out.println(result);
	}
	
	//UNION
	static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if(aRoot==bRoot) return false;
		else {
			if(aRoot<bRoot) parents[bRoot] = aRoot;
			else parents[aRoot] = bRoot;
			return true;
		}
	}
	
	//FIND
	static int find(int a) {
		if(parents[a] == a) return a;
		else {
			return parents[a] = find(parents[a]);
		}
	}
	
	
	static void init() throws IOException {
		V = nextInt(); E = nextInt();
		edges = new Edge[E];
		parents = new int[V+1];
		for(int i=0; i<=V; i++) {
			parents[i] = i;
		}
		for(int i=0; i<E; i++) {
			int from = nextInt();
			int to = nextInt();
			int weight = nextInt();
			edges[i] = new Edge(from, to, weight);
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}