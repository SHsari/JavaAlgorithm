package a250305;

import java.util.Arrays;
import java.util.Scanner;

public class AdjMatrixTest {
	
	static int[][] adjMatrix;
	static int V;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		V = sc.nextInt();
		
		adjMatrix = new int[V][V];
		int E = sc.nextInt();
		
		for(int i=0; i < E; i++) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			adjMatrix[from][to] = 1;
			adjMatrix[to][from] = 1;
			//죽고싶다 시발
		}
		
		for( int[] am : adjMatrix ) {
			System.out.println(Arrays.toString(am));
		}
	}

}
