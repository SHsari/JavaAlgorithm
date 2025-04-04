package a250327.boj15686chicken;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class BOJ_15686_치킨배달_송석현 {
	
	/* boj15686 치킨배달
	 * 
	 * 1. 맵을 입력받습니다.
	 * 	집과, 치킨집이 있는 경우 해당 좌표를 house, chicken에 추가합니다.
	 * 
	 * 2. house 배열과, chicken 배열을 기반으로 모든 집과 치킨집간 distance Matrix를 구합니다.
	 *  -> init 함수 내부에서 setDistMatrix(house, chicken)을 호출합니다.
	 *  
	 * 3. 전체 치킨집에서 M개의 치킨집을 고르고 
	 * 	-> 최초호출시 setCombination() 
	 *  -> 이후 호출시 nextCombination()
	 *  
	 * 4. 해당 상황에서 distanceMatrix를 순회하며 집마다의 최소 거리를 구합니다.
	 * 	-> 최소거리 합에 따라 최솟값 업데이트
	 * 
	 * -> 더 이상 새로운 조합이 없을 때 까지 3, 4 번을 반복합니다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static int N, M, houseCnt, chickenCnt;
	static int [][] matrix;
	static int[] selected;
	
	public static void main(String[] args) throws IOException {
		init(); // setDistMatrix 내부에서 호출
		setCombination();
		
		int minDist = Integer.MAX_VALUE;
		
		//루프 시작: 첫번째
		int dist = getChickenDist();
		if(dist<minDist) minDist = dist;
		
		// 두번째 루프부터.
		while(nextCombination()) {
			dist = getChickenDist();
			if(dist<minDist) minDist = dist;
		}
		
		System.out.println(minDist);
	}
	
	// 치킨집 조합을 바탕으로 모든 집에대한 집-> 치킨집 최소거리를 계산하는 함수.
	// 전체 집의 최소거리 합을 반환.
	static int getChickenDist() {		
		int distSum =0;
		
		for(int h=0; h<houseCnt; h++) {
			int min=Integer.MAX_VALUE;
			for(int i=0; i<M; i++) {
				if(matrix[h][selected[i]] < min) min = matrix[h][selected[i]];
			}
			distSum+=min;
		}
		return distSum;
	}
	
	
	static boolean nextCombination() {
		// selected배열을 확인하여 다음 조합을 만들어주는 함수.
		// 다음조합이 존재할 경우 true, 없으면 false를 반환.
		for(int i=1; i<=M; i++) {
			if(selected[i] - selected[i-1] > 1) {
				selected[i-1] ++;
				for(int j=0; j<i-1; j++) {
					selected[j] = j;
				}
				return true;
			}
		}
		return false;
	}
	
	// 최초의 치킨집 조합을 만들어 주는 함수.
	static void setCombination() {
		selected = new int[M+1];
		for(int i=0; i<M; i++) {
			selected[i] = i;
			//[0, 1, 2, 3, ... , M-1]
		}
		selected[M] = chickenCnt;
	}
	
	// 2번 distanceMatrix 제작 함수.
	static void setDistMatrix(Point[] house, Point[] chicken) {
		matrix = new int[houseCnt][chickenCnt];
		for(int h=0; h<houseCnt; h++) {
			for(int c=0; c<chickenCnt; c++) {
				matrix[h][c] = getMDist(house[h], chicken[c]);
			}
		}
	}

	// 1번 입력 합수
	// 2번 distanceMatrix 제작.
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        M = nextInt();
        
        //집과 치킨집의 총 갯수를 세고
        houseCnt=0; chickenCnt=0;
        
        // 집과 치킨집의 좌표를 저장할 배열
        Point[] house = new Point[2*N];
        Point[] chicken = new Point[13];
        
        for(int r=0; r<N; r++) {
        	for(int c=0; c<N; c++) {
        		// 집과 치킨집을 발견할 경우 해당 좌표를 배열에 추가.
        		int info = nextInt();
        		if(info==1) house[houseCnt++] = new Point(r,c);
        		else if(info==2) chicken[chickenCnt++] = new Point(r,c);
        	}
        }
        
        // 만들어진 좌표 배열을 이용해 distanceMatrix 셋팅.
        setDistMatrix(house, chicken);
	}
	
	// 두 좌표간 manhattanDistance를 계산하는 함수
	static int getMDist(Point p1, Point p2) {
		return Math.abs(p1.x-p2.x) + Math.abs(p1.y-p2.y);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}