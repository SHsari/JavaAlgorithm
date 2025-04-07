package a250407.boj7579app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Main {
	/*
	 * BOJ 7579 앱
	 * 
	 * N개의 활성화 된 앱들.
	 * 각 앱은 m[i] 바이트의 메모리 사용.
	 * 각 앱은 비활성화 후 재실행 하는데 ci 만큼의 비용이 듦.
	 * 
	 * 사용자가 추가로 M byte의 메모리가 필요할 때, 
	 * 활성화 앱들 중 일부를 비활성화 하여 M byte의 메모리를 확보하는데,
	 * 이때 재실행비용을 최소화 하는 경우를 구하여라 (최소 재실행비용 출력)
	 * 
	 * -> solve1 : 모든 memory 경우에 수에 대해 cost를 구함...
	 * -> solve2 : 모든 cost에 대한 최대 memory를 구한다.
	 */

	static BufferedReader br;
	static StreamTokenizer st;

	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        solve2();
       
	}
	

	/*
	 * 모든 cost에 대한 최대 memory를 구한다.
	 * 시간복잡도
	 * 앱의 갯수 N <= 100
	 * 개별 cost의 범위 1 <= cost <= 100
	 * cost합 <= 10000
	 * 
	 * O(costSum*N) ~~ O(10^6)
	 */
	static void solve2() throws IOException {
	
		int N = nextInt();
        int LowerBound = nextInt();
        
        int[] cost = new int[N];
        int[] memory = new int[N];
        
        for(int i=0; i<N; i++) memory[i] = nextInt();
        
        int costSum=0;
        for(int i=0; i<N; i++) {
        	cost[i] = nextInt();
        	costSum+=cost[i];
        }
        
        
        int[] dp = new int[costSum+1];
        
        Arrays.fill(dp, 0, cost[0], 0);
        Arrays.fill(dp, cost[0], costSum+1, memory[0]);
        
        for(int idx=1; idx<N; idx++) {
        	for(int costLim=costSum; costLim>=cost[idx]; costLim--)
        		dp[costLim] = Math.max(dp[costLim], dp[costLim-cost[idx]]+memory[idx]);
        }
        
        for(int i=0; i<=costSum; i++) {
        	if(dp[i] >= LowerBound) {
        		System.out.println(i);
        		return;
        	}
        }
	}
	
	/*
	 * 모든 0~Limit의 Memory LowerBound에 대해서
	 * 최소 cost를 구하는 방식
	 * 
	 * memory 값이  1~10^7 이다.
	 * 
	 * 시간복잡도
	 * O(10^7 * N ) ~~ O(10^9)
	 * 
	 * 일반적인 2차원 dp 배열을 쓰면 메모리 초과가 난다.
	 */
	static void solve1() throws IOException {
		int N = nextInt();
        int Limit = nextInt(); //LowerBound
        
        int[] cost = new int[N];
        int[] memory = new int[N];
        int[] minCost = new int[Limit+1]; // dp 배열
        
        for(int i=0; i<N; i++) memory[i] = nextInt();
        for(int i=0; i<N; i++) cost[i] = nextInt();
        
        // 0번 앱에 대해서 초기화 과정
        if(memory[0] <= Limit) {
        	Arrays.fill(minCost, 0, memory[0]+1, cost[0]);
        	Arrays.fill(minCost, memory[0]+1, Limit+1, Integer.MAX_VALUE);
        }else { Arrays.fill(minCost, cost[0]); }
        
        
        // 1번 이후 앱에 대해서.
        int memorySum = memory[0];
        for(int idx=1; idx<N; idx++) {
        	// 지금까지의 메모리합과 LowerBound중 적은것을 현재 Limit으로 삼기.
        	memorySum += memory[idx];
        	int curLimit = Math.min(Limit, memorySum);
        
        	// 1차원 dp 배열을 쓰기 때문에 높은 Limit 부터.
        	// 현재 앱의 memory 보다 높은 LowerBound에 대하여.
        	for(int lim=curLimit; lim>=memory[idx]; lim--)
        		// 해당 limit (lowerBound)에 대해 최소 cost 업데이트
        		minCost[lim] = Math.min(minCost[lim], minCost[lim-memory[idx]] + cost[idx]);
        	
        	// 현재 앱의 memory 보다 적은 부분에 대하여.추가 업데이트
        	int bound = Math.min(memory[idx], Limit);
        	for(int lim=bound; lim>=0; lim--)
        		minCost[lim] = Math.min(minCost[lim], cost[idx]);
        }
        System.out.println(minCost[Limit]);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}
