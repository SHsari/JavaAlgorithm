package a250409.swea1263network2;
import java.io.*;


public class Solution {
	/*
	 * 플로이드 워셜 기초 문제라 보고 풀었습니다만, 
	 * bfs 로 풀면 빠를 것 같네요
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N;
	static int[][] adjMatrix;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			init();
			
			for(int mid=0; mid<N; mid++) {
				for(int i=0; i<N; i++) {
					if(mid==i) continue;
					for(int j=0; j<N; j++) {
						if(i==j) continue;
						int ogij = adjMatrix[i][j];
						int newDist = adjMatrix[i][mid] + adjMatrix[mid][j];
						if(newDist < 100000 && newDist>0 && ogij > newDist) {
							adjMatrix[i][j] = newDist;
							adjMatrix[j][i] = newDist;
						}
						
					}
				}
			}
			
			// 최소거리를 반환
			int minSum = Integer.MAX_VALUE; 
			for(int i=0; i<N; i++) {
				int sum = 0;
				for(int j=i+1; j<N; j++) {
					sum += adjMatrix[i][j];
				}
				for(int j=i-1; j>=0; j--) {
					sum += adjMatrix[i][j];
				}
				if (minSum > sum) {
					minSum = sum;
				}
			}
			
			result.append(minSum).append("\n");
		}
		System.out.println(result);
	}
	
	static void init() throws IOException {
		N = nextInt();
		adjMatrix = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				int tmp = nextInt();
				adjMatrix[i][j] = tmp==0? Integer.MAX_VALUE : tmp;
			}
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}