package a250409.boj1613history;

import java.io.*;


public class Main {

	static BufferedReader br;
	static StreamTokenizer st;

    static int N, K;
    static int matrix[][];

	public static void main(String[] args) throws IOException {

        init();
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