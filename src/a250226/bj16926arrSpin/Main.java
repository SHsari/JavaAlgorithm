package a250226.bj16926arrSpin;
import java.io.*;
/* BAEKJOON 16926
 * 배열을 회전시키는 과제입니다.
 * 
 * 
 * 
 */


class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	// N과 M은 배열의 세로와 가로길이.
	// 각각 row column 에 대응됩니다.
	// R은 회전 횟수
	// list는 배열이 여러 depth의 Cycle로 이뤄졌다고 볼때,
	// [depth][cycleIndex]로 접근합니다.
	// cycleLength는 depth별로 cycle의 길이를 저장합니다.

	static int N, M, R;
	static int[][] list;
	static int[] cycleLength;


	public static void main(String[] args) throws IOException {
		N = nextInt(); M = nextInt(); R = nextInt();
		
		int maxDepth = Math.min(N, M)/2;
		list = new int[maxDepth][];
		cycleLength = new int[maxDepth];
		
		// depth별로 cycleLength 초기화
		for(int depth=0; depth<maxDepth; depth++) {
			int length = (N-(depth*2))*2 + (M-(depth*2))*2 - 4;
			cycleLength[depth] = length;
			list[depth] = new int[length];
		}
		
		// list 입력 -> cycle별로 입력한다.
		for(int row=0; row<N; row++) {
			for(int col=0; col<M; col++) {
				int depth = getDepthFrom(row, col);
				int idx = getNth(depth, row, col);
				list[depth][idx] = nextInt();
			}
		}
		
		
		// 마지막으로 순회하면서 +R 위치에 있는 숫자를 출력한다.
		for(int row=0; row<N; row++) {
			for(int col=0; col<M; col++) {
				int depth = getDepthFrom(row, col);
				int idx = getNth(depth, row, col);
				// cycle이기 때문에 cycle의 length로 mod 연산하여 index에 접근합니다.
				idx = (idx+R)%cycleLength[depth];
				result.append(list[depth][idx]).append(" ");
			}
			result.append("\n");
		}
		System.out.println(result);
	}
	
	// 간단히 row, column 값을 통해 몇번째 깊이의 cycle에 접근해야 하는 지 depth를 알려주는 함수.
	static int getDepthFrom(int row, int col) {
		return Math.min(Math.min(row, col), Math.min(N-1-row, M-1-col));
	}
	
	// row, column, depth 값을 통해 cycle의 몇번째 원소에 접근해야 하는 지 알려주는 함수
	static int getNth(int depth, int row, int col) {
		row-=depth; col-=depth;
		int len = cycleLength[depth];
		if(row==0) return col;
		else if(col==0) return len-row;
		else if(col==M-1 - depth*2) return row + col;
		else return len-(row+col);
	}

	// 왕빠른 스트림 토크나이져.
	static int nextInt() throws IOException{
		st.nextToken();
		return (int)st.nval;
	}
}