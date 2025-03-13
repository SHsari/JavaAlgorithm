package a250220.guganHap5;
import java.io.*;

/*
 * boj 11660 구간합구하기 5
 * 
 * 숫자 배열을 N,N int 배열 map[][] 에 저장합니다.
 * 
 * map[row][col]에는
 * (1,1) ~ (row,col)까지의 직사각형의 합을 저장합니다.
 * 
 * 합 시작 점과 합 끝점 좌표를 받은 이후
 * 
 * x2, y2 사각형에서 
 * x1, y2 사각형을 빼주고
 * x2, y1 사각형을 빼주고
 * x1, y1 사각형을 더해줍니다.
 * 
 * 
 * 
 */

public class BOJ_11660_구간합구하기5_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static int[][] map;
	static int N, M;
	
	public static void main(String[] args) throws IOException {
		init();
		//print();
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<M; i++) {
			
			//여기서 -1을 붙이지 않아서 처음에 오류가 발생했습니다.
			int x1 = nextInt()-1;
			int y1 = nextInt()-1;
			int x2 = nextInt();
			int y2 = nextInt();
			
			int sum = map[x2][y2] - map[x2][y1] - map[x1][y2] + map[x1][y1];
			result.append(sum).append("\n");
			
		}
		System.out.println(result);

	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        N = nextInt()+1;
        M = nextInt();
        map = new int[N][N];

        for(int r=1; r<N; r++) {
        	for(int c=1; c<N; c++) {
        		map[r][c] = nextInt() + map[r][c-1] + map[r-1][c] - map[r-1][c-1];
        	}
        }
	}
	// 디버깅을 위한 프린트 함수
	static void print() {
		StringBuilder sb = new StringBuilder();
		for(int r=1; r<N; r++) {
			sb.append("\n");
			for(int c=1; c<N; c++) {
				sb.append(map[r][c]).append(" ");
			}
		}
		System.out.println(sb);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}