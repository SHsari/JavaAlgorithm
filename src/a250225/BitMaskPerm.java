package a250225;

import java.util.Scanner;
import java.util.Arrays;

public class BitMaskPerm {

	static int N, R;
	static int[] numbers, input;
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		N = sc.nextInt();
		R = sc.nextInt();
		input = new int[N];
		numbers = new int[R];
		for(int i=0; i<N; i++) {
			input[i] = sc.nextInt();
		}
		bitPerm(0, 0);

	}
	
	static void bitPerm(int cnt, int flag) {
		if(cnt == R) {
			System.out.println(Arrays.toString(numbers));
			return;
		}
		
		for(int i=0; i<N; i++) {
			if((flag & 1<<i)  != 0) continue;
			numbers[cnt] = input[i];
			bitPerm(cnt+1, flag | 1<<i);
		}
	}

}
