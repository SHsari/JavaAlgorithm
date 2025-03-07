package a250220.dice;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.Scanner;

public class dice {
	static int[] list;
	static int M;
	static int N;
	static int mode;


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		mode = sc.nextInt();
		list = new int[M];
		dice2(0, 1);

	}
	
	static void dice() {
		
	}
	
	static void dice1() {
		
	}
	
	
	//중복 조합
	static void dice2(int depth, int stpt) {
		if(depth == M) {
			System.out.println(Arrays.toString(list));
			return;
		}
		for(int i=stpt; i<=6; i++) {
			list[depth] = i;
			dice3(depth+1, i);
		}
	}
	
	//일반 조합
	static void dice3(int depth, int stpt) {
		if(depth == M) {
			System.out.println(Arrays.toString(list));
			return;
		}
		for(int i=stpt; i<=6; i++) {
			list[depth] = i;
			dice3(depth+1, i+1);
		}
	}
}

@FunctionalInterface
interface NPCM {
	public void sth(int depth, int stpt);
}
