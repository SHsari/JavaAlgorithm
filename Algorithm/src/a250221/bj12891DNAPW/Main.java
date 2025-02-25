package a250221.bj12891DNAPW;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Map;
/*
 * S: 주어지는 문자열의 길이
 * P: 비밀번호의 길이
 * 
 * k로 순회하는 for문: A,C,G,T 이렇게 4개를 순회
 * 
 * ACGT 갯수를 누적합으로 기록한다.
 * 비밀번호 윈도우 P를 모두 확인하며 셀 필요 없이
 * index의 시작과 끝만 확인하면 되도록 한다.
 */
public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static int S, P;
	static final Map<Character, Integer> DNAIDX = Map.of('A', 0, 'C', 1, 'G', 2, 'T', 3);
	
	// 누적합 저장할 배열
	static int[][] dnaCount;
	
	// 문자별 최소 갯수를 저장할 배열
	static int[] minACGT = new int[4];

	
	public static void main(String[] args) throws IOException {
		StringTokenizer sp = new StringTokenizer(br.readLine());
		
		S = Integer.parseInt(sp.nextToken());
		P = Integer.parseInt(sp.nextToken());
		
		dnaCount = new int[4][S+1];
		int[] dctmp = new int[4];

		String dnaString = br.readLine();
		for(int i=0; i<S; i++) {
			int idx = DNAIDX.get(dnaString.charAt(i));
			dctmp[idx] ++;
			for(int k=0; k<4; k++) {
				dnaCount[k][i+1] = dctmp[k];
			}
		}

		StringTokenizer acgt = new StringTokenizer(br.readLine());
		for(int k=0; k<4; k++) {
			minACGT[k] = Integer.parseInt(acgt.nextToken());
		}
		

		/*
		 * 실수한 것:
		 * 누적합에서 cnt[start] - cnt[end]
		 * 위의 경우 start+1 에서 end까지의 갯수를 세게 된다.
		 * start index의 문자는 갯수에서 제외됨.
		 */
		int count =0;
		for(int start=0; start<=S-P; start++) {
			boolean isValidPW = true;
			int end = start+P;
			for(int k=0; k<4; k++) {
				if(dnaCount[k][end] - dnaCount[k][start] < minACGT[k]) {
					isValidPW = false; break;
				}
			}
			if(isValidPW) count ++;
		}

		System.out.println(count);
	}
}
