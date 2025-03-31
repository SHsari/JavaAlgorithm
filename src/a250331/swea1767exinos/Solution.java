package a250331.swea1767exinos;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import java.util.List;


public class Solution {
	/*
	 * SWEA 1767 어쩌구
	 * 
	 * 맵사이즈 7<= N <= 12
	 * 코어 1 <= core <= 12
	 * 
	 * 코어의 4방으로 연결이 가능하기 때문에
	 * 모든 경우의 수는
	 * 최대 4^12 대략 10^8 정도이다.
	 * 
	 * -> 가능.
	 * 
	 * + 프루닝이 자동으로 아주 많이 일어날 것이므로 절반도 안되지 않을까.
	 * 생각혔는디 ..?
	 * 
	 * 
	 * Point의 Set을 적극적으로 사용했습니다.
	 * 이미 도선이 놓인 경로에 대해서
	 * Set<Point> pathSum 으로 관리했습니다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, coreNum;
	static int[][] map;
	static Point[] cores;
	
	static int maxConnected, minLength;
	static Set<Point> pathSum;
	
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	
	public static void main(String[] args) {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			
			dfs(0, 0);
			
			result.append(minLength).append("\n");
		}
		System.out.println(result);
	}
	
	static void dfs(int coreIdx, int connectedCore) {
		if(coreIdx == coreNum) {
			if(connectedCore>maxConnected) {
				maxConnected = connectedCore;
				minLength = pathSum.size();
			} else if(connectedCore==maxConnected && minLength>pathSum.size()) {
				minLength = pathSum.size();
			}
			return;
		}
		
		int maxEstimated = connectedCore + (coreNum - coreIdx);
		
		for(int dir=0; dir<4; dir++) {
			List<Point> path = getPath(cores[coreIdx], dir);
			
			if(path != null) {
				pathSum.addAll(path);
				dfs(coreIdx+1, connectedCore+1);
				pathSum.removeAll(path);
			} 
			// 현재 코어를 사용 할수 없음이 결정되었기 때문에 maxEstimated-1 해준다.
			else if(maxConnected <= maxEstimated-1) {
				dfs(coreIdx+1, connectedCore);
			}
		}
	}
	
	static List<Point> getPath(Point p, int dir) {
		int dr = dRow[dir];
		int dc = dCol[dir];
		
		int r = p.x + dr;
		int c = p.y + dc;
		
		List<Point> path = new ArrayList<>();
		while(map[r][c] == 0) {
			Point cur = new Point(r,c);
			if(pathSum.contains(cur)) {
				return null;
			} else {
				path.add(cur);
				r+=dr; c+=dc;
			}
		}
		if(map[r][c]==-1) {
			return path;
		} else return null;
	}
	
	static void init() {
		pathSum = new HashSet<>();
		maxConnected = 0;
		minLength = Integer.MAX_VALUE;

		N = nextInt();
		
		map = new int[N+2][N+2];
		Arrays.fill(map[0], -1);
		Arrays.fill(map[N+1], -1);
		cores = new Point[12];
		coreNum =0;
		
		for(int r=1; r<=N; r++) {
			map[r][0] = map[r][N+1] = -1;
			for(int c=1; c<=N; c++) {
				int tmp = nextInt();
				map[r][c] = tmp;
				if(tmp == 1) {
					cores[coreNum++] = new Point(r, c);
				}
			}
		}
	}
	
	static int nextInt() {
		try {
			st.nextToken();
			return (int) st.nval;
		}
		catch(IOException e) { e.printStackTrace();}
		return (int) st.nval;
	}
}