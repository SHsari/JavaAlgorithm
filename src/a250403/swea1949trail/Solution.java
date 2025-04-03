package a250403.swea1949trail;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	/* SWEA 1949 mk등산로
	 * 2차원 map에서 최장 감소 수열 찾기.
	 * - 가장 높은 곳에서 출발.
	 * - 특수조건: map 중 한 곳을 K이하만큼 높이를 낮출 수 있다.
	 * 
	 * 지도의 한 변의 길이 N (3 ≤ N ≤ 8)
	 * 최대 공사 가능 깊이 K (1 ≤ K ≤ 5)
	 * 지형의 높이 1 이상 20 이하의 정수
	 * 지도에서 가장 높은 봉우리는 최대 5개이다.
	 * 필요한 경우 지형을 깎아 높이를 1보다 작게 만드는 것도 가능하다
	 * 
	 * 최대 map area  64
	 * 모든 좌표마다 공사를 해보는 bruteForce의 경우
	 * 
	 * N^2 * K
	 * 
	 * 최대 봉우리에서 가능 모든 경우 DFS..
	 * 
	 * <= 102400 ~~ 10^5??
	 * 
	 * 
	 * 일단 맵의 크기가 충분히 작고.
	 * 숫자의 범위도 작으므로 그냥 완탐 가봅니다.
	 */
	
	static int N, K, map[][], maxHeight;
	static int maxLength;
	static List<Point> maxPoints;
	static int[] dRow = {0,1,0,-1};
	static int[] dCol = {1,0,-1,0};
	static List<Point> maxPath;


	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			// 맵이 변형 가능하므로
			// 모든 변형된 경우의 수에 대한 완전탐색 for문
			
			// 굴착을 하지 않은 상태에서 진행
			for(Point st : maxPoints) {
				maxPath = new ArrayList<>();
				dfs(st.x, st.y, 1);
			}
			
			// 좌표별로 굴착 후 진행
			for(int r=1; r<=N; r++) {
				for(int c=1; c<=N; c++) {
					// 현재 좌표에서 굴착기 개설
					Excavator exc = new Excavator(r, c);
					// 추가 굴착이 가능하다면.
					while(exc.mapIterate()) {
						// 새로운 굴착 높이마다 maxPoints에서 dfs.
						for(Point st : maxPoints) {
							maxPath = new ArrayList<>();
							// 굴착한 곳이 아닌 경우에만.
							if(st.x==r && st.y==c) continue;
							else  {								
								dfs(st.x, st.y, 1);
							}
						}
					}
				}
			}
			result.append(maxLength).append("\n");
		}
		System.out.println(result);
	}
	
	// 경로 dfs
	static void dfs(int r, int c, int count) {
		maxPath.add(new Point(r,c));
		int ogHeight = map[r][c];
		map[r][c] = 100;
		int didGoFurther = 0;
		// 4방탐색.
		for(int dir=0; dir<4; dir++) {
			int adjRow = dRow[dir] + r;
			int adjCol = dCol[dir] + c;
			int adjHeight = map[adjRow][adjCol];
			if(adjHeight < ogHeight) {
				didGoFurther ++;
				dfs(adjRow, adjCol, count+1);
			}
		}
		// 이 지점이 마지막 확장 지점인 경우 maxLength와 비교하여 업데이트 합니다.
		if(didGoFurther==0 && maxLength<count) maxLength = count;
		map[r][c] = ogHeight;
	}
	
	static class Excavator {
		/*
		 * 굴착기 클래스 입니다
		 * 완전탐색을 피하기 위해
		 * 유효한 굴착 깊이에 대해서 굴착을 진행합니다. 
		 */
		int r, c;
		int ogHeight;
		int[] adjs;
		int adjCnt, adjIdx;
		
		Excavator(int r, int c) {
			this.r = r; this.c = c;
			this.ogHeight = map[r][c];
			adjIdx =0;
			adjCnt=0;
			adjs = new int[4];
			for(int dir=0; dir<4; dir++) {
				int adjRow = r + dRow[dir];
				int adjCol = c + dCol[dir];
				int adjHeight = map[adjRow][adjCol];
				if(adjHeight <= ogHeight) {
					if ((ogHeight - adjHeight) < K) { 
						adjs[adjCnt++] = adjHeight;
					}
				}
			}
		}
		
		// 유효 굴착 높이(dfs 시에 변화를 일이키는 높이)를 확인하여
		// 추가굴착을 진행합니다. 유효 굴착 높이가 없을 경우 false를 반환합니다.
		public boolean mapIterate() {
			if(adjIdx == adjCnt)  {
				map[r][c] = ogHeight;
				return false;
			}
			else {
				map[r][c] = adjs[adjIdx++] -1;
				return true;
			}
		}
	}
	
	static void init() throws IOException {
		N = nextInt();
		K = nextInt();
		map = new int[N+2][N+2];
		Arrays.fill(map[0], 100);
		Arrays.fill(map[N+1], 100);
		maxLength = 0;
		maxHeight = 0;
		for(int r=1; r<=N; r++) {
			map[r][0] = map[r][N+1] = 100;
			for(int c=1; c<=N; c++) {
				int tmp = nextInt();
				map[r][c] = tmp;
				if(maxHeight < tmp) maxHeight = tmp;
			}
		}
		
		maxPoints = new ArrayList<>();
		for(int r=1; r<=N; r++) {
			for(int c=1; c<=N; c++) {
				if(map[r][c] == maxHeight) {
					maxPoints.add(new Point(r,c));
				}
			}
		}
		
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}