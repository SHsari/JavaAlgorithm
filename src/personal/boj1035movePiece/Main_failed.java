package personal.boj1035movePiece;
import java.io.*;
import java.util.Arrays;

public class Main_failed {
	
	static BufferedReader br;
	static StreamTokenizer st;
	static int starCnt, meanRow, meanCol;
	static int[] starRow;
	static int[] starCol;
	static int[][] mDist;
	static Edge[] edges;
	
	
	static class Edge implements Comparable<Edge>{
		int idx1, idx2, dist;
		
		Edge(int idx1, int idx2, int dist) {
			this.idx1 = idx1;
			this.idx2 = idx2;
			this.dist = dist;
		}
		public int compareTo(Edge o) {
			return this.dist - o.dist; 
		}
	}

	public static void main(String[] args) {
		
	}
	
	private static void solve() {
		Arrays.sort(edges);
		
		
	}
	
	private static void init() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StreamTokenizer(br);
		starCnt = 0;
		starRow = new int[6];
		starCol = new int[6];
		int rowSum =0, colSum=0;
		for(int r=0; r<5; r++) {
			char[] line = nextStr().toCharArray();
			for(int c=0; c<5; c++) {
				if(line[c] == '*') {
					starRow[starCnt] = r;
					starCol[starCnt] = c;
					rowSum+=r;
					colSum+=c;
					starCnt++;
				}
			}
		}
		
		meanRow = rowSum/starCnt;
		meanCol = colSum/starCnt;
		starRow[starCnt] = meanRow;
		starCol[starCnt] = meanCol;
		
		edges = new Edge[starCnt*(starCnt-1)/2];
		int distIdx = 0;
		mDist = new int[starCnt][starCnt];
		int minDist = 20;
		for(int i=0; i<starCnt; i++) {
			for(int j=i+1; j<starCnt; j++) {
				int dist = getMDist(i,j);
				mDist[i][j] = mDist[j][i] = dist;
				edges[distIdx++] = new Edge(i, j, dist);
				if(dist<minDist) minDist = dist;
			}
		}
	}
	
	private static int getMDist(int first, int second) {
		return Math.abs(starRow[first]-starRow[second]) +
		Math.abs(starCol[first]-starCol[second]);
	}
	
	private static String nextStr() throws IOException {
		st.nextToken();
		return st.sval;
	}
}
/*
*...*
.....
.....
.....
*...*
*/