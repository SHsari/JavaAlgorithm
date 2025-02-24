package a250221.bj2961DeliciousFood;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
	
	static int N;
	static int[] sour;
	static int[] bitter;
	
	static int sourMul;
	static int bitterSum;
	static int minDiff;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {

		N = Integer.parseInt(br.readLine());
		sour = new int[N];
		bitter = new int[N];
		sourMul =1;
		bitterSum=0;
		minDiff = Integer.MAX_VALUE;
		
		for(int i=0; i<N; i++) {
			StringTokenizer line = new StringTokenizer(br.readLine());
			sour[i] = Integer.parseInt(line.nextToken());
			bitter[i] = Integer.parseInt(line.nextToken());
		}
		combination(0);
		System.out.println(minDiff);
	}	
	
	static void combination(int start) {
		if(start==N) {
			return ;
		}
		
		for(int idx = start; idx<N; idx ++) {
			sourMul*=sour[idx];
			bitterSum+=bitter[idx];
			int result = Math.abs(sourMul-bitterSum);
			
			if(minDiff>result) {
				minDiff = result;
				System.out.println(result);
			}
			
			combination(start+1);
			
			sourMul /= sour[idx];
			bitterSum -= bitter[idx];
		}
	}
}
