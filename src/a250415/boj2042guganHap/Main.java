package a250415.boj2042guganHap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Main {
	/*
	 * boj2042 구간합 구하기.
	 * 
	 * 기본 구간합 문제에 실시간으로 배열의 특정 인덱스 수정을 반영해야 합니다.
	 * 
	 * 배열의 길이 N
	 * 수정 횟수 M
	 * 구간합 요청 횟수 K
	 * 
	 * 길이 N의 일반적인 누적합 배열을 만듭니다.
	 * 수정 명령이 있을때 마다, 수정 index, 수정 값을 TreeMap에 넣습니다.
	 * 수정 index의 크기를 이용해 log(M) 이내에 수정 값에 접근하기 위함입니다.
	 * 
	 * 구간합 요청이 있을 경우
	 * 시작index부터 끝 index 사이에 있었던 모든 수정요청을 가져와서
	 * 기본 구간합 값에 수정사항을 반영해줍니다.
	 * 
	 * O(M*log(M) + K*log(M) + M) 이거 맞나..?
	 */
	
	static BufferedReader br;
	
	static int N, M, K;
	static long origin[];
	static TreeMap<Integer, Long> changes;
	
	public static void main(String[] args) throws IOException {
		init();
		System.out.print(solve());
	}
	
	static StringBuilder solve() throws IOException {
		StringBuilder result = new StringBuilder();
		for(int i=0; i<M+K; i++) {
			StringTokenizer line = new StringTokenizer(br.readLine());
        	int command = Integer.parseInt(line.nextToken());
        	
        	// change
        	if (command == 1) {
        		int idx = Integer.parseInt(line.nextToken());
        		long newValue = Long.parseLong(line.nextToken());
        		long ogValue = origin[idx] - origin[idx-1];
        		changes.put(idx, newValue-ogValue);
        	}
        	else if (command == 2) {
        		int startIdx = Integer.parseInt(line.nextToken());
        		int endIdx = Integer.parseInt(line.nextToken());
        		long ggh = getGGH(startIdx, endIdx);
        		result.append(ggh).append("\n");
        	}
        }
		return result;
	}

	
	static long getGGH(int from, int to) {
		long ggh = origin[to] - origin[from-1];
		
		Collection<Long> submap = changes.subMap(from, true, to, true).values();
		for(long diffValue : submap) {
			ggh += diffValue;
		}

		
		return ggh;
	}

	static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine());
      
        N = Integer.parseInt(line.nextToken());
        M = Integer.parseInt(line.nextToken());
        K = Integer.parseInt(line.nextToken());
        
        origin = new long[N+1];
        changes = new TreeMap<>();
        for(int i=1; i<=N; i++) {
        	origin[i] = origin[i-1] + Long.parseLong(br.readLine());
        }
	}
}