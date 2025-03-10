package a250221.swea1225pwMaker;

import java.io.*;
/*
 * SWEA_1225_암호생성기
 * 
 * 별다른.. 설명이 필요가 없네요.
 * 
 * 숫자는 8개
 * index = (index+1)%8;
 * subtract = (subtract+1)%5;
 * 
 * 해서 연산한 숫자가 0 이하가 될 때까지 돌립니다.
 * 
 */

public class SWEA_1225_암호생성기_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        int T = 10;
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			initAndSolve();			
		}
		
		System.out.println(result);
	}

	
	static void initAndSolve() throws IOException {
		nextInt();
		
		int[] array = new int[8];
		for(int i=0; i<8; i++) {
			array[i] = nextInt();
		}
		
		int startPoint =7;
		int subtract = 0;
		do {
			startPoint = (startPoint+1) % 8;  
			array[startPoint] -= subtract+1;
			subtract = (subtract +1) %5;
		}while(array[startPoint] > 0);
		
		array[startPoint] = 0;
			
		for(int i=0; i<8; i++) {
			startPoint = (startPoint+1) % 8;
			result.append(array[startPoint]).append(" ");
		}
		result.append("\n");
		
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}