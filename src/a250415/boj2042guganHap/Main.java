package a250415.boj2042guganHap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.TreeSet;


public class Main {
	static class Change implements Comparable<Change> {
		int at;
		long diff;
		Change(int at, long diff) {
			this.at = at; this.diff = diff;
		}
		@Override
		public int compareTo(Change o) {
			return this.at - o.at;
		}
		@Override
		public boolean equals(Object o) {
			if(this == o) return true;
			else if(o == null || this.getClass() != o.getClass()) return false;
			Change other = (Change) o;
			return this.at == other.at;
		}
	}
	
	static BufferedReader br;
	static StreamTokenizer st;
	
	static int N, M, K, changeCnt;
	static long origin[];
	static TreeSet<Change> changes;
	
	public static void main(String[] args) throws IOException {

		
	}
	
	static StringBuilder solve() throws IOException {
	   for(int i=0; i<M+K; i++) {
        	int command = nextInt();
        	
        	// change
        	if (command == 1) {
        		int idx = nextInt();
        		st.nextToken();
        		long newValue = (long) st.nval;
        		long ogValue = origin[idx] - origin[idx-1];
        		
        		changes.add(new Change(idx, newValue - ogValue));
        	}
        	else if (command == 2) {
        		int startIdx = nextInt();
        		int endIdx = nextInt();
        	}
        }
	}
	
	static void addChange(Change newChange) {
		if(changes.contains(newChange)) {
			changes.
		}
	}
	
	static long getGGH(int from, int to) {
		long ggh = origin[to] - origin[from-1];
		
		int ceilingIdx = from;
	}

	static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
      
        N = nextInt();
        M = nextInt();
        K = nextInt();
        
        origin = new long[N+1];
        changes = new TreeSet<>();
        for(int i=1; i<=N; i++) {
        	st.nextToken();
        	origin[i] = (long)st.nval + origin[i-1];
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}