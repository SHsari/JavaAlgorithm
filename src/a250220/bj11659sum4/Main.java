package a250220.bj11659sum4;
import java.io.*;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static int N, M;
	static long[] iList;
	public static void main(String[] args) throws IOException {
		// 입력  수열의 길이 N과
		// 테스크의 수 M 입력
		StringTokenizer nm = new StringTokenizer(br.readLine());
		N = Integer.parseInt(nm.nextToken());
		M = Integer.parseInt(nm.nextToken());
		iList = new long[N+1];
		StringTokenizer list = new StringTokenizer(br.readLine());
		// 배열 iList에 입력.
		iList[0] = 0;
		for(int i=1; i<=N; i++) {
			iList[i] = iList[i-1]+Integer.parseInt(list.nextToken());
		}
		
		for(int i=0; i<M; i++) {
			getSumIJ();
		}
		
		System.out.println(sb);
		
	}
	
	static void getSumIJ() throws IOException {
		StringTokenizer ij = new StringTokenizer(br.readLine());
		
		int start = Integer.parseInt(ij.nextToken());
		int end = Integer.parseInt(ij.nextToken());
		long sum = iList[end] - iList[start-1];
		
		sb.append(sum + "\n");		
	}

}
