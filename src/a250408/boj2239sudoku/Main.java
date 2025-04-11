package a250408.boj2239sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Main {
	/* boj 2239 스도쿠
	 * 
	 * 결론적으로 오답인데, 그 이유가.
	 * 최소 조건을 만족하지 않아서 인것으로 보임.
	 * 
	 * 특정위치에 대해 후보 숫자를 순서대로 순회하기 전에,
	 * 최소후보를 가진 위치를 고르는 작업이 우선되는데
	 * 
	 * 위의 과정때문에 최소 숫자 조건을 만족하지 못하는 것으로 보인다.
	 */
	
	// 스도쿠 퍼즐에서 하나의 좌표를 표현하는 클래스
	static class Coordinate implements Comparable<Coordinate> {
		int row, col; // 좌표의 row, column
		// 각 좌표가 소속된 row, column, block의 숫자 사용 현황
		boolean[] horHas, verHas, blockHas;
		
		Coordinate(int row, int col) {
			this.row = row; this.col = col;
			horHas = hor[row];
			verHas = ver[col];
			blockHas = block[blockOf(row,col)];
		}
		
		//후보의 리스트를 반환
		public List<Integer> getCandidateList() {
			List<Integer> candidate = new ArrayList<>();	
			for(int i=1; i<=9; i++) {
				if(!verHas[i] && !horHas[i] && !blockHas[i])
					candidate.add(i);
			}
			return candidate;
		}
		
		@Override
		public String toString() {
			return "[" + row + ", " + col + "]";
		}
	}

	static int[][] map;
	static boolean[][] ver, hor, block;
	
	static Queue<Coordinate> coordinates = new ArrayDeque<>();
	
	public static void main(String[] args) throws IOException {
		init();
		
		dfs();

		printMap();
	}
	
	public static void printMap() {
		StringBuilder sb = new StringBuilder();
		for(int r=0; r<9; r++) {
			for(int c=0; c<9; c++) {
				sb.append(map[r][c]);
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	public static boolean dfs() {
		if(coordinates.isEmpty()) return true;
		
		// dfs backtracking 시 이번 depth의 설정을 rollback 하기 위한
		// updated Coordinate list
		Queue<Coordinate> updated = new ArrayDeque<>();
		
		// 후보의 갯수가 가장 적은 것.
		Coordinate coor = coordinates.poll();
	
		List<Integer> candidates = coor.getCandidateList();
		while(candidates.size()==1) {
			
			put(coor.row, coor.col, candidates.get(0));
			updated.add(coor);
			
			if(coordinates.isEmpty()) return true;
			coor = coordinates.poll();
			candidates = coor.getCandidateList();
		}
		if(candidates.size()>1) {
			for(int canNum : candidates) {
				put(coor.row, coor.col, canNum);
				if(dfs()) return true;
				else rollback(coor.row, coor.col);
			}
			coordinates.add(coor);
		}
		
		for(Coordinate rbCoor : updated) {
			rollback(rbCoor.row, rbCoor.col);
		}
		updated.addAll(coordinates);
		coordinates = updated;
		return false;
	}
	
	static void rollback(int row, int col) {
		int num = map[row][col];
		map[row][col] =0;
		ver[col][num] = false;
		hor[row][num] = false;
		block[blockOf(row,col)][num] = false;
	}
	
	static void put(int row, int col, int num) {
		map[row][col] = num;
		ver[col][num] = true;
		hor[row][num] = true;
		block[blockOf(row, col)][num] = true;
	}
	
	private static int blockOf(int row, int col) {
		return (row/3) * 3 + col/3;
	}

	static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new int[9][9];
        ver = new boolean[9][10];
        hor = new boolean[9][10];
        block = new boolean[9][10];
        for(int i=0; i<9; i++) 
        	ver[i][0] = hor[i][0] = block[i][0] = true; 	
        
        for(int r=0; r<9; r++) {
        	char[] row = br.readLine().toCharArray();
        	for(int c=0; c<9; c++) {
        		if(row[c] == '0') {
        			coordinates.add(new Coordinate(r,c));
        			continue;
        		} else {
        			int value = row[c] - '0';
        			put(r,c,value);
        		}
        	}
        }
	}
}