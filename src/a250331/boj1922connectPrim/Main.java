package a250331.boj1922connectPrim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
	/*
	 * boj 1922 프림을 사용해서 풉니다.
	 * 컴퓨터의 수 N (1 ≤ N ≤ 1000)
	 * 연결할 수 있는 선의 수 M (1 ≤ M ≤ 100,000)
	 * 간선이 상당히 많아질 수 있으므로 프림이 적합한 것 같네요.
	 */
	
	//다음 노드와 비용을 저장하는 Edge 클래스.
	static class Edge implements Comparable<Edge>{
		int toNode, expanse;
		Edge(int to, int exp) {
			toNode = to; expanse = exp;
		}
		@Override
		public int compareTo(Edge o) {
			return this.expanse - o.expanse;
		}
	}
	// 노드의 갯수 N;
	static int N;
	
	public static void main(String[] args) throws IOException {
		List<Edge>[] adjList = init();
		int result = prim(adjList);
		System.out.println(result);
	}
	
	static int prim(List<Edge>[] adjList) {
		PriorityQueue<Edge> pQue = new PriorityQueue<>();
		boolean[] connected = new boolean[N+1];
		int connectCnt=0;
		int connectExp = 0;
		int nextNode=1;
		
		connected[1] = true;
		connectCnt++;
		
		while(connectCnt < N) {
			for(Edge edge : adjList[nextNode]) pQue.offer(edge);
			
			// 간선 뽑기.
			Edge currEdge = null;
			do {
				currEdge = pQue.poll();
				nextNode = currEdge.toNode;
				// 이미 연결된 노드를 향한 간선이면 다시하기
			} while(connected[nextNode]);
			
			
			// pQue가 비어있는 것 과 같은 상황은 없다고 한다.
			// 즉 현위치에서 무조건 nextNode가 존재.
			connected[nextNode] = true;
			connectExp += currEdge.expanse;
			connectCnt++;
		}
		
		return connectExp;
	}
	
	static List<Edge>[] init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer st = new StreamTokenizer(br);
		
		st.nextToken();
		N = (int) st.nval;
		
		List<Edge> edgesOf[] = new ArrayList[N+1];
		for(int i=1; i<=N; i++) edgesOf[i] = new ArrayList<>();
		
		st.nextToken();
		int edgeNum = (int) st.nval;
		for(int i=0; i<edgeNum; i++) {
			st.nextToken(); int a = (int) st.nval;
			st.nextToken(); int b = (int) st.nval;
			st.nextToken(); int exp = (int) st.nval;
			
			if(a!=b) {
				edgesOf[a].add(new Edge(b, exp));
				edgesOf[b].add(new Edge(a, exp));
			}
		}
		
		return edgesOf;
	}
	
	
}
