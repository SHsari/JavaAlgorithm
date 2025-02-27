package a250221.bj2961DeliciousFood;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
	
	static int N;
	static int[] sour;
	static int[] bitter;
	
	static int minDiff;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {

		N = Integer.parseInt(br.readLine());
		sour = new int[N];
		bitter = new int[N];
		minDiff = Integer.MAX_VALUE;
		
		for(int i=0; i<N; i++) {
			StringTokenizer line = new StringTokenizer(br.readLine());
			sour[i] = Integer.parseInt(line.nextToken());
			bitter[i] = Integer.parseInt(line.nextToken());
		}
		combination(0, 1, 0);
		System.out.println(minDiff);
	}	
	
	static void combination(int start, int curSour, int curBitter) {
		if(start==N) {
			return ;
		}
		
		for(int idx = start; idx<N; idx ++) {
			curSour*=sour[idx];
			curBitter+=bitter[idx];
			int result = Math.abs(curSour - curBitter);
			
			if(minDiff>result) {
				minDiff = result;
			}
			
			combination(idx+1, curSour, curBitter);
			curSour/=sour[idx];
			curBitter-=bitter[idx];
			
		}
	}
}
