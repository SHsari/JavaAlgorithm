package liveClass;

import java.io.*;


public class NQueen {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	
	static boolean[] col, slash, bSlash;
	static int N, count;
	
	public static void main(String[] args) throws IOException {
		N = nextInt();
		col = new boolean[N+1];
		slash = new boolean[2*N+1];
		bSlash = new boolean[2*N];
		count =0;
		setQueens(1);
		System.out.println(count);
	}
	
	
	static void setQueens(int rowNo) {
		
		if(rowNo>N) { count ++; return;}
			
		for(int c=1; c<=N; c++) {
			
			if(!isAvailable(rowNo, c)) continue;
			
			
			col[c] = slash[rowNo+c] = bSlash[(rowNo-c) +N] = true;
			setQueens(rowNo+1);
			col[c] = slash[rowNo+c] = bSlash[(rowNo-c) +N] = false;
		}
	}
	
	static boolean isAvailable(int rowNo, int c) {
		return !col[c] && !slash[rowNo+c] && !bSlash[rowNo-c+N];
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int)st.nval;
	}
}
