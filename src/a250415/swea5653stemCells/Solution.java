package a250415.swea5653stemCells;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Solution {
	/* 1. map의 특정 위치에 위치한 줄기세포는 vitality(생명력) 속성을 가진다.
	 * 
	 * 줄기세포는 생성한 후 vitality+1 시간이 지나면 사방으로 분화,
	 * vitality*2 시간이 지나면 사라진다.
	 * 
	 * 같은 시간에 서로 다른 줄기세포에서 같은 좌표로 분화를 하게 될 경우
	 * vitality가 큰것으로 분화된다.
	 * 
	 * 살아있는 줄기세포 list를 만들어서
	 * 매 시간마다 살아있는 줄기세포 리스트를 순회합니다.
	 *  분화시간이 된 세포에 대해 사방탐색-> 분화-> 리스트 추가
	 * 	소멸시간이 된 세포에 대해 리스트에서 삭제.
	 * 
	 * 문제에서 지정한 시간이 되면 해당 리스트의 Size를 통해
	 * 살아있는 세포의 수를 알 수 있습니다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
    static int N, M, totalTime;
    static int[][] vitalityOf, generatedTimeOf;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			List<Point> liveCells = init();
			int liveCellCnt = solve(liveCells, vitalityOf, generatedTimeOf);
			
			result.append(liveCellCnt).append("\n");
		}
		System.out.println(result);
	}
	
	/*
	 * 진행중에 다음과 같은 실수가 있었습니다.
	 * 
	 * 1. 중간에 로직을 살짝 변경했는데, 이를 초기화 함수에 완벽히 반영하지 않음
	 * 2. 분화 타이밍을 1초 앞서게 잡아서 분화가 훨씬 빨리 됨
	 * 3. 분화와 소멸이 동시에 될 수 있음을 고려하지 않음
	 * 
	 * 2, 3번 실수에 대해서 로직이 잘못된 줄 알고 열심히 파싱하다가
	 * 더 많은 시간의 소요가 있었습니다.
	 * 
	 */
	
	static int solve(List<Point> liveCells, int[][] map, int[][] gTime) {
		int[] dRow = {0, 1, 0, -1};
		int[] dCol = {1, 0, -1, 0};
		
		for(int t=1; t<=totalTime; t++) {
			
			List<Point> newCells = new ArrayList<>();
			
			// list를 순회하면서 동시에 삭제도 하기 위해서 iterater 사용.
			Iterator<Point> iter = liveCells.iterator();
			while(iter.hasNext()) {
				Point curr = iter.next();
				int generated = gTime[curr.x][curr.y];
				int vitality = map[curr.x][curr.y];
				

				// 분화시점이 된 세포에 대해서
				if(t - generated == vitality+1) { // vitality+1 시점에 분화
					// 사방탐색
					for(int dir=0; dir<4; dir++) {
						int nextR = curr.x + dRow[dir];
						int nextC = curr.y + dCol[dir];
						
						if(gTime[nextR][nextC] > t) {
							gTime[nextR][nextC] = t;
							map[nextR][nextC] = vitality;
							newCells.add(new Point(nextR, nextC));
						}
						
						// 동시에 같은공간 분화시 vitality가 큰것 우선
						else if(gTime[nextR][nextC] == t) {
							if(map[nextR][nextC] < vitality) {
								map[nextR][nextC] = vitality;
							}
						}
					}
				}
				
				// 소멸시점에 삭제.
				if(t - generated >= vitality * 2) {
					// 소멸시점 정확하나 분화와 동시에 가능함. (else if 안됨)
					iter.remove();
				}
			}
			// 현재 타임에 새로 생성된 세포들을 다음 타임 live list에 추가.
			liveCells.addAll(newCells);
			
		}
		
		return liveCells.size();
	}
	
	static List<Point> init() throws IOException {
		N = nextInt();
		M = nextInt();
		totalTime = nextInt();
		
		int offset = totalTime / 2 + 2;
		vitalityOf = new int[N+2*offset][M+2*offset];
		generatedTimeOf = new int[N+2*offset][M+2*offset];
		for(int i=0; i< N+2*offset; i++) {
			Arrays.fill(generatedTimeOf[i], Integer.MAX_VALUE);
		}
		
		List<Point> liveCells = new ArrayList<>();
		
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				int vitality = nextInt();
				
				if(vitality >0) {
					vitalityOf[offset+r][offset+c] = vitality;
					generatedTimeOf[offset+r][offset+c] = 0;
					Point newCell = new Point(offset+r, offset+c);
					liveCells.add(newCell);
				}
			}
		}
		
		return liveCells;
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}