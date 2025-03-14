package personal.boj2212sensor;

import java.io.*;
import java.util.Arrays;

/* 백준 2212번 센서
 * 
 * 직선위의 센서들이 띄엄띄엄 존재합니다요
 * K개의 집중국을 설치하는데, 수신 가능 영역은 자유롭게 설정할 수 있지만
 * 모든 센서를 커버하고
 * 각 집중국의 영역 거리의 합이 최소가 되도록 하려고 합니다.
 * 
 * K개 집중국 -> 집중국 사이의 빈공간: K-1개
 * 빈공간의 합이 최대가 되려면
 * 
 * 센서와 센서 사이의 거리 N-1개 중, 
 * 가장 거리가 긴 K-1개를 고르면 됩니다데스
 * 
 */

public class Main {
	static BufferedReader br;
	static StreamTokenizer st;
	static int[] intervals;
	static int[] xloc;
	static int NS, K;

	public static void main(String[] args) throws IOException {
		System.out.println(init());
	}
	
	static int init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StreamTokenizer(br);
		NS = nextInt();
		K = nextInt();
		
		if(NS<=K) return 0;
		
		xloc = new int[NS];
		intervals = new int[NS-1];
		
		for(int i=0; i<NS; i++) {
			xloc[i] = nextInt();
		}
		
		Arrays.sort(xloc);
		for(int i=1; i<NS; i++) {
			intervals[i-1] = xloc[i] - xloc[i-1];
		}
		
		Arrays.sort(intervals);
		int kSum =0;
		for(int i=NS-2; i>NS-1-K; i--) {
			kSum+=intervals[i];
		}
		return (xloc[NS-1] - kSum - xloc[0]);
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}
