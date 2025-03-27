package a250326.swea7465chungYong;
import java.io.*;

/*
 * SWEA 7465 창용마을 무리의갯수
 * 
 * 유니온 파인드 문제...
 * 유니온 파인드를 사용해야만 하는 문제..
 * BFS로도 풀 수 있을 것 같다.
 * 
 */
public class SWEA_7465_창용마을무리의개수_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	// 마을 사람의 수 Nperson, 관계의 수 MIter
	// 모임의 갯수 moims
	// 유니온.파인드를 위한 parents 배열
	static int Nperson, MIter, moims, parents[];
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			Nperson = nextInt(); MIter = nextInt();
			// 처음에 모임의 갯수는 N과 동일합니다.
			moims = Nperson;
			parents = new int[Nperson+1];
			for(int i=0; i<Nperson+1; i++) {
				parents[i] = i;
			}
			
			for(int iter=0; iter<MIter; iter++) {
				int a = nextInt();
				int b = nextInt();
				
				union(a, b);
			}
			
			result.append(moims).append("\n");
		}
		System.out.println(result);
	}
	
	static int findRoot(int a) {
		if(parents[a] != a) {
			parents[a] = findRoot(parents[a]);
		}
		return parents[a];
	}
	
	static void union(int a, int b) {
		int rootA = findRoot(a);
		int rootB = findRoot(b);
		
		if(rootA != rootB) {
			// 둘이 유니온이 아닌 경우에
			// 합쳐주고 모임의 갯수 -1 해줍니다.
			parents[rootA] = rootB;
			moims--;
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

} 