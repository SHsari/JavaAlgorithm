package a250226.bj16926arrSpin;
import java.io.*;

class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();
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
				idx = (idx+R)%cycleLength[depth];
				result.append(list[depth][idx]).append(" ");
			}
			result.append("\n");
		}
		
		System.out.println(result);
		
	}
	
	
	static int getDepthFrom(int row, int col) {
		return Math.min(Math.min(row, col), Math.min(N-1-row, M-1-col));
	}
	
	static int getNth(int depth, int row, int col) {
		row-=depth; col-=depth;
		int len = cycleLength[depth];
		if(row<=col) {
			return col+row;
		}else {
			return len - (col+row);
		}
	}

	static int nextInt() throws IOException {
		st.nextToken();
		return (int)st.nval;
	}

}