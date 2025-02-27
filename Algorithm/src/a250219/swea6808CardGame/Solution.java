package a250219.swea6808CardGame;


//약간 말도안되는 최적화를 해보자

import java.io.*;
import java.util.StringTokenizer;


public class Solution {


	// 상수들
	static final int NINE = 9;
	static final int EIGHTEEN = 18;
	static final int WINSCR = 86;
	static final int NINEFACTORIAL = 362880;

	// 소유한 카드배열
	// 순서대로 저장된 규영 카드배열
	static int[] gy;
	// 순서가 무관한 인영 카드 셋트
	static int[] iySet;
	// 순서대로 메반 재배치되는 인영 카드 배열
	static int[] iy;

	// 규영이 소유한 카드배열을 불리언으로
	static boolean[] isGy;

	//인영ㅇ 순열순회를 위한 비짓트 배열
	static boolean[] visitedIY = new boolean[NINE];



	static int gyWinCount;
	static int iyWinCount;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());


		for(int tc=1; tc<=T; tc++) {

			gy = new int[NINE];
			iySet = new int[NINE];
			iy = new int[NINE];
			isGy = new boolean[EIGHTEEN];

			gyWinCount = 0;
			iyWinCount = 0;

			//규영이의 목록을 일단 받자
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<NINE; i++) {
				int gyi = Integer.parseInt(st.nextToken());
				gy[i] = gyi;
				isGy[gyi-1] = true;
			}

			//isGyuyoung 배열 확인하며 in Young 추가
			int icount = 0;
			for(int i=0; i<EIGHTEEN; i++) {
				if(!isGy[i]) iySet[icount++] = i+1;
			}

			// 여그꺼정 초기화 완료인가
			nFactorialIY(0);
			sb.append("#" + tc + " ");
			sb.append(gyWinCount + " ");
			sb.append(iyWinCount + "\n");
		}

		System.out.println(sb);

	}



	static void nFactorialIY(int depth) {
		if(depth==NINE) {
			int gyScr=0;
			int iyScr=0;
			for(int i=0; i<NINE; i++) {
				if(gy[i] > iy[i]) { 
					gyScr += gy[i] + iy[i];	
				}
				else {
					iyScr += gy[i] + iy[i];
				}
				if(gyScr>=WINSCR) {
					gyWinCount++;
					break;
				}
				if(iyScr>=WINSCR) {
					iyWinCount++;
					break;
				}
			}

			return;
		}

		for(int i=0; i<NINE; i++) {
			if(!visitedIY[i]) {
				visitedIY[i] = true;
				iy[depth] = iySet[i];
				nFactorialIY(depth+1);
				visitedIY[i] = false;
			}
		}

	}

}
