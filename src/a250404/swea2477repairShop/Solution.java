package a250404.swea2477repairShop;

import java.io.*;
import java.util.Arrays;

public class Solution {
	/* SWEA 2477 차량 정비소
	 * 
	 * 1. 고객들의 도착시간이 순서대로 주어집니다.
	 *  -> 순서대로 받습니다.
	 * 
	 * 2. reception 단 O(K*N)
	 *  -> 고객들을 순회하면서 몇번 창구에 들어갈 지 확인합니다.
	 *  -> 동시에 고객들의 접수업무가 끝난 시각을 기록합니다.
	 * 
	 * 3. Customer2 class를 이용해 고객들을 다시 정렬합니다. O(K*logK)
	 *  -> 접수창구에서 나온 순서대로 접근할 수 있도록 합니다.
	 * 
	 * 4. Repair 단. O(K*M)
	 *  -> 위에서 정렬한 순서대로 고객들을 순회하면서
	 *  -> 2번과 동일한 방법으로 고객들의 수리 데스크 번호를 확인합니다.
	 *  -> 즉시 지갑을 잃어버린 고객과의 일치여부를 확인하여 index를 더해줍니다.
	 * 
	 */


	static class Customer2 implements Comparable<Customer2> {
		/*
		 * 접수창구를 빠져나온 고객들을
		 * 빠져나온 순서대로 접근하기 위해
		 * CompareTo를 정의한 클래스입니다.
		 */
		int idx;
		Customer2(int index) { this.idx = index; } 

		@Override
		public int compareTo(Customer2 o) {
			// 1. 시간 우선
			int diff = customers[this.idx] - customers[o.idx];
			if (diff != 0)
				return diff;
			// 2. 동시일 경우 창구번호 우선.
			else
				return visitedRecep[this.idx] - visitedRecep[o.idx];
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	static int recepNum, repairNum, customerNum, A, B;
	static int[] receptions, repairs, customers;
	static int[] visitedRecep;

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StreamTokenizer(br);
		result = new StringBuilder();

		int T = nextInt();
		for (int tc = 1; tc <= T; tc++) {
			result.append("#").append(tc).append(" ");

			// 입력
			init();
			
			// 접수데스크 단.
			// visitedRecep[idx]에 idx고객의 접수데스크 번호를 저장.
			// customers[idx]에 접수업무가 끝난 시각을 저장. (기존 도착시간을 덮어씀)
			doReception();

			// 수리데스크단
			int custIdxSum = doRepair();
			if(custIdxSum == 0) custIdxSum = -1;
			result.append(custIdxSum).append("\n");
		}
		System.out.println(result);
	}

	static void init() throws IOException {

		recepNum = nextInt();
		repairNum = nextInt();
		customerNum = nextInt();
		A = nextInt();
		B = nextInt();

		receptions = new int[recepNum + 1];
		repairs = new int[repairNum + 1];
		customers = new int[customerNum + 1];
		visitedRecep = new int[customerNum + 1];

		for (int rc = 1; rc <= recepNum; rc++)
			receptions[rc] = nextInt();
		for (int rp = 1; rp <= repairNum; rp++)
			repairs[rp] = nextInt();
		for (int c = 1; c <= customerNum; c++)
			customers[c] = nextInt();

	}

	// 접수창구 거치기.
	static void doReception() {
		int[] endTimes = new int[recepNum + 1];
		// 접수창구는 모두 비어있다.
		Arrays.fill(endTimes, Integer.MIN_VALUE);

		for (int cIdx = 1; cIdx <= customerNum; cIdx++) {
			// 고객이 진입할 창구 번호와 해당 창구에 진입할 시간에 대한 변수
			int deskIdx = 0;
			int deskEnterTime = Integer.MAX_VALUE;

			// 창구를 순회합니다.
			for (int rIdx = 1; rIdx <= recepNum; rIdx++) {
				// 빈 창구 발견! 경우
				if (endTimes[rIdx] <= customers[cIdx]) {
					deskIdx = rIdx;
					deskEnterTime = customers[cIdx];
					break;
				}
				// 빈곳이 아닌경우, 가장 빨리 빈곳이 될 창구 확인.
				else if (deskEnterTime > endTimes[rIdx]) {
					deskIdx = rIdx;
					deskEnterTime = endTimes[rIdx];
				}
			}
			// 창구 결정 후 창구 상태 및 고객상태 업데이트
			endTimes[deskIdx] = deskEnterTime + receptions[deskIdx];
			// reception 업무 끝난 시간 저장.
			customers[cIdx] = endTimes[deskIdx];
			// reception 창구 번호 저장
			visitedRecep[cIdx] = deskIdx;

		}
	}

	// 수리창구 거치기.
	static int doRepair() {
		// 일단 고객들을 접수과정을 마친 시간순으로 접근할 수 있도록 작업합니다.
		Customer2[] customers2 = new Customer2[customerNum];
		for (int cIdx = 0; cIdx < customerNum; cIdx++) {
			customers2[cIdx]= new Customer2(cIdx+1);
		}
		Arrays.sort(customers2);

		// 수리 창구 셋팅 -> 접수창구와 동일
		int[] endTimes = new int[repairNum + 1];
		Arrays.fill(endTimes, Integer.MIN_VALUE);

		// 바로 전화돌릴 고객들의 고객번호 합을 구합니다.
		int idxSum = 0;

		// 접수를 마친 고객순으로 접근
		for (int idx = 0; idx < customerNum; idx++) {
			int cIdx = customers2[idx].idx;

			int deskIdx = 0;
			int deskEnterTime = Integer.MAX_VALUE;

			// 수리창구를 1번부터 순회하며 
			// cIdx 번 고객이 몇번 수리창구를 이용했는 지 구함.
			for (int rIdx = 1; rIdx <= repairNum; rIdx++) {

				if (endTimes[rIdx] <= customers[cIdx]) {
					deskEnterTime = customers[cIdx];
					deskIdx = rIdx;
					break;
				} else if (deskEnterTime > endTimes[rIdx]) {
					deskEnterTime = endTimes[rIdx];
					deskIdx = rIdx;
				}
			}

			endTimes[deskIdx] = deskEnterTime + repairs[deskIdx];
			if (deskIdx == B && visitedRecep[cIdx] == A)
				idxSum += cIdx;
		}
		return idxSum;
	}

	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}