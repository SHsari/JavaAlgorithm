package a250410.boj12865normalBag;

import java.io.*;
import java.util.Arrays;


public class Main {
	/*
	 * 저번에 knapSack 풀 때도 느낀 것 인데 
	 * Knapsack dp는 제약조건이 하나씩 추가되면서
	 * 서로다른 결과 값을 가지는 구간의 갯수가 늘어나는 구조이다.
	 * 즉, 구간내의 값은 동일하므로,
	 * -> N*WeightLimit 모든 숫자에 대해서 계산하고
	 * 배열에 넣어주고 하는 일이 비효율적이라 느꼈다.
	 * 
	 * 고로 필요한 구간의 경곗값에 대해서만 계산할 수 있도록
	 * memoization을 이용한 DFS를 사용해보자.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static int N, Limit, weight[], value[];
	static int maxValueAt[][]; // 메모할 곳.
	
	public static void main(String[] args) throws IOException {
		
		init();
		int answer = dfs(N-1, Limit);
		System.out.println(answer);
	}
	
	static int dfs(int objIdx, int wLimit) {
		if(maxValueAt[objIdx][wLimit] >= 0) return maxValueAt[objIdx][wLimit];
		
		// 현재 index의 object 비포함하는 경우 값.
		int maxProfit = dfs(objIdx-1, wLimit);
		
		// 현재 index의 object가 현재의 wLimit에서 포함 가능한 지 확인
		int nextWLimit = wLimit - weight[objIdx];
		// 아래 부등호 범위 0 포함 안시켜서 정답 잘못계산. 꼭 포함시켜주자.
		if(nextWLimit >= 0) {
			// 포함 가능하다면 포함했을 경우 값 계산하여 반영
			int profitWithCurrObj = dfs(objIdx-1, nextWLimit) + value[objIdx];
			maxProfit = Math.max(maxProfit, profitWithCurrObj);
		}
		maxValueAt[objIdx][wLimit] = maxProfit;
		return maxProfit;
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        Limit = nextInt();
        weight = new int[N];
        value = new int[N];
        maxValueAt = new int[N][Limit+1];
        
        for(int i=0; i<N; i++) {
        	Arrays.fill(maxValueAt[i], -1);
        	weight[i] = nextInt();
        	value[i] = nextInt();
        }
        // 0번째 물건에 대해서 간단히 초기화.
        Arrays.fill(maxValueAt[0], 0, weight[0], 0);
        Arrays.fill(maxValueAt[0], weight[0], Limit+1, value[0]);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}