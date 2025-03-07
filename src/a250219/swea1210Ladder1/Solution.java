package a250219.swea1210Ladder1;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {
	// 사다리가 동일한 곳에 양쪽으로 나와있을 수 없다
	//
	// ㄱ좌우 너비를 102로 선언해버림 
	
	static StringBuilder result;
	static boolean[][] map;
	static int endpt;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		result = new StringBuilder();
		map = new boolean[100][102];

		for(int tc=1; tc<=10; tc++) {
			
			String tct = br.readLine(); // testCase 번호 입력 삭제
			//인풋 받기 -> map 완성
			for(int i=0; i<99; i++) {
				String line = br.readLine();
				for(int j=0; j<100; j++) {
					map[i][j+1] = line.charAt(j*2) == '1';
				}
			}
			
			String line = br.readLine();
			
			for(int j=0; j<100; j++) {
				map[99][j+1] = line.charAt(j*2) == '1';
				if(line.charAt(j*2) == '2') {
					map[99][j+1] = true;
					endpt = j+1;
				}
			}
			
			int tcResult = solve(endpt);
			//System.out.println("\n"+endpt);
			//System.out.println(tcResult);
			result.append("#").append(tc).append(" ");
			result.append(tcResult).append("\n");
		}
		
		System.out.println(result);
	}
	
	static int solve(int endJ) {
		int i = 100;
		int j = endJ;
		while(--i > 0) {
			for(int d=-1; d<2; d+=2) {
				int nj = j + d;
				if(map[i][nj]) {
					while( map[i][nj]) {
						nj += d;
					}

					j = nj-d;
					break;
				}
			}
		}
		return j-1;
	}

}
