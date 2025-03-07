package a250220.bj15650NM2;

/*
 * baekJoon n과m 2
 * 
 */

import java.util.Scanner;



public class Main {
	static int N, M;
	static int[] list;
	static StringBuilder sb;
	
	
	//조합함수.
	static void combination(int start, int depth) {
		if(depth==M) {
			for(int a : list) { sb.append(a + " "); }
			sb.append("\n");
			return;
		}
		for(int i=start; i<=(N-M+depth+1); i++) {
			list[depth] = i;
			combination(i+1, depth+1);
		}
		
	}
	
	public static void main(String[] args) {
		sb = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		list = new int[M];
		
		combination(1, 0);
		
		System.out.println(sb);
	}
}
