package a250219.swea6808CardGame;

import java.io.*;
import java.util.StringTokenizer;

//약간 말도안되는 최적화를 해보자

public class Solution2 {
	// 상수들
	public static final int GY = 0;
	public static final int IY = 1;
	public static final int NINE = 9;
	public static final int NINETEEN = 19;
	static final int WINSCR = 86;
	static final int factorial[] = { 362880, 40320, 5040, 720, 120, 24, 6, 2, 1, 1 };
	
	static int[] scores = {0,0};

	// 소유한 카드배열
	// 순서대로 저장된 규영 카드배열
	static int[] gy;
	// 순서가 무관한 인영 카드 셋트
	static int[] iySet;

	//인영 순열순회를 위한 비짓트 배열
	static boolean[] visitedIY = new boolean[NINE];

	static int[] winCount = {0,0};
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for(int tc=1; tc<=T; tc++) {
			winCount[GY] = 0;
			winCount[IY] = 0;
			
			gy = new int[NINE];
			iySet = new int[NINE];
			
			boolean[] isGy = new boolean[NINETEEN];


			//규영이의 목록을 일단 받자
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<NINE; i++) {
				int gyi = Integer.parseInt(st.nextToken());
				gy[i] = gyi;
				isGy[gyi] = true;
			}

			//isGyuyoung 배열 확인하며 in Young 추가
			int icount = 0;
			for(int i=1; i<NINETEEN; i++) {
				if(!isGy[i]) iySet[icount++] = i;
			}
			
			// 여그꺼정 초기화 완료인가
			getGS(0);
			sb.append("#" + tc + " ");
			sb.append(winCount[GY] + " ");
			sb.append(winCount[IY] + "\n");
		}

		System.out.println(sb);

	}
	
	static void getGS(int depth) {
		int bound = NINE-depth;
		for(int i=0; i<bound; i++) {
			int score = gy[depth] + iySet[i];
			int winner = gy[depth] > iySet[i]? GY : IY;
			scores[winner] += score;
			if(scores[winner] >= WINSCR) {
				winCount[winner] += factorial[depth+1];
				scores[winner] -= score;
				continue;
			}
			
			swap(iySet, i, bound-1);
			getGS(depth+1);
			swap(iySet, i, bound-1);
			scores[winner] -= score;

		}
	}
	
	static void swap(int[] arr, int idx1, int idx2) {
		int tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}
	
}
//
//interface GetGS {
//	public void getGS(int depth);
//}
//
//class GetGsFirst implements GetGS {
//	@Override
//	public void getGS(int depth) {
//		
//	}
//}
//
//class GetGsSecond implements GetGS {
//	@Override
//	public void getGS(int depth) {
//		
//	}
//}
//
//class GetGsLast implements GetGS {
//	@Override
//	public void getGS(int depth) {
//		
//	}
//}
//
//
//
//
