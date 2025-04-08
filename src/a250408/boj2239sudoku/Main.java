package a250408.boj2239sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;


public class Main {
	
	static class Block {
		boolean[] has = new boolean[10];
		int unAdded = 9;
		Block() { has[0] = true; }
		public void set(int num) {
			if(!has[num]) {
				has[num] = true;
				unAdded--;
			}
		}
	}
	
	static class Coor implements Comparable<Coor> {
		int row, col;
		@Override
		public int compareTo(Coor o) {
			return 0;
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	
	static int[][] map;
	static Block[] ver, hor, block;
	static List<Coor> candidate = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {

	}
	
	private static int getCandidateAt(int row, int col) {
		
	}
	
	private static int blockOf(int row, int col) {
		return (row/3) * 3 + col/3;
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        map = new int[9][9];
        ver = new Block[9];
        hor = new Block[9];
        block = new Block[9];
        
        for(int r=0; r<9; r++) {
        	st.nextToken();
        	char[] row = st.sval.toCharArray();
        	for(int c=0; c<9; c++) {
        		map[r][c] = row[c];
        		hor[r].set(row[c]);
        		ver[c].set(row[c]);
        		block[blockOf(r,c)].set(row[c]);
        	}
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}