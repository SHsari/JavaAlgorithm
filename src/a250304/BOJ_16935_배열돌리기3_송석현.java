package a250304;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/* BOJ 16935 배열돌리기 3
 *
 * 1번부터 6번까지 함수 구현했습니다.
 * 함수들을 List<Runnable>에 넣어서 코드를 간소화 했습니다.
 * 
 */

public class BOJ_16935_배열돌리기3_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static int N, M, R;
	static int map[][];
	static List<Runnable> funcList;

	
	public static void main(String[] args) throws IOException {
		StringBuilder result = new StringBuilder();
		init();
		for(int i=0; i<R; i++) {
			funcList.get(nextInt()-1).run();
		}
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				result.append(map[r][c]).append(" ");
			}
			result.append("\n");
		}
		System.out.println(result);
	}

	static void func1() {
		for(int r=0; r<N/2; r++) {
			int symR = N-1-r;
			int[] tmp = map[r];
			map[r] = map[symR];
			map[symR] = tmp;
		}
	}
	
	static void func2() {
		for(int r=0; r<N; r++) {
			for(int c=0; c<M/2; c++) {
				int symC = M-1-c;
				int tmp = map[r][c];
				map[r][c] = map[r][symC];
				map[r][symC] = tmp;
			}
		}	
	}
	
	static void func3() {
		int[][] newMap = new int[M][N];
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				newMap[c][N-1-r] = map[r][c];
			}
		}
		int tmp = M;
		M = N;
		N = tmp;
		map = newMap;
	}
	
	static void func4() {
		int[][] newMap = new int[M][N];
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				newMap[M-1-c][r] = map[r][c];
			}
		}
		int tmp = M;
		M = N;
		N = tmp;
		map = newMap;
	}
	
	static void func5() {

		int[] dRow = {0, N/2, 0, -N/2};
		int[] dCol = {M/2, 0, -M/2, 0};
		int[] stRow = {0, 0, N/2, N/2};
		int[] stCol = {0, M/2, M/2, 0};
		int[] endRow = {N/2, N/2, N, N};
		int[] endCol = {M/2, M, M, M/2};
		
		int[][] newMap = new int[N][M];
		
		for(int section=0; section<4; section++) {
			int dr = dRow[section];
			int dc = dCol[section];
			for(int r=stRow[section]; r<endRow[section]; r++) {
				for(int c=stCol[section]; c<endCol[section]; c++) {
					newMap[r+dr][c+dc] = map[r][c];
				}
			}
		}
		map = newMap;
	}
	
	static void func6() {

		int[] dRow = {N/2, 0, -N/2, 0};
		int[] dCol = {0, -M/2, 0, M/2};
		
		int[] stRow = {0, 0, N/2, N/2};
		int[] stCol = {0, M/2, M/2, 0};
		
		int[] endRow = {N/2, N/2, N, N};
		int[] endCol = {M/2, M, M, M/2};
		
		int[][] newMap = new int[N][M];
		
		for(int section=0; section<4; section++) {
			int dr = dRow[section];
			int dc = dCol[section];
			for(int r=stRow[section]; r<endRow[section]; r++) {
				for(int c=stCol[section]; c<endCol[section]; c++) {
					newMap[r+dr][c+dc] = map[r][c];
				}
			}
		}
		map = newMap;
	}
	
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        N = nextInt(); M = nextInt(); R = nextInt();
        map = new int[N][M];
        for(int r=0; r<N; r++) {
        	for(int c=0; c<M; c++) {
        		map[r][c] = nextInt();
        	}
        }
        funcList = new ArrayList<>();
        
        funcList.add(BOJ_16935_배열돌리기3_송석현::func1);
        funcList.add(BOJ_16935_배열돌리기3_송석현::func2);
        funcList.add(BOJ_16935_배열돌리기3_송석현::func3);
        funcList.add(BOJ_16935_배열돌리기3_송석현::func4);
        funcList.add(BOJ_16935_배열돌리기3_송석현::func5);
        funcList.add(BOJ_16935_배열돌리기3_송석현::func6);
        
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}