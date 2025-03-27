package a250326.swea3289disjoint;

import java.io.*;
/* 기본적인 Union & Find 문제로 파악됩니다.
 * 
 * 
 */

public class SWEA_3289_서로소집합_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	// 노드의 갯수 Nodes, tc마다의 명령 수행 갯수 MIter
	static int Nodes, MIter;
	static int[] parents;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			Nodes = nextInt(); MIter = nextInt();
			parents = new int[Nodes+1];
			for(int i=0; i<Nodes+1; i++) parents[i] = i;
			
			for(int iteration=0; iteration<MIter; iteration++) {
				int command = nextInt();
				int a = nextInt();
				int b = nextInt();
				
				//합집합 명령일 경우
				if(command == 0) union(a, b);	
				
				//union 확인 명령일 경우
				else {
					if(find(a)==find(b)) result.append("1");
					else result.append("0");
				}
			}
			
			result.append("\n");	
		}
		
		System.out.println(result);
	}
	
	// 루트 부모노드 확인 함수
	static int find(int a) {
		if(parents[a] == a) return a;
		else {
			parents[a] = find(parents[a]);
			return parents[a];		
		}
	}
	
	// 합집합 함수
	static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		if(rootA != rootB) parents[rootA] = rootB;
	}

	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}