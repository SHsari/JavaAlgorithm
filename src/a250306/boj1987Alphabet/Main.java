package a250306.boj1987Alphabet;

import java.io.*;
import java.util.StringTokenizer;

/* BOJ 1987	알파벳.
 * 
 * visited 배열을 사용한 DFS로 풀었습니다.
 * 
 * 0~26범위를 가지는 visited 배열을 매 dfs 재귀마다 넘겨주는 형식입니다.
 * 
 * -> visited 배열을 넘겨주는 것이 어려워서 하나의 int의 bitmask를 대신 사용했습니다.
 * 
 */

public class Main {

	// 알파벳 지도를 Char 대신 int의 배열로 받기 위해 사용하는 offset character 입니다.
	// A~Z 까지 1~26 범위의 값을 가지도록 하기 위함입니다. 
	static final char OFFSETCHAR = 'A'-1;
	
	//방향 배열
	static final int[] dRow = {0,1,0,-1};
	static final int[] dCol = {1,0,-1,0};
	
	static BufferedReader br;

	// Row, Column
	static int R, C;
	// 결과값이 될 maxCount
	static int maxCount;
	// 알파벳 지도를 입력받을 map 배열
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        
        
        StringTokenizer rc = new StringTokenizer(br.readLine());
       
        R = Integer.parseInt(rc.nextToken());
        C = Integer.parseInt(rc.nextToken());
  
        map = new int[R+2][C+2];
        
        for(int row=1; row<=R; row++) {
        	char[] line = br.readLine().toCharArray();
        	for(int col=1; col<=C; col++) {
        		map[row][col] = line[col-1] - OFFSETCHAR;
        	}
        }
        // 입력완료
        
        
        maxCount =0;
        int bitmask =1;
        dfs(1, 1, 1, bitmask);
        
        
        System.out.println(maxCount);     
	}
	
	
	static void dfs(int row, int col, int count, int visited) {
		//현재 row와 column의 Alphabet에 대하여 Visited 처리를 합니다.
		visited = setVisitedAt(map[row][col], true, visited);
	
		// 재귀를 했는지 여부를 판단하기 위한 변수 nextCount
		int nextCount=0;
		// 4방탐색
		for(int dir=0; dir<4; dir++) {
			int nextRow = row + dRow[dir];
			int nextCol = col + dCol[dir];
			int alphaIdx = map[nextRow][nextCol];
			if(!isVisited(alphaIdx, visited)) {
				nextCount++;
				dfs(nextRow, nextCol, count+1, visited);
			}
		}
		// 재귀하지 않았다면(더이상 이동할 곳이 없었다면)
		if(nextCount==0 && count>maxCount) maxCount = count; 
	}
	
	// 비트마스크 동작 함수
	static int setVisitedAt(int index, boolean value, int bitmask) {
		if(value) return bitmask | (1 << index);
		else return bitmask &= ~(1 << index);
	}
	
	static boolean isVisited(int index, int bitmask) {
		return (bitmask & (1 << index)) != 0 ;
	}

}