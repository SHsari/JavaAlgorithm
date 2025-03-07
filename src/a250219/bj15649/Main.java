package a250219.bj15649;

// 순 1 조 2 부 1
// 순열 조합 붑운집한

import java.io.*;
import java.util.StringTokenizer;

public class Main {

	static StringBuilder sb;
	static boolean[] visited;
	static char[] result;

	static int N, M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		visited = new boolean[N];
		result = new char[M*2];
		
		for(int i=1; i<M*2; i+=2) {
			result[i] = ' ';
		}
		
		nPm(0);
		System.out.println(sb);
	}
	
	
	static void nPm(int depth) {
		if(depth==M) {
			sb.append(result).append('\n');
			return;
		}
		
		for(int i=0; i<N; i++) {
			if(visited[i]) continue;
			else {
				result[depth*2] = (char)(i+'1');
				visited[i] = true;
				nPm(depth+1);
				visited[i] = false;
			}
		}
	}
}
