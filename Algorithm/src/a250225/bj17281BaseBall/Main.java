package a250225.bj17281BaseBall;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static final int NINE = 9;
	static final int THREE = 3;
	
	static int[][] inScr; // 이닝별 타자의 성적
	static int[] eintaList = new int[NINE];
	static boolean[] isUsed;
	static int tajaIter;
	static int N;
	static int roux;
	static int score;
	static int maxScore=0;
	
	public static void main(String[] args) throws IOException{
		
		N = Integer.parseInt(br.readLine());
		
		inScr = new int[N][NINE];
		
		for(int inning=0; inning<N; inning++) {
			StringTokenizer line = new StringTokenizer(br.readLine());
			for(int taja=0; taja<NINE; taja++) {
				inScr[inning][taja] = Integer.parseInt(line.nextToken());	
			}
		}
		
		isUsed = new boolean[NINE];
		isUsed[0] = true;
		eintaList[THREE] = 0;
		
		permutateEintaList(0);
		
		System.out.println(maxScore);
	}
	
	static void permutateEintaList(int depth) {
		// 순서가 다 짜여졌다면
		if(depth==NINE) {
			score=0;
			tajaIter=0;
			for(int inning=0; inning<N; inning++) {
				playInning(inning);
			}
			
			
			if(score>maxScore) {
				maxScore = score;
			}
			return;
		}
		
		// 4번타자 제외
		else if(depth==THREE) depth ++;
		
		// 
		for(int idx=1; idx<NINE; idx++) {
			if(!isUsed[idx]) {
				
				eintaList[depth] = idx;
				isUsed[idx] = true;
				permutateEintaList(depth+1);
				isUsed[idx] = false;
				
			}
		}
	}
	
	/*
	 * 한이닝 플레이
	 * 자동으로 스코어와 roux 현황 업데이트
	 */
	static void playInning(int inning) {
		
		int outCount=0;
		roux = 0;
		
		while(outCount < 3) {
			int taja = eintaList[tajaIter];
			roux+=1; // 치는 타자 1명 추가;
			
			// 한명의 타자 플레이
			int tajaScore = inScr[inning][taja];
			
			//아웃이라면
			if(tajaScore==0)  {
				outCount ++;
				roux --;
			}
			//성적을 냈다면
			//정해진 타자 성적에 따라 
			//진루 상황과 점수 업데이트 (roux, score)
			else forward(tajaScore);			
			
			//다음타자.
			tajaIter = (tajaIter+1) % NINE;
		}
	}
	
	/*
	 * tajaScore을 받아서
	 * static 변수인 score과 roux 업데이트
	 */
	static void forward(int tajaScore) {
		for(int i=0; i<tajaScore; i++) {
			//진루
			roux *= 2;
			//만약 3루에서 진루했다면 득점
			score += roux / 16;
			//득점한 주자 제거
			roux %= 16;
		}
	}
}
