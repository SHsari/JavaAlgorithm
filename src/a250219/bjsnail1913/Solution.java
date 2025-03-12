package a250219.bjsnail1913;

import java.util.Scanner;

public class Solution {
	static StringBuilder sb = new StringBuilder();
	static int[][] map;
	static int[] bhx = {1, 0, -1, 0}; //x방향
	static int[] bhy = {0, 1, 0, -1}; //y방향
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc=1; tc<=T; tc++) {
			sb.append("#").append(tc).append("\n");
			int n = sc.nextInt();
			solve(n);
			
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					sb.append(map[i][j]).append(" ");
				}
				sb.append("\n");
			}
			
		}
		System.out.println(sb);
		sc.close();
	}
	
	static void solve(int n) { 
		map = new int[n][n];
		int num =1;
		int nx =-1, ny=-1;
		
		for(int sqlen=n; sqlen>=1; sqlen-=2) {
			int startNum = num;
			map[++ny][++nx] = num++;
			for(int dir=0; dir<4; dir++) {
				int dy = bhy[dir];
				int dx = bhx[dir];
				
				for(int l=1; l<sqlen; l++) {
					ny += dy;
					nx += dx; 
					map[ny][nx] = num++;
				}
			}
			map[nx][ny] = startNum;
			num--;
		}
		
	}
	
}
