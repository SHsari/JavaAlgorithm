package a250402.swea2117boan;


import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * SWEA 2117 방범서비스
 * 
 * 마름모꼴 지역에 대한 방범서비스 제공 업체
 * 서비스 제공 범위 N: 중심으로부터 마름모 꼭짓점까지의 거리
 * N에 대한 서비스 제공 비용: 2N(N-1)+1
 * 
 * 서비스 제공 범위에 속한 집 하나에서 낼 수 있는 비용 M
 * 
 * 서비스 제공 업체가 손해보지 않고 서비스 할 수 있는 최대 집의 수
 * 
 * 맵 한변의 길이 N (5 ≤ N ≤ 20)
 * 집 하나당 회수비용 M (1 ≤ M ≤ 10)
 *
 * 1. 맵을 입력받을 때, 모든 집의 좌표를 저장합니다.
 * 
 * 2. 모든 맵의 좌표를 순회합니다.
 *		2-1. 각 좌표에서 모든 집까지의 거리를 구하고 오름차순으로 정렬합니다.
 * 		2-2. 정렬된 Mdist를 순회하며 서비스 가능한지 지출과 수익을 비교합니다.
 * 			-> 서비스 가능한 최대 집의 수를 반환합니다.
 *
 */

public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, Money;
	static int[][] map;
	static int houseCnt;
	static List<Point> houses;
	
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			int maxHouse=0;
			for(int r=1; r<=N; r++) {
				for(int c=1; c<=N; c++) {
					int localMax = findMaxHouseAt(r, c);
					if(maxHouse<localMax) maxHouse = localMax;
				}
			}
			
			result.append(maxHouse).append("\n");
		}
		
		System.out.println(result);
	}
	
	
	// 특정 좌표로부터 서비스 가능한 최대 집의 수를 반환하는 함수.
	static int findMaxHouseAt(int r, int c) {
		// 현 좌표와 다른 집들 사이의 거리를 저장할 배열
		int[] distances = new int[houseCnt];
		
		// 집 좌표들을 순회하면서 거리를 저장
		int index=0;
		for(Point house : houses) {
			int dist = getManhattanDist(r, c, house);
			distances[index++] = dist;
		}
		
		// 거리를 오름차순으로 정렬
		Arrays.sort(distances);
		
		// 거리 배열을 순회하며 서비스 가능한 집의 수를 구함.
		int maxHouse=0;
		int houseIdx =0;
		while(houseIdx<houseCnt) {
			if(houseIdx+1<houseCnt && distances[houseIdx] == distances[houseIdx+1]) {
				houseIdx++;
			}
			// 해당 집의 거리까지 서비스하기 위한 비용을 구함
			int expanse = getExpanseOf(distances[houseIdx]);
			// 해당 집까지 서비스했을 때 얻을 수 있는 수익과 비용을 비교
			if(expanse <= houseIdx*Money + Money) { maxHouse=houseIdx+1; }
			houseIdx++;
		}
		return maxHouse;
	}
	
	static int getExpanseOf(int dist) {
		return 2*dist*(dist-1) +1;
	}
	
	static int getManhattanDist(int r1, int c1, Point house) {
		return Math.abs(r1-house.x) + Math.abs(c1-house.y)+1;
	}
	
	
	static void init() throws IOException {
		N = nextInt();
		Money = nextInt();
		houses = new ArrayList<>();
		map = new int[N+2][N+2];
		Arrays.fill(map[0], -1);
		Arrays.fill(map[N+1], -1);
		houseCnt =0;
		for(int r=1; r<=N; r++) {
			map[r][0] = map[r][N+1] = -1;
			for(int c=1; c<=N; c++) {
				int tmp = nextInt();
				map[r][c] = tmp;
				if(tmp==1) {
					houseCnt++;
					houses.add(new Point(r,c));
				}
			}
		}
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}