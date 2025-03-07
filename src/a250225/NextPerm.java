package a250225;

import java.util.Scanner;
import java.util.Arrays;

public class NextPerm {

	static int N, R;
	static int[] numbers, input;
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		N = sc.nextInt();
		input = new int[N];
		numbers = new int[R];
		for(int i=0; i<N; i++) {
			input[i] = sc.nextInt();
		}
		// 오름차순 정렬
		Arrays.sort(input);
		
		// np
		
		do {
			System.out.println(Arrays.toString(input));
		} while(np());

	}
	
	
	/*
	 * 현상태에서 사전식 다음 순열 생성 후
	 * 다음 순열 존재시 True, 아니면 false;
	 * 
	 * 1회당 N번정도 순회하는 함수.
	 * 모든 경우의 수 순회하는 경우
	 * N! * N
	 * 
	 * 
	 * 일반 Combination을 했을 때,
	 * 
	 * 
	 */
	static boolean np() {
		// Max 찾기
		
		int i=N-1;
		while(i>0 && input[i-1] >= input[i]) --i; // i 순회: 평균 N/2
	
		if(i==0) return false; // 내림차순으로 정렬된 상태
		
		else {
			int j = N-1;
			
			while(input[i-1] >= input[j]) --j; // j 순회: N/4
		
			swap(input, i-1, j);
			
			int k = N-1;
			while(i<k) swap(input, i++, k--); // swap량: N/4 
			 
			
			return true;
			
			
		}
		
	}
	
	static void swap(int[] arr, int idx1, int idx2) {
		int tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}

}
