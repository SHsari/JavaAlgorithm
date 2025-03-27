package liveClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Kruskal {
	
	static class Edge implements Comparable<Edge> {
		int from, to, weight;
		
		Edge(int st, int end, int weight) {
			this.from = st;
			this.to = end;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}
	}
	
	static Edge[] edgeList;
	static int[] parents;
	static int V, E;
	
	static void init() {
		parents = new int[V];
		for(int i=0; i<V; i++) {
			parents[i] = i;
		}
	}
	
	static int find(int a) {
		if(a==parents[a]) return a;
		else {
			parents[a] = find(parents[a]);
			return parents[a];
		}
	}
	
	static boolean union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		if(rootA==rootB) return false;
		else {
			parents[rootA] = rootB;
			return true;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		edgeList = new Edge[E];
	
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
		
			edgeList[i] = new Edge(from, to, weight);
		}
		
		Arrays.sort(edgeList);
		init();
		int result = 0, count=0;
		for(Edge e : edgeList) {
			if(union(e.from, e.to)) {
				result += e.weight;
				if(++count == V-1) break;
			}
		}
		
		System.out.println(result);
	}

}
