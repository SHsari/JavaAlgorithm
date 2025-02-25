package a250225.bj10972NextPerm;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	static int[] array;
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		

		
		StringTokenizer line = new StringTokenizer(br.readLine());
		array = new int[N];
		
		for(int i=0; i<N; i++) {
			array[i] = Integer.parseInt(line.nextToken());
		}
		
		if(N<= 1) {
			System.out.println("-1");
			return;
		}
		int i = N-1;
		
		// 특정 인덱스 이하의 숫자 정렬이 최대가 되는 (내림차순)
		// 즉, 내림차순이 깨지는 index 발견하기
		while(array[i] <= array[i-1] ) { 
			if(--i <= 0) {
				System.out.println("-1");
				return;
			}
		}
		
		// i-1위치와 교환할 숫자 찾기.
		int pole = array[i-1];
		int j = N-1;
		while(array[j]<=pole) j--;
		swap(i-1, j);
		
		int k=N-1;
		while(i < k) swap(i++, k--);
		
		StringBuilder result = new StringBuilder();
		
		for(int idx=0; idx<N; idx++) {
			result.append(array[idx]).append(" ");
		}
		System.out.println(result);
	}
	
	static void swap(int idx1, int idx2) {
		int tmp = array[idx1];
		array[idx1] = array[idx2];
		array[idx2] = tmp;
	}
}
