package a250307.swea1267workSequence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

/* SWEA_1267 작업순서
 * 
 * 정석적인 위상정렬 문제로 보입니다.
 * 
 * 
 */
public class SWEA_1267_작업순서_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

	//Vertex의 V
	//Edge의 E
	// 각각의 갯수를 나타냅니다.
	static int V, E;
	static int[] inDegree;
	static List<List<Integer>> adjList;
	
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
		int T = 10;
		
		for(int tc=1; tc<=T; tc++) {
			result.append("\n#").append(tc).append(" ");
			
			V = nextInt(); E = nextInt();
			
			inDegree = new int[V+1];
			adjList = new ArrayList<>();
			
			// 인접리스트 초기화
			for(int i=0; i<=V; i++) {
				adjList.add(new ArrayList<>());
			}
			
			// 인접리스트 및 진입차수 입력
			for(int i=0; i<E; i++) {
				int pre = nextInt();
				int cur = nextInt();
				adjList.get(pre).add(cur);
				inDegree[cur] ++;
			}
			
			topologyBFS();
			
		}
			
		System.out.println(result);
	}
	
	/*
	 * 
	 * 왠지 BFS가 더 정직한? 순서인 것 같아 BFS 로 풀었습니다.
	 */
	static void topologyBFS() {
		Queue<Integer> zeroIn = new ArrayDeque<>();
		
		for(int subject=1; subject<=V; subject++) {
			if(inDegree[subject]==0) {
				zeroIn.add(subject);
			}
		}
		
		
		while(!zeroIn.isEmpty()) {
			int pre = zeroIn.poll();
			result.append(pre).append(" ");
			for(int subject : adjList.get(pre)) {
				if(--inDegree[subject] == 0) {
					zeroIn.add(subject);
				}
			}
		}
		 
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}
