package a250410.swea5648atomicDecay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Solution {
	
	static class Atom implements Comparable<Atom> {
		int x, y, dir, e;
		double cTime;
		
		Atom(int x, int y, int dir, int e) {
			this.x = x; this.y = y;
			this.dir = dir; this.e = e;
			cTime = Integer.MAX_VALUE;
		}

		@Override
		public int compareTo(Atom o) {
			if(dir==0)	return this.y - o.y;
			else if(dir==1) return o.y - this.y;
			else if(dir==2) return o.x - this.x;
			else if(dir==3) return this.x - o.x;
			return 0;
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int dx[] = {0, 0, -1, 1};
	static int dy[] = {1, -1, 0, 0};
	static int N, dirIdx[];
	static Atom atoms[][];
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			for(int dir=0; dir<4; dir++) {
				for(int i=0; i<dirIdx[dir]; i++) {
					if(atoms[dir][i]
				}
			}
			
			
			
			
		}
	}
	
	static void solve() {
		int CE = 0;
		
		int d1 = 0, d2 = 1;
		// 1. 상,하;

		for(int i=0; i<dirIdx[d1]; i++) {
			for(int j=0; j<dirIdx[d2]; j++) {
				Atom a = atoms[d1][i];
				Atom b = atoms[d2][j];
				
				if(a.y < b.y) {
					if(a.x == b.x) {
						double cTime = Math.abs(a.y - b.y) / 2;
						if(a.cTime >= cTime && b.cTime >= cTime) {
							a.cTime = cTime; b.cTime = cTime;
						}
					}
				}
				else { break; }
			}
		}
		
		d1 = 0; d2 = 2;
		// 2. 상, 좌;
		
		for(int i=0; i<dirIdx[d1]; i++) {
			for(int j=0; j<dirIdx[d2]; j++) {
				Atom a = atoms[d1][i];
				Atom b = atoms[d2][j];
				
				if(a.y < b.y && a.x < b.x) {
					if(b.y-a.y == b.x-a.x) {
						int cTime = b.y-a.y;
						if(a.cTime >= cTime && b.cTime >= cTime) {
							a.cTime = cTime; b.cTime = cTime;
						}
					}
				}
			}
		}
		
		
		d1 = 0; d2 = 3; 
		//3. 상, 우;
		for(int i=0; i<dirIdx[d1]; i++) {
			for(int j=0; j<dirIdx[d2]; j++) {
				Atom a = atoms[d1][i];
				Atom b = atoms[d2][j];
				
				if(a.y < b.y && a.x < b.x) {
					if(b.y-a.y == a.x-b.x) {
						int cTime = b.y-a.y;
						if(a.cTime >= cTime && b.cTime >= cTime) {
							a.cTime = cTime; b.cTime = cTime;
						}
					}
				}
			}
		}
		
		
		d1 = 1; d2 = 2; 
		//4. 하, 좌;
		for(int i=0; i<dirIdx[d1]; i++) {
			for(int j=0; j<dirIdx[d2]; j++) {
				Atom a = atoms[d1][i];
				Atom b = atoms[d2][j];
				
				if(a.y > b.y && a.x < b.x) {
					if(b.y-a.y == a.x-b.x) {
						int cTime = a.y - b.y;
						if(a.cTime >= cTime && b.cTime >= cTime) {
							a.cTime = cTime; b.cTime = cTime;
						}
					}
				}
			}
		}
		
		
		d1 = 1; d2 = 3; 
		//4. 하, 우;
		for(int i=0; i<dirIdx[d1]; i++) {
			for(int j=0; j<dirIdx[d2]; j++) {
				Atom a = atoms[d1][i];
				Atom b = atoms[d2][j];
				
				if(a.y > b.y && a.x > b.x) {
					if(b.y-a.y == b.x - a.x) {
						int cTime = a.y - b.y;
						if(a.cTime >= cTime && b.cTime >= cTime) {
							a.cTime = cTime; b.cTime = cTime;
						}
					}
				}
			}
		}
		
		d1 = 2; d2 = 3; 
		//4. 좌, 우;
		for(int i=0; i<dirIdx[d1]; i++) {
			for(int j=0; j<dirIdx[d2]; j++) {
				Atom a = atoms[d1][i];
				Atom b = atoms[d2][j];
				
				if(a.x < b.x) {
					if(a.y == b.y) {
						double cTime = Math.abs(a.x - b.x) / 2;
						if(a.cTime >= cTime && b.cTime >= cTime) {
							a.cTime = cTime; b.cTime = cTime;
						}
					}
				}
				else { break; }
			}
		}
		
	}
	
	static void init() throws IOException {
		N = nextInt();
		atoms = new Atom[4][N];
		dirIdx = new int[4];
		
		for(int i=0; i<N; i++) {
			int x, y, dir, e;
			x = nextInt();
			y = nextInt();
			dir = nextInt();
			e = nextInt();
			atoms[dir][dirIdx[dir]++] = new Atom(x, y, dir, e);
		}
		
		for(int dir=0; dir<4; dir++) {
			Arrays.sort(atoms[dir], 0, dirIdx[dir]);
		}
	}


	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}