package a250326.boj15683watch;

import java.awt.Point;
import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/*
 * boj 15683 감시
 * 
 * 사각형의 맵이 주어지고
 * cctv의 종류에 따라서 감시 가능 방향이 다르다.
 * cctv 각각의 각도를 사각지대가 최소가 되도록 정해서
 * 최소 사각지대의 수를 출력
 * 
 * map 해석
 * 0: 아무것도 없음
 * 1~5: cctv설치됨. 숫자는 종류를 나타냄
 * 6: 벽.
 * 
 * cctv의 최대 갯수가 8개이니까.
 * 최대의 경우의수는 4^8이다.
 * 즉2^16
 */

public class Main {
	/* 풀이방식
	 * 
	 * 1. 맵입력 및 CCTV 번호와 위치 저장.
	 * 2. CCTV를 순회하며 설치 방향별로 감시 가능한 좌표를 Set의 배열로 만들어 저장
	 * 3. 위의 과정을 통해 Set<Point> 2차원 배열이 만들어지면
	 * 	리스트의 인덱스 순서대로 탐색한다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static int Row, Col, sagak, minSagak, map[][];
	static int cctvCnt, cctvR[], cctvC[], cctvN[];
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	static Set<Point>[][] watchedPoints;
	
	public static void main(String[] args) throws IOException {
		init();
		dfs(0, new HashSet<>());
		System.out.println(minSagak);
	}
	
	/* 첫번째 실수 뭐였더라
	 * 
	 * 
	 */
	static void dfs(int depth, Set<Point> points) {
		if(depth==cctvCnt) {
			int sagakCount = sagak - points.size();
			if(sagakCount < minSagak) minSagak = sagakCount;
			return;
		}
		else {
			for(Set<Point> pointsOfCurrentCCTV : watchedPoints[depth]) {
				Set<Point> nextPoints = new HashSet<>();
				nextPoints.addAll(pointsOfCurrentCCTV);
				nextPoints.addAll(points);
				dfs(depth+1, nextPoints);
			}
		}
	}

	
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        minSagak=Integer.MAX_VALUE;
        cctvCnt =0;
        cctvR = new int[8];
        cctvC = new int[8];
        cctvN = new int[8];
        Row = nextInt();
        Col = nextInt();
        // 사각지대 갯수
        sagak = Row*Col;
        map = new int[Row+2][Col+2];
        Arrays.fill(map[0], 6);
        Arrays.fill(map[Row+1], 6);
        
        // 맵을 그대로 저장합니다.
        for(int r=1; r<=Row; r++) {
        	map[r][0] = map[r][Col+1] = 6;
        	for(int c=1; c<=Col; c++) {
        		int tmp = nextInt();
        		if(0<tmp) {
        			// 전체 지대에서 cctv와 벽의 갯수를 뺍니다.
        			sagak--;
        			// 아래는 해당 좌표에 cctv가 있는 경우
        			if(tmp!=6) {
        				cctvN[cctvCnt] = tmp;
        				cctvR[cctvCnt] = r;
        				cctvC[cctvCnt] = c;
        				cctvCnt++;
        			}
        			map[r][c] = tmp;
        		}
        	}
        }
        watchedPoints = new HashSet[cctvCnt][];
        
        for(int cid=0; cid<cctvCnt; cid++) {
        	int r = cctvR[cid], c = cctvC[cid];
        	switch(cctvN[cid]) {
        	case 1: cc1(cid, c, r);
        	case 2: cc2(cid, c, r);
        	case 3: cc3(cid, c, r);
        	case 4: cc4(cid, c, r);
        	case 5: cc5(cid, c, r);
        	}
        }
	}
	
	
	/* tamSaek 함수.
	 * 시작점으로부터 주어진 한 방향으로 맵을 탐색하면서
	 * 해당 방향에서 감시 가능한 점을 주어진 Set<Point>에 추가합니다.
	 */
	static void tamsaek(int r, int c, int dir, Set<Point> pointsAtDir) {
		int dr = dRow[dir];
		int dc = dCol[dir];
		int curR = r + dr;
		int curC = c + dc;
		while(map[curR][curC] != 6) {
			if(map[curR][curC] == 0) {
				pointsAtDir.add(new Point(curR, curC));
			}
			curR+=dr; curC+=dc;
		}
	}

	// 아래는 tamsaek 함수를 활용한
	// 각 CCTV의 방향별 감시가능한 점을 찾아 저장해놓는 함수입니다.
	static void cc1(int cctvId, int r, int c) {
		Set<Point>[] affectedPoints = new HashSet[4];
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			affectedPoints[dir] = pointsAtDir;
		}
		watchedPoints[cctvId] = affectedPoints;
	}
	
	static void cc2(int cctvId, int r, int c) {
		Set<Point>[] affectedPoints = new HashSet[2];
		for(int dir=0; dir<2; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			tamsaek(r, c, dir+2, pointsAtDir);
			affectedPoints[dir] = pointsAtDir;
		}
		watchedPoints[cctvId] =  affectedPoints;
	}
	
	static void cc3(int cctvId, int r, int c) {
		Set<Point>[] affectedPoints = new HashSet[4];
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			tamsaek(r, c, (dir+1)%4, pointsAtDir);
			affectedPoints[dir] = pointsAtDir;
		}
		watchedPoints[cctvId] = affectedPoints;
	}
	
	static void cc4(int cctvId, int r, int c) {
		Set<Point>[] affectedPoints = new HashSet[4];
		Set<Point>[] tmpPoints = new HashSet[4];
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			tmpPoints[dir] = pointsAtDir;
		}
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			for(int subDir=0; subDir<4; subDir++) {
				if(subDir != dir) {
					pointsAtDir.addAll(tmpPoints[subDir]);
				}
			}
			affectedPoints[dir] = pointsAtDir;
		}
		watchedPoints[cctvId] = affectedPoints;
	}
	
	static void cc5(int cctvId, int r, int c) {
		Set<Point>[] affectedPoints = new HashSet[1];
		Set<Point> pointsAtDir = new HashSet<>();
		for(int dir=0; dir<4; dir++) {
			tamsaek(r, c, dir, pointsAtDir);
		}
		affectedPoints[0] = pointsAtDir;
		watchedPoints[cctvId] = affectedPoints;
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}