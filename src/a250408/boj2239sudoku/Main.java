package a250408.boj2239sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	/* boj 2239 스도쿠
	 * 
	 * 1. 인풋을 받아서 unfilledR, unfilledC 배열에 채워지지 않은 부분의 row, col을 순서대로 저장
	 * 
	 * 2. 다음은 숫자의 현황을 확인할 수 있는 boolean 배열에 대한 명시입니다.
	 * 	2-1  vertical[column번호][숫자] 를 통해서 해당 새로줄에 숫자가 존재하는지 판단
	 *  2-2  horizontal[row번호][숫자] 를 통해서 해당 가로줄에 숫자가 존재하는지 판단
	 *  2-3  blockOf(row, column)은 boolean 배열을 반환,
	 * 		역시 blockOf(row, column)[숫자] 를 통해 해당 3x3블럭에 숫자가 존재하는 지 판단.
	 * 
	 * 3. getCandidatesOf(row, column)은 해당 좌표에 들어갈 수 있는 모든 숫자후보를 반환.
	 * 		3-1. 숫자 1~9를 순회하며 2번에 명시한 배열을 이용해 들어갈 수 있는 숫자인 지 판단하고 후보 배열로 반환.
	 * 
	 * 4. 왼쪽 위 부터 순서대로 모든 가능성에 대해 순회하고 모든 숫자를 채우면 정지.
	 * 		4-1. dfs로 unfilled 배열을 순회.
	 * 		4-2. 3번을 이용해서 해당 unfilled 좌표에 대해서 후보를 받음
	 * 		4-3. 후보가 1개라면 바로 집어넣고 다음 좌표에 대해 4-2번 반복
	 * 		4-4. 후보가 여러개라면 해당 후보를 순회하는데,
	 * 			4-4-1. 넣어보고 다음 좌표에 대해서 재귀호출
	 * 				재귀호출에 대한 반환값이 false이면 rollback 하고 다음 후보 test.
	 * 		
	 * 		4-5. 모든 후보를 test한 결과가 false 거나 현 좌표에 대한 후보가 없다면
	 * 			4-5-1. 현재 재귀깊이에서 채운 모든 좌표를 다시 되돌리기.
	 * 			4-5-2. false 반환.
	 */

	static int[][] map;
	static boolean[][] ver, hor, block;
	static int[] unfilledR;
	static int[] unfilledC;
	static int unfilledCnt;

	public static void main(String[] args) throws IOException {
		init();

		if(!dfs(0)) {
			System.out.println(-1);
			return;
		}

		printMap();
	}
	
	public static boolean dfs(int startIdx) {
		// 채워지지 않은 모든 좌표를 다 채웠다면,
		if(startIdx == unfilledCnt) return true;
		
		// 초기 셋팅 -> 좌표 가져오기 -> 좌표에 대한 후보 가져오기.
		int nextIdx = startIdx;
		int row = unfilledR[nextIdx];
		int col = unfilledC[nextIdx];
		int[] candi = getCandidatesAt(row, col) ;
		int candiNum = candi[9];
		//System.out.println("candiNum :" + candiNum+ ", arr: " + Arrays.toString(candi));
		
		// 후보가 하나이면 추가 재귀 없이 바로 해결 후
		// 다음 좌표에 대한 후보를 받아오기를 반복.
		while(candiNum == 1) {
			put(row, col, candi[0]);

			if(++nextIdx == unfilledCnt)
				return true;
			
			row = unfilledR[nextIdx];
			col = unfilledC[nextIdx];
			candi = getCandidatesAt(row, col);
			candiNum = candi[9];
		}
		
		// 후보가 많으면
		if(candiNum > 1) {
			for(int idx=0; idx<candiNum; idx++) {
				put(row, col, candi[idx]);
				if(dfs(nextIdx+1))
					return true;
				else rollback(row, col);
			}
		}

		// 후보가 없거나, 모든 후보를 시도해봐도 답이 없을 때,
		// 현 함수에서 채워놓은 모든 좌표를 돌려놓음.
		for(int rollBackIdx = startIdx; rollBackIdx < nextIdx; rollBackIdx ++)
			rollback(unfilledR[rollBackIdx], unfilledC[rollBackIdx]);

		return false;
	}

	// 숫자가 작은 순서대로 후보를 반환하는 함수.
	static int[] getCandidatesAt(int row, int col) {
		// candidates[9] 은 후보의 갯수로 하자.
		int candidates[] = new int[10];
		int candiIdx = 0;
		boolean currBlock[] = blockOf(row, col); 

		for(int i=1; i<=9; i++)
			if( !ver[col][i] && !hor[row][i] &&	!currBlock[i] )
				candidates[candiIdx++] = i;

		candidates[9] = candiIdx;
		return candidates;
	}
	
	// 집어넣었던 숫자를 다시 취소하기.
	static void rollback(int row, int col) {
		int num = map[row][col];
		map[row][col] = 0;
		ver[col][num] = false;
		hor[row][num] = false;
		blockOf(row,col)[num] = false;
	}
	
	//숫자를 해당 좌표에 집어넣기.
	static void put(int row, int col, int num) {
		map[row][col] = num;
		ver[col][num] = true;
		hor[row][num] = true;
		blockOf(row, col)[num] = true;
	}
	
	// 해당 좌표가 속한 block을 반환
	private static boolean[] blockOf(int row, int col) {
		return block[(row/3) * 3 + col/3];
	}

	static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new int[9][9];
        ver = new boolean[9][10];
        hor = new boolean[9][10];
        block = new boolean[9][10];

		unfilledCnt = 0;
		unfilledR = new int[81];
		unfilledC = new int[81];

        for(int i=0; i<9; i++) 
        	ver[i][0] = hor[i][0] = block[i][0] = true; 	
        
        for(int r=0; r<9; r++) {
        	char[] row = br.readLine().toCharArray();
        	for(int c=0; c<9; c++) {
				int value = row[c] - '0';
				if(value == 0) {
					unfilledR[unfilledCnt] = r;
					unfilledC[unfilledCnt++] = c;
				}
				else put(r, c, value);
        	}
        }
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
}