package liveClass;

import java.util.Arrays;

/*
 * 추후에 랭크관리 코드도 짜보자
 * 
 */
public class DisjointSet {
	static int N;
	static int[] parents;
	static int[] rank;
	
	static void init() {
		parents = new int[N];
		for(int i=0; i<N; i++) {
			parents[i] = i;
		}
		rank = new int[N];
		Arrays.fill(rank, 0);
	}
	
	static int find(int a) {
		if(a==parents[a]) return a;
		return parents[a] = find(parents[a]); // 경로압축
	}
	
	static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		if(aRoot == bRoot) return false;
		else {
			if(rank[aRoot] > rank[bRoot]) parents[bRoot] = aRoot;
			else if(rank[aRoot] < rank[bRoot]) parents[aRoot] = bRoot;
			else {
				parents[aRoot] = bRoot;
				rank[bRoot] ++;
			}
			return true;
		}
	}
}
