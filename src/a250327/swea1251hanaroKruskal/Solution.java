package a250327.swea1251hanaroKruskal;

import java.io.*;
import java.util.Arrays;


public class Solution {
    /*
     * SWEA 1251 하나로 Kruskal
     * 
     * N개의 노드가 주어질 때,
     * 최대 간선 그래프로부터
     * Minimum spanning Tree를 완성해라.
     *
     * 간선 밀도가 높기 때문에 kruskal 보다 Prim이 나을 것 같습니다.
     * 
     * 그렇지만 kruskal로 구현합니다. 
     */

     // 간선 객체.
    static class Edge implements Comparable<Edge>{
        double price;
        int i1, i2;
        Edge(int i1, int i2, double price) {
            this.i1 = i1;
            this.i2 = i2;
            this.price = price;
        }
        @Override
        // 추후 정렬을 위한 Comparable implements.
        public int compareTo(Edge o) {
            if(this.price > o.price) return 1;
            else if (this.price == o.price) return 0;
            else return -1;
        }
    }

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

    static int N;
    static int x[], y[];
    static double E;
    static Edge[] edges;

    //kruskal Algorithm을 위한 변수들
    static int parents[];
    static int rank[];
    
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
            //입력받고
            init();
            // 특수 케이스에 대해서 미리 처리하고
            if(N==1 || E==0) {
                result.append("0").append("\n");
                continue;
            }

            //노드 좌표로부터 간선정보를 생성하고 간선을 비용 오름차순으로 정렬합니다.
            makeAndSortEdges();
            double totalPrice =0;
            int selectedEdges=0;
            
            // 모든 간선을 오름차순으로 순회하며
            for(Edge edge : edges) {
                if(union(edge.i1, edge.i2)) {
                    totalPrice += edge.price;
                    if(++selectedEdges == N-1) break;
                }
            }
            result.append(Math.round(totalPrice)).append("\n"); 
		}
        System.out.println(result);
	}

    //UNION 
    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if(rootA != rootB) {
            if(rootA<rootB) parents[rootB] = rootA;
            else parents[rootA] = rootB;
            return true;
        } else return false;
    }

    //FIND
    static int find(int i) {
        if(parents[i] == i) return i;
        else {
            return parents[i] = find(parents[i]);
        }
    }

    static void makeAndSortEdges() {
        int idx =0;
        for(int i=0; i<N; i++) {
            for(int j=i+1; j<N; j++) {
                double price = getPriceBetween(i, j);
                edges[idx++] = new Edge(i, j, price);
            }
        }

        Arrays.sort(edges);
    }

    private static double getPriceBetween(int i, int j) {
        long distx, disty;
        distx = (long)x[i] - (long)x[j];
        disty = (long)y[i] - (long)y[j];
        return E*(distx*distx + disty*disty);
    }
	
    static void init() throws IOException {
        N = nextInt();
        x = new int[N];
        y = new int[N];
        edges = new Edge[N*(N-1)/2];
        parents = new int[N];
        for(int i=0; i<N; i++) parents[i] = i;

        for(int i=0; i<N; i++) x[i] = nextInt();
        for(int i=0; i<N; i++) y[i] = nextInt();

        st.nextToken();
        E = st.nval;
    }


	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}