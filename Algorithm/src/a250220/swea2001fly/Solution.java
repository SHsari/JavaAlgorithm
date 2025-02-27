package a250220.swea2001fly;

/*
 * SWEA 2001 파리퇴치
 * N, M이 충분히 작아서
 * 그냥 완탐으로 풀어도 될 것 같으다.
 * 슬라이딩 윈도우를 쓰면 좀 더 효율적이 되겠다.
 * 
 */

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Solution {
	
	static int mapLen;
	static int killBox;
	static int[][] map;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			mapLen = Integer.parseInt(st.nextToken());
			killBox = Integer.parseInt(st.nextToken());
			map = new int[mapLen][mapLen];
			
			for(int row=0; row<mapLen; row++) {
				StringTokenizer lineST = new StringTokenizer(br.readLine());
				for(int col=0; col<mapLen; col++) { //j는 column을 결정
					map[row][col] = Integer.parseInt(lineST.nextToken());
				}
			}
			// 입력완료
			
			sb.append("#" + tc + " ").append(tamsaek()).append("\n");
			
		}
		System.out.println(sb);
	}
	
//	슬라이딩 윈도우 없이 완탐 조져버렸습니다.
	static int tamsaek() {
		// 0,0 시작점 에서의 값.
		int bound = mapLen - killBox;
		int max = 0;
		
		for(int row=0; row<=bound; row++) {
			for(int col=0; col<=bound; col++) {
				int killnum = getKillNum(row, col, killBox, killBox);
				if(killnum>max) max = killnum;
			}
		}
		return max;
	}
	
	static int getKillNum(int row, int col, int rowLen, int colLen) {
		int sum=0;
		for(int dr=0; dr<rowLen; dr++) { //rowLen만큼 row 순회
			for(int dc=0; dc<colLen; dc++) { //colLen만큼 col 순회
				int cRow = row+dr; // currentRow
				int cCol = col+dc; // currentColumn
				sum += map[cRow][cCol];
			}
		}
		return sum;
	}
	
}
