package a250409.boj1613history;

import java.io.*;


public class Main {
	/*
	 * BOJ 1613	역사
	 * 키 순서 문제가 생각난다.
	 * 
	 * 사건번호 2개와 전후 관계가 여러개 주어진다.
	 * 
	 * 이후 주어진 전후 관계를 통해서
	 * 주어지는 임의의 사건번호 쌍에 대해서 전후관계를 알 수 있는 지 여부를 출력.
	 * 
	 * 최단경로도 O(N^3)
	 * 
	 * 연결 여부도 O(N^3)
	 * 
	 * 플로이드 워셜.
	 * 알수가 없다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;

    static int N, K;
    static int matrix[][];

	public static void main(String[] args) throws IOException {

		// 전후관계 입력받고
        init();
        
        // 플로이드 워셜을 통해 알 수 있는 모든 전후관계에 대해 업데이트
        for(int k=1; k<=N; k++) {
            for(int j=1; j<=N; j++) {
                if(k==j || matrix[k][j] == 0) continue;
                for(int i=1; i<=N; i++) {
                    if(matrix[i][k] ==1 && matrix[k][j] == 1) {
                        matrix[i][j] = 1;
                        matrix[j][i] = -1;
                    }
                    else if(matrix[i][k] == -1 && matrix[k][j] == -1) {
                        matrix[i][j] = -1;
                        matrix[j][i] = 1;
                    }
                }
            }
        }
        
        // answer()을 통해 질문을 입력받고 답 출력.
        System.out.println(answer());
	}


    static StringBuilder answer() throws IOException {
        StringBuilder result = new StringBuilder();
        int S = nextInt();
        for(int i=0; i<S; i++) {
            int j, k;
            j = nextInt();
            k = nextInt();
            result.append(matrix[k][j]).append("\n");
        }
        return result;
    }

    static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);

        N = nextInt();
        K = nextInt();

        matrix = new int[N+1][N+1];

        for(int i=0; i<K; i++) {
            int pre, post;
            pre = nextInt();
            post = nextInt();

            matrix[pre][post] = 1;
            matrix[post][pre] = -1;
        }

	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}