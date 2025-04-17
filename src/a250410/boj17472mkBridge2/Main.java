package a250410.boj17472mkBridge2;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Queue;


public class Main {
	/* boj 17472 다리만들기 2
	 * 
	 * 1. 전체 영역에 대한 bfs를 통해 섬 지역에 번호 붙이기
	 *  1-1 . 섬을 좌표들의 list로 가지고 있기 (LandList)
	 * 
	 * 2. 각 섬에 대해서 BFS를 통해 다른 섬에 도달할 수 있는 최소 거리를 구하기
	 * 	2-1. 간선 class로 저장
	 * 	
	 * 3. 간선배열을 이용해 kruskal 하기.
	 */
	

	// 좌표 class
	private static class Pair {
		int i, j; // i가 y 역방향값 j가 x
		
		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	//간선 class
	private static class GS implements Comparable<GS> {
		int dist, p1, p2;
		
		GS(int dist, int p1, int p2) {
			this.dist = dist;
			this.p1 = p1;
			this.p2 = p2;
		}

		@Override
		public int compareTo(GS other) {
			return this.dist -  other.dist;
		}
		
		@Override 
		public String toString() {
			return new String("dist: " + dist + "  p1: " + p1 + "  p2: " + p2);
		}
	}

	public static int[][] map;
	public static List<Pair> land;
	public static int n, m;
	
	public static List<List<Pair>> landList = new ArrayList<>();
	
	public static List<GS> ganseon = new ArrayList<>();
	public static int[] parent;
	public static int[] rank;
	
	public static int length=0;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][m]; //0으로 초기화
		land = new ArrayList<>();
		
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int j = 0;
			while(st.hasMoreTokens()) {
				int tmp = Integer.parseInt(st.nextToken());
				map[i][j] = tmp;
				if(tmp == 1) land.add(new Pair(i, j));
				j++;
			}
		}
		
		//printMap();
		
		//지도 입력 완료
		landList.add(new ArrayList<>());
		// 섬은1번부터 인덱스로 순회 가능
		// bound, LandList 도 0번 인덱스를 미리 채워넣기
		int landCount = bfsSetNum();
		
		//printMap();

		// 섬들을 연결할 수 있는 다리(최소길이)를 구하고 간선 배열에 저장
		for(int li=1; li<=landCount; li++) {
			List<Pair> lands = landList.get(li);
			//System.out.println("landList_num: " + li + "  size: " + lands.size());
			
			int[] gsMap = new int[landCount+1];
			for(Pair p : lands) {
				for(int d=0; d<4; d++) {
					boolean isValid = false;
					int ni = p.i + di[d];
					int nj = p.j + dj[d];
					int dist =0;
					
					while(ni>=0 && nj>=0 && ni<n && nj<m) {
						//System.out.println("ni, nj : " + ni + ", " + nj);
						if(map[ni][nj] != 0) { isValid = true;  break;}
						else {
							ni += di[d];
							nj += dj[d];
							dist ++;
						}
					}
					if(isValid && dist > 1) {
						int end = map[ni][nj];
						if(end>li && dist > 1 && (gsMap[end]==0 || gsMap[end] > dist)) {
							gsMap[end] = dist;
						}
					}
				}
			}
			
			for(int j=li+1; j<=landCount; j++ ) {
				if(gsMap[j]>1) {
					GS gs = new GS(gsMap[j], li, j);
					//System.out.println(gs);
					ganseon.add(gs);
				}
				
			}
		}
		
		//모든 최소연결간선 저장 완료.
		// ganseon[출발점][도착점] 하면 된다.
		ganseon.sort((e1, e2) -> { return e1.dist - e2.dist; });
		parent = new int[landCount+1];
		rank = new int[landCount+1];
		
		for(int i=0; i<=landCount; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
		
		int edgeCnt =0;
		for(GS ngs : ganseon) {
			int pp1 = findParent(ngs.p1);
			int pp2 = findParent(ngs.p2);
			// union하기.
			if(pp1 != pp2) {
				int nPP = (rank[pp1] > rank[pp2]) ? pp1 : pp2;
				rank[nPP] ++;
				parent[ngs.p1] = nPP;
				parent[ngs.p2] = nPP;
				length += ngs.dist;
				edgeCnt++;
				if(edgeCnt == landCount-1) {
					System.out.println(length);
					return;
				}
			}
			
		}
		System.out.println(-1);
		
	}

	/// 부모찾는 로직.
	private static int findParent(int i) {
		if(parent[i] == i) return i;
		else return findParent(parent[i]);
	}
	
	private static int[] dj = {0, 1, 0, -1};
	private static int[] di = {1, 0, -1, 0};
	
	private static int bfsSetNum() {
		boolean[][] visit = new boolean[n][m];
		
		int count = 0;
		for(Pair coor : land) {
			if(visit[coor.i][coor.j]) continue;
			
			int i=coor.i; int j = coor.j;
			Queue<Pair> qq = new ArrayDeque<>();
			List<Pair> ll = new ArrayList<>();
			qq.add(new Pair(i, j));
			ll.add(new Pair(i,j));
			visit[i][j] = true;
			count ++;
			map[i][j] = count;
			
			while(!qq.isEmpty()) {
				Pair now = qq.poll();
				for(int d=0; d<4; d++) {
					int ni = now.i+di[d];
					int nj = now.j+dj[d];
					if(ni>=0 && nj>=0 && ni<n && nj<m) {
						if(visit[ni][nj] || map[ni][nj] == 0) continue;
						else {
							Pair p = new Pair(ni, nj);
							qq.add(p);
							ll.add(p);
							visit[ni][nj] = true;
							map[ni][nj] = count;
						}
					}
				}
			}
			landList.add(ll);
		}
		return count;
	}

	
	private static void printMap() {
		for(int i=0; i<n; i++) {
			for(int j=0; j<m ; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
