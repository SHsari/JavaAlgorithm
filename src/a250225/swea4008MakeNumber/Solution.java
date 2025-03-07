package a250225.swea4008MakeNumber;

import java.io.*;
import java.util.StringTokenizer;
import java.util.function.BiFunction;
import java.util.List;
import java.util.ArrayList;


/*
 * SWEA 4008 숫자 만들기
 * 
 * 문제를 볼 때마다 드는 생각은.
 * 완탐을 해야되나 이거??
 * 
 * 완탐 없이 어떻게 풀지?
 * 
 * -> 이러다가 못푼다.
 * -> 일단 완탐으로 풀고. 나중에 프루닝을 하던..
 * 
 * 숫자의 갯수 N
 * 3 <= N <= 12 
 * 연산자의 갯수는 N-1
 * 
 * 
 */

public class Solution {
	
	static final int OPERATOR_NUMBER = 4; 
	static final int PLUS = 0;
	static final int MINUS = 1;
	static final int MUL = 2;
	static final int DIV = 3;
	
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();

	static int N;
	static int[] numbers;
	static int[] operators = new int[OPERATOR_NUMBER];
	
	static int min, max;
	
	static List<BiFunction<Integer, Integer, Integer>> operator = new ArrayList<>();
	static {
		operator.add((left, right) -> {return left + right;});
		operator.add((left, right) -> {return left - right;});
		operator.add((left, right) -> {return left * right;});
		operator.add((left, right) -> {return left / right;});
	}

	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			result.append("#" + tc + " ");
			
			min = Integer.MAX_VALUE;
			max = Integer.MIN_VALUE;
			
			N = Integer.parseInt(br.readLine());
			
			//오퍼레이터별 갯수 입력
			StringTokenizer operatorLine = new StringTokenizer(br.readLine());
			for(int idx =0; idx < OPERATOR_NUMBER; idx ++) {
				operators[idx] = Integer.parseInt(operatorLine.nextToken());
			}
			
			//주어진 숫자 리스트 입력.
			numbers = new int[N];
			StringTokenizer numberLine = new StringTokenizer(br.readLine());
			for(int idx = 0; idx < N; idx ++) {
				numbers[idx] = Integer.parseInt(numberLine.nextToken());
			}
			
			
			operatorPermutation(0, numbers[0]);
			
			
			result.append(max-min).append("\n");
		}
		
		System.out.println(result);
	}
	
	/* 
	 * 그냥 이전에 사용했던 순열을 사용해보겠다.
	 * visited는 operator 갯수를 하면 될 듯..?
	 */
	static void operatorPermutation(int depth, int result) {
		if(depth == N-1) {
			if(result < min) min = result;
			if(result > max) max = result;
		}
		
		for(int i=0; i<OPERATOR_NUMBER; i++) {
			if(operators[i] != 0) {
				
				operators[i] --;
				int newResult = operator.get(i).apply(result, numbers[depth+1]);
				operatorPermutation(depth+1, newResult);
				operators[i] ++;
			}
		}
		
		return;
	}
}
