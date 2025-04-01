package a250331.swea1251hanaroPrim;

import java.io.*;
import java.util.Arrays;

public class Solution{
    /*
     * SWEA 1251 하나로 Prim
     * 
     * 
     * N개의 노드, 각 노드의 좌표가 주어질때
     * 최대 밀도의 간선으로 이뤄진 그래프로부터
     * Minimum spanning Tree를 완성해라.
     *
     * 간선 밀도가 높기 때문에 kruskal 보다 Prim이 나을 것 같습니다.
     * 
     * Prim으록 구현합니다.
     */
	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

	//node의 갯수 N
	//x, y좌표를 임시로 저장하는 x, y 배열
	//Expanse Rate E. 거리제곱*E가 간선의 비용.
    static int N;
    static int x[], y[];
    static double E;

    static double[][] expanseMatrix;
    static boolean[] isTree;
    static double[] minExpanseTo;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
            init();
            setPriceMatrix();

            // 아래부터 프림 알고리즘
            // 생각보다 길지 않군여.
            double edgeSum =0; 
            int addedVertex=0;
            int vertexNum=1;
            isTree[addedVertex] = true;
            
            while(vertexNum<N) {
	            // 새로 추가된 정점의 인접 노드간 거리를 확인하여
            	// 비트리 노드까지의 최소 거리를 업데이트
	            // 동시에 가장 가까운 비트리 노드를 구하기.
	            int minIdx=-1;
	            double min = Double.MAX_VALUE;
	            for(int i=0; i<N; i++) {
	                if(!isTree[i]) {
	                    if(minExpanseTo[i] > expanseMatrix[addedVertex][i]) {
	                        minExpanseTo[i] = expanseMatrix[addedVertex][i];
	                    }
	                    if(minExpanseTo[i] < min) { 
	                    	min = minExpanseTo[i];
	                    	minIdx = i;
	                    }
	                }
	            }
	            
	            // 정해진 가장 가까운 비트리노드를 추가.
	            isTree[minIdx] = true;
	            vertexNum++;
	            edgeSum+=min;
	            addedVertex = minIdx;
            }	
            result.append(Math.round(edgeSum)).append("\n");
    		System.out.println(tc);
		}

		System.out.println(result);
	}

	// 노드의 좌표로부터 모든 가능한 간선의 비용을 구하고 expanseMatrix 완성
    static void setPriceMatrix() {
        long distx, disty;
        for(int i=0; i<N; i++) {
            for(int j=i+1; j<N; j++) {
                distx = (long)x[i] - (long)x[j];
                disty = (long)y[i] - (long)y[j];
                expanseMatrix[j][i] = expanseMatrix[i][j] = E*(distx*distx + disty*disty);
            }
        }
    }

    
    // 초기화 및 입력 함수
    static void init() throws IOException {
        N = nextInt();
        x = new int[N];
        y = new int[N];

        for(int i=0; i<N; i++) x[i] = nextInt();
        for(int i=0; i<N; i++) y[i] = nextInt();

        st.nextToken();
        E = st.nval;
        expanseMatrix = new double[N][N];
        minExpanseTo = new double[N];
        isTree = new boolean[N];
        Arrays.fill(minExpanseTo, Double.MAX_VALUE);
    }
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}