package a250326.boj15683watch;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;


@FunctionalInterface
interface CCTV {
	void call(int r, int c);
}


public class Main {
	
	/*
	 * boj 15683 감시
	 * 
	 * 사각형의 맵이 주어지고
	 * cctv의 종류에 따라서 감시 가능 방향이 다르다.
	 * cctv의 각도를 최소 사각지대가 되도록 정해서
	 * 최소 사각지대를 출력.
	 * 
	 * 0: 아무것도 없음
	 * 1~5: cctv 종류
	 * 6: 벽.
	 * 
	 * cctv의 최대 갯수가 8개이니까.
	 * 최대의 경우의수는 4^8이다.
	 * 즉2^16
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static int Row, Col, sagak, minSagak, map[][];
	static int cctvCnt, cctvR[], cctvC[], cctvN[];
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	static List<List<Set<Point>>> watchedPoints;
	//CCTV 함수를 인덱스로 접근
	static List<CCTV> cctvFunc;
	
	public static void main(String[] args) throws IOException {
		init();
		
//		for(int i=0; i<cctvCnt; i++) {
//			System.out.print(i+ "th cctv" + cctvN[i] + ": ");
//			for(Set<Point> pointsAtDir : watchedPoints.get(i)) {
//				System.out.print(pointsAtDir.size() + ", ");
//			}
//			System.out.println();
//		}
		dfs(0, new HashSet<>());
		System.out.println(minSagak);
	}
	
	static void dfs(int depth, Set<Point> points) {
		if(depth==cctvCnt) {
			int sagakCount = sagak - points.size();
			if(sagakCount < minSagak) minSagak = sagakCount;
			return;
		}
		else {
			for(Set<Point> pointsOfCurrentCCTV : watchedPoints.get(depth)) {
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
        
        // 펑셔널 인터페이스 리스트에 각 CCTV 종류별 함수 추가.
        cctvFunc = new ArrayList<>();
        cctvFunc.add(Main::cc0);
        cctvFunc.add(Main::cc1);
        cctvFunc.add(Main::cc2);
        cctvFunc.add(Main::cc3);
        cctvFunc.add(Main::cc4);
        cctvFunc.add(Main::cc5);
        
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
        
        for(int r=1; r<=Row; r++) {
        	map[r][0] = map[r][Col+1] = 6;
        	for(int c=1; c<=Col; c++) {
        		int tmp = nextInt();
        		if(0<tmp) {
        			sagak--;
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
        watchedPoints = new ArrayList<>();
        
        for(int cid=0; cid<cctvCnt; cid++) {
        	cctvFunc.get(cctvN[cid]).call(cctvR[cid], cctvC[cid]);
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
	static void cc0(int a, int b) {}
	static void cc1(int r, int c) {
		List<Set<Point>> affectedPoints = new ArrayList<>();
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			affectedPoints.add(pointsAtDir);
		}
		watchedPoints.add(affectedPoints);
	}
	
	static void cc2(int r, int c) {
		List<Set<Point>> affectedPoints = new ArrayList<>();
		for(int dir=0; dir<2; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			tamsaek(r, c, dir+2, pointsAtDir);
			affectedPoints.add(pointsAtDir);
		}
		watchedPoints.add(affectedPoints);
	}
	
	static void cc3(int r, int c) {
		List<Set<Point>> affectedPoints = new ArrayList<>();
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			tamsaek(r, c, (dir+1)%4, pointsAtDir);
			affectedPoints.add(pointsAtDir);
		}
		watchedPoints.add(affectedPoints);
	}
	
	static void cc4(int r, int c) {
		List<Set<Point>> affectedPoints = new ArrayList<>();
		List<Set<Point>> tmpPoints = new ArrayList<>();
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			tamsaek(r, c, dir, pointsAtDir);
			tmpPoints.add(pointsAtDir);
		}
		for(int dir=0; dir<4; dir++) {
			Set<Point> pointsAtDir = new HashSet<>();
			for(int subDir=0; subDir<4; subDir++) {
				if(subDir != dir) {
					pointsAtDir.addAll(tmpPoints.get(subDir));
				}
			}
			affectedPoints.add(pointsAtDir);
		}
		watchedPoints.add(affectedPoints);
	}
	
	static void cc5(int r, int c) {
		List<Set<Point>> affectedPoints = new ArrayList<>();
		Set<Point> pointsAtDir = new HashSet<>();
		for(int dir=0; dir<4; dir++) {
			tamsaek(r, c, dir, pointsAtDir);
		}
		affectedPoints.add(pointsAtDir);
		watchedPoints.add(affectedPoints);
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}
