package a250331.swea3124mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class Solution {
	
	/* 
	 * SWEA 3124 MST -> Prim으로 풀기.
	 * from, to, weight으로 이뤄진 간선들이 주어지면
	 * MST를 구하여라
	 * 
	 * Prim으로 풉니다. -> 간선정보를 주기 때문에 Kruskal이 나아보입니다.
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
		int to, weight;
		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}
		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}
	
	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int V, E;
	static List<Edge>[] adjList;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			//입력
			init();
			long totalWeight = prim();
			result.append(totalWeight).append("\n");
			
		}
		System.out.println(result);
	}
	
	/*
	 * Prioirty Queue를 사용한 prim 알고리즘
	 * 메모리 낭비는 훨씬 심할 것으로 예상됩니다.
	 * 
	 * minimum edge를 정해서 새로운 vertex를 트리에 추가하고
	 * 추가된 vertex를 목적지로 하는 edge들을
	 * Queue에서 제거할 수 있는 알고리즘이 있다면.. 더 좋을 것 같습니다.
	 * 
	 * vertex번호를 HashMap으로 해서... 객체 자체를 제거?
	 * 
	 * 여튼 2가지 방식으로 정렬 및 접근을 할 수 있는 그런 자료구조가 필요하겠네용..?
	 * 
	 */
	static long prim() {
		PriorityQueue<Edge> edgeHeap = new PriorityQueue<>();
		boolean[] isTree = new boolean[V+1];
		int newVertex = 1;
		isTree[newVertex] = true;
		
		// 실수 2.totalWeight을 int로 선언.
		// Kruskal 구현때도 겪은 문제를 반복.
		long totalWeight = 0;
		
		for(int i=1; i<V; i++) {
			edgeHeap.addAll(adjList[newVertex]);
			
			Edge nextEdge= edgeHeap.poll();
			
			//실수 1. while문 조건식을 반대로 적음.
			while(isTree[nextEdge.to]) {
				nextEdge = edgeHeap.poll();
			}
			newVertex = nextEdge.to;
			// 실수 3. visited 처리 안함
			isTree[newVertex] = true;
			totalWeight += nextEdge.weight;
		}
		// 일단 문제를 한 90% 풀어놓고 점심먹은 후 다시 풀으려니까
		// 연속적인 집중력이 많이 부족한 것 같단 느낌입니다.
		return totalWeight;
	}
	
	
	static void init() throws IOException {
		V = nextInt(); E = nextInt();
		adjList = new ArrayList[V+1];
		for(int i=1; i<=V; i++) {
			adjList[i] = new ArrayList<>();
		}

		for(int i=0; i<E; i++) {
			int v1 = nextInt();
			int v2 = nextInt();
			int weight = nextInt();
			adjList[v1].add(new Edge(v2, weight));
			adjList[v2].add(new Edge(v1, weight));
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}