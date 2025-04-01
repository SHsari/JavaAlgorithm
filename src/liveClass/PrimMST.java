package liveClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class PrimMST {

	static BufferedReader br;
	static StreamTokenizer st;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StreamTokenizer(br);
		
		int V = nextInt();
		int[][] adjMatrix = new int[V][V];
		boolean[] visited = new boolean[V];
		int[] minEdge = new int[V];
		
		for(int i=0; i<V; i++) {
			for(int j=0; j<V; j++) {
				adjMatrix[i][j] = nextInt();
			}
		}
		
		Arrays.fill(minEdge, Integer.MAX_VALUE);
		int result = 0;
		minEdge[0] = 0;
		
		int c;
		for(c=0; c<V; c++) {
			int min = Integer.MAX_VALUE;
			int minVertex = -1;
			for(int i=0; i<V; i++) {
				if(!visited[i] && min> minEdge[i]) {
					min = minEdge[i];
					minVertex = i;
				}
			}
			if(minVertex == -1) break;
			result += min;
			visited[minVertex] = true;
			
			
			//
			for(int i=0; i<V; i++) {
				if(!visited[i] 
						&& adjMatrix[minVertex][i] != 0
						&& minEdge[i] > adjMatrix[minVertex][i]) {
					minEdge[i] = adjMatrix[minVertex][i];
				}
			}
		}
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
	
}
