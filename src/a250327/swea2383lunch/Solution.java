package a250327.swea2383lunch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Solution {
	/*
	 * swea 2383 점심식사시간
	 * N*N의 맵이 주어진다.
	 * 
	 * 맵에는 빈공간, 사람, 계단
	 * 으로 이뤄져 있다. 사람이 계단 까지 이동하는데 걸리는 시간은
	 * 맨하탄 distance*minute
	 * 
	 * 계단은 동시에 3명 이상이 올라가 있을 수 없고.
	 * 각 계단에는 내려가는데 걸리는 시간이 주어진다.
	 * 
	 * 아래는 포함구간으로 한다.
	 * 계단의 갯수 2개
	 * 사람명수 1~10
	 * 맵의 크기 N 4~10
	 * +계단의 길이 2~10
	 * 
	 * 1. 사람마다 선택지가 2가지
	 *  - 부분집합으로 푼다. 최대 2^10 * 평균시간.
	 *  
	 * 2. 다른방법은 떠오르지 않어.
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static class Person {
		int r, c, dist[];
		Person(int row, int col) {
			this.r = row; this.c = col;
			this.dist = new int[2];
		}
	}
	
	static class Stair {
		int r, c, length, onTopCnt, onComingIdx, stairIdx;
		List<Person> selected;
		LinkedList<Integer> enterTime;
		Stair(int idx, int row, int col, int len) {
			this.stairIdx = idx;
			this.r = row; this.c = col;
			this.length = len;
		}
		void init() {
			selected = new ArrayList<>();
			enterTime = new LinkedList<>();
			onTopCnt=0;
			onComingIdx=0;
		}
		
		int simulate() {
			System.out.println("stair "+ stairIdx);
			int time = 0;

			while(onComingIdx < selected.size() || onTopCnt>0) {
				time++;
				
				while(onComingIdx<selected.size()){
					Person p = selected.get(onComingIdx);
					if(p.dist[stairIdx]<=time) {
						onTopCnt++;
						onComingIdx++;
					} else break;
				}
				
				
				while(!enterTime.isEmpty()){
					int stairTime = time - enterTime.peek();
					if(stairTime >= length) enterTime.poll();
					else { break; }
				}
				
				int qsize = enterTime.size();
				
				for(;qsize<3 && onTopCnt>0; qsize++, onTopCnt--) {
					enterTime.add(time);
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append("t: ").append(time).append(", qsize: ").append(qsize);
				sb.append(", topCnt: ").append(onTopCnt).append(", onComing: ").append(onComingIdx);
				sb.append(", realQ: ").append(enterTime.size());
				System.out.println(sb);
			}
			
			return enterTime.getLast() + length;
		}
	}
	
	
	static int N, Time, peopleCnt;
	static int[][] map;
	static Person[] people;
	static Stair[] stairs;
	
	public static void main(String[] args) {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			initAndGetInput();
			int minEndTime = Integer.MAX_VALUE;
			
			int maxBitmask = 1 << peopleCnt;
			for(int bitmask=0; bitmask<maxBitmask; bitmask++) {
				System.out.println("on Bitmask: " + bitmask);
				initStairs(bitmask);
				int endTime = Math.max(stairs[0].simulate(), stairs[1].simulate());
				if(endTime<minEndTime) minEndTime = endTime;
				
			}
			
			result.append(minEndTime).append("\n");
		}
		System.out.println(result);
	}
	
	static void initStairs(int bitmask) {
		stairs[0].init();
		stairs[1].init();
		
		for(int i=0; i<peopleCnt; i++) {
			// i번째 사람이 들어갈 계단 index
			int stairIdx = (bitmask & 1<<i) == 0 ? 0 : 1;
			stairs[stairIdx].selected.add(people[i]);
		}
		
		stairs[0].selected.sort((a,b) -> a.dist[0] - b.dist[0]);
		stairs[1].selected.sort((a,b) -> a.dist[1] - b.dist[1]);
	}
	
	
	static void initAndGetInput() {
		peopleCnt =0;
		people = new Person[10];
		stairs = new Stair[2];
		N = nextInt();
		map = new int[N][N];
		
		int stairIdx = 0;
		
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				int tmp = nextInt();
				if(tmp==1) people[peopleCnt++] = new Person(r, c);
				else if	(tmp > 1) {
					stairs[stairIdx] = new Stair(stairIdx, r, c, tmp);
					stairIdx++;
				}
			}
		}
		
		for(int i=0; i<peopleCnt; i++) {
			people[i].dist[0] = getManhattan(people[i].r, people[i].c, stairs[0].r, stairs[0].c);
			people[i].dist[1] = getManhattan(people[i].r, people[i].c, stairs[1].r, stairs[1].c);
		}
	}

	static int getManhattan(int x1, int y1, int x2, int y2) {
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
	static int nextInt() {
		try { st.nextToken(); }
		catch(IOException e) { e.printStackTrace(); }
		return (int) st.nval;
	}
}