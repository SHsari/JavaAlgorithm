package a250307.swea2382microbeIsolation;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/*
 * SWEA2382 미생물 격리
 * 
 * N*N 의 맵
 * 군집의 좌표, 군집 내 미생물 수, 이동방향
 * 매시간 방향으로  이동
 * 끝에 닿았을 경우 군집 내 미생물 수 절반 감소 (홀수일 경우 버리면 된다)
 * 
 * 두개 이상의 군집이 한 위치에 모이면 합쳐짐.
 * 이동방향의 경우 가장 큰 수의 군집것을 유지.
 * (군집 크기가 동일한 경우는 없다)
 * 
 * M시간동안 미생물 군집 격리. M시간 후 남아있은 미생물 수 총합.
 * 
 * 세로위치(1), 가로위치(1), 미생물 수(7), 이동방향(상)
 * 1 2 3 4
 * 상, 하, 좌, 우,
 */

public class SWEA_2382_미생물격리_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	static int[] dRow = {-1, 1, 0, 0};
	static int[] dCol = {0, 0, -1, 1};

	// 한 변의 길이 N, 격리시간 M, 군집갯수 K
	// 다 쓰고 보니까 군집은 Cluster인데,
	// 군집갯수와도 라임이 맞으니 Kluster라고 하겠습니다...
	static int N, M, K;
	static int microbeCount;
	static int[][] standardMap;
	static Map<Integer, Microbe> klusters;
	static int startKey =100;

	static final int BOUND = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");

			//입력 및 초기화함수
			init();


			for(int hour=0; hour<M; hour++) {
				iterate();
			}

			int countSum =0 ;
			for(Microbe kluster: klusters.values()) {
				countSum += kluster.count;
			}
			result.append(countSum).append("\n");
		}
		System.out.println(result);
	}

	static void init() throws IOException {

		N = nextInt();
		M = nextInt();
		K = nextInt();

		standardMap = new int[N][N];
		Arrays.fill(standardMap[0], BOUND);
		Arrays.fill(standardMap[N-1], BOUND);
		for(int i=1; i<N-1; i++) {
			standardMap[i][0] = BOUND;
			standardMap[i][N-1] = BOUND;
		}

		klusters = new HashMap<>();
		for(int key=startKey; key<K+startKey; key++) {
			Microbe mk = new Microbe();
			mk.row = nextInt();
			mk.col = nextInt();
			mk.count = nextInt();
			mk.dir = nextInt()-1;
			klusters.put(key, mk);
		}
	}
	
	// 한시간 후의 상태로 iterate
	static void iterate() {
		//System.out.println("========Iteration Start========");
		List<Integer> deleteList = new ArrayList<>();
		Map<Point, List<Integer>> collision = new HashMap<>();

		/*
		 * 와싀.. .clone() 함수를 이차원 배열에 쓰면
		 * 얕은 복사가 된다고 합니다.ㅠㅠㅠㅠ 요거땜시 난리였네
		 * 
		 * 2차원 배열 순회를 피하기 위해서 여러~가지를 했지만
		 * 오히려 시간이 더 드는 것 같습니다.
		 */
		int[][] newStatus = new int[N][];
		for(int r=0; r<N; r++) {
			newStatus[r] = standardMap[r].clone();
		}

		for(Integer key : klusters.keySet()) {
			
			Microbe mk = klusters.get(key);

			mk.row += dRow[mk.dir];
			mk.col += dCol[mk.dir];
			//System.out.println(mk);

			int pointStatus = newStatus[mk.row][mk.col];

			// 격리벽에 부딫히는 경우
			if(pointStatus == BOUND) {
				//일단 다른 klsuter와 충돌할 수 없다.
				//방향을 반대로 바꾸고 개체수를 반으로 줄인다.
				if(mk.dir%2==1) { mk.dir--; } 
				else { mk.dir++; }
				mk.count /= 2;
				// 군집이 사라졌다면 추후 삭제를 위해 리스트에 넣는다.
				if(mk.count == 0) deleteList.add(key);
				//System.out.println("reached Bound, count: " + mk.count);
			}

			// 이미 다른 객체가 옮겨져 있는 경우
			// == 충돌 발생의 경우
			else if(pointStatus >= startKey) {
				Point collidePoint = new Point(mk.row, mk.col);
				// 발생했는데, 기록이 안되어있는 경우
				if(!collision.containsKey(collidePoint)) {
					int firstArrival = pointStatus;
					List<Integer> collideList = new ArrayList<>();
					collideList.add(firstArrival);
					collision.put(collidePoint, collideList);
				}
				// 이후 자기 자신도 충돌현황에 집어넣는다.
				collision.get(collidePoint).add(key);
				
			}
			// 빈 공간일 경우
			else {
			// 오직 충돌을 위한 표시....
			newStatus[mk.row][mk.col] = key;
			}	

			// 충돌 맵은 HASH MAP
			// 현황판(맵)은 2차원 integer 배열
		}
		// 모든 클러스터를 확인하며 1 iteration을 진행한 후
		// 충돌처리, 삭제 등의 마무리 작업을 해준다.

		// 먼저 충돌처리
		//collision Point를 순회하면서 충돌한 kluster들을 확인하고 합칩니다.
		for(Point point : collision.keySet()) {
			//System.out.println(point.toString());
			int maxCount =0;
			int countSum = 0;
			int maxDir = -1;
			List<Integer> colliders = collision.get(point);
			for(Integer colliderKey : colliders) {
				Microbe collider = klusters.get(colliderKey);
				countSum += collider.count;
				if(maxCount<collider.count) {
					maxCount = collider.count;
					maxDir = collider.dir;
				}
				klusters.remove(colliderKey);
			}

			Microbe combined = new Microbe(point.x, point.y, countSum, maxDir);
			Integer combinedKey = newStatus[point.x][point.y];
			klusters.put(combinedKey, combined);
			//System.out.println("collided, count: " + countSum);
		}

		// 둘째 삭제처리
		for(Integer zeroKlusterKey : deleteList) {
			klusters.remove(zeroKlusterKey);
		}

	//	System.out.println("========Iteration end========");
	}


	// 미생물 군집 클래스
	static class Microbe {
		int count, dir, row, col;
		Microbe() {}
		Microbe(int row, int col, int count, int dir) {
			this.row = row;
			this.col = col;
			this.count = count;
			this.dir = dir;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("count: ").append(count);
			sb.append("\nrc: ").append(row).append(", ").append(col);
			sb.append("\ndir: ").append(dir).append("\n");
			return sb.toString();
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}