package a250331.swea1251hanaroPrim;

import java.io.*;
import java.util.Arrays;

public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

    static int N;
    static int x[], y[];
    static double E;

    static double[][] adjMatrix;
    static boolean[] visited;
    static double[] minEdge;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
            init();
            setPriceMatrix();

            int start=0;
            double min = Integer.MAX_VALUE;
            int minIdx=-1;
            // 일단 시작정점?
            for(int i=0; i<N; i++) {
                if(adjMatrix[start][i]<min) {
                    min = adjMatrix[start][i];
                    minIdx = i;
                }
            }
            //새로 추가된 정점의 인접 노드간 거리를 확인.
            for(int i=0; i<N; i++) {
                if(!visited[i]) {
                    if(minEdge[i] > adjMatrix[minIdx][i]) {
                        minEdge[i] = adjMatrix[minIdx][i];
                    }
                }
            }

            
			
		}
	}

    static void setPriceMatrix() {
        long distx, disty;
        for(int i=0; i<N; i++) {
            for(int j=i+1; j<N; j++) {
                distx = (long)x[i] - (long)x[j];
                disty = (long)y[i] - (long)y[j];
                adjMatrix[j][i] = adjMatrix[i][j] = E*(distx*distx + disty*disty);
            }
        }
    }

    static void init() throws IOException {
        N = nextInt();
        x = new int[N];
        y = new int[N];

        for(int i=0; i<N; i++) x[i] = nextInt();
        for(int i=0; i<N; i++) y[i] = nextInt();

        st.nextToken();
        E = st.nval;
        adjMatrix = new double[N][N];
        minEdge = new double[N];
        visited = new boolean[N];
        Arrays.fill(minEdge, Double.MAX_VALUE);
    }
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}