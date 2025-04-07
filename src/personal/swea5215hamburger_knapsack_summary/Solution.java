package personal.swea5215hamburger_knapsack_summary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Stack;

public class Solution {
	
	/*
	 * SWEA 5215 햄버거
	 * 
	 * 다시 문제를 읽어보니
	 * 가장 기본적인 KnapSack Problem이네용..
	 * 
	 * 그때보다 쬐금 늘긴 했나 봅니다.
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int N, Limit;
	static int[] TasteOf, CalorieOf;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			init();
			int maxTaste = -1;
			// maxTaste = dp();
			// maxTaste = dfsDP(N-1, Limit);
			maxTaste = dfsMemo();
			result.append(maxTaste).append("\n");
			
		}
		System.out.println(result);
	}
	
	
	/*
	 * 정석적인 knapsack dp 입니다.
	 * 0~N-1번을 차례대로 사용 고려 대상에 추가합니다.
	 * 해당 재료를 넣을 때와 넣지 않을 때 중 더 큰 편익을
	 * tasteAt[재료idx][weightLimit]
	 * 에 저장합니다.
	 * 
	 * 가능한 모든 weightLimit에 대해 최대 편익을 계산해야 한다는 점이 
	 * 생각하기 어려운 부분입니다.
	 */
	static int dp() {
		int[][] tasteAt = new int[N][Limit+1];
		// 0번 재료에 대한 셋팅
		Arrays.fill(tasteAt[0], CalorieOf[0], Limit+1, TasteOf[0]);
		
		// 1번 재료부터 반복문으로.
		for(int src=1; src<N; src++) {
			// src의 칼로리 보다 작은 곳에 대해서는 
			// 무조건 src를 넣지 않았을때의 편익을 넣어줌.
			for(int cal=1; cal<CalorieOf[src]; cal++) {
				tasteAt[src][cal] = tasteAt[src-1][cal];
			}
			
			// src의 칼로리 보다 큰 곳에 대해서는
			// src를 넣을때, 넣지 않을때의 편익을 비교해야 합니다.
			for(int cal=CalorieOf[src]; cal<=Limit; cal++) {
				int tasteWithCur = tasteAt[src-1][cal-CalorieOf[src]] + TasteOf[src];
				tasteAt[src][cal] = Math.max(tasteAt[src-1][cal], tasteWithCur);
			}
		}
		// 모든 재료를 고려했을때 최대 무게에 대한 최대 편익.
		return tasteAt[N-1][Limit];
	}
	
	/*
	 * dfs로 풀기.
	 * 위의 DP와 사실상 거의 동일하나
	 * 중복 호출로 인해서 시간복잡도가 최대 2^N까지 증가합니다.
	 * 잉 백트랙킹을 사용한 부분집합 완전탐색과 거의 동일하구만..
	 */
	static int dfsDP(int srcIdx, int calLimit) {
		if(srcIdx==0) {
			if(calLimit >= CalorieOf[srcIdx]) return TasteOf[srcIdx];
			else return 0;
		}
		
		else {
			int noCurSrc = dfsDP(srcIdx-1, calLimit);
			int withCurSrc = 0;
			if(calLimit>=CalorieOf[srcIdx])
				withCurSrc = dfsDP(srcIdx-1, calLimit-CalorieOf[srcIdx]) + TasteOf[srcIdx];
			return Integer.max(noCurSrc, withCurSrc);
		}
	}
	
	/*
	 * 위의 DFS에서 중복호출을 줄이기 위해
	 * 메모이제이션을 사용하려고 합니다.
	 */
	static class State {
		int limit, src;
		
		State(int srcIdx, int calLimit) {
			this.src = srcIdx;
			this.limit = calLimit;
		}
	}
	
	
	static int dfsMemo() {
		
		int[][] profitMemo = new int[N][Limit+1];
		for(int i=0; i<N; i++)
			Arrays.fill(profitMemo[i], -1);
		boolean[][] didPush = new boolean[N][Limit+1];
		Stack<State> stack = new Stack<>();
		stack.push(new State(N-1, Limit));
		didPush[N-1][Limit] = true;

		int pushCnt=0;
		while(!stack.empty()) {
			// 빼내지 않고 들여다보기만.
			State curr = stack.peek();
			
			//현재 상태 계산이 필요한 상황
			if(profitMemo[curr.src][curr.limit] < 0) {
				// 현재가 인덱스 =0 인경우 바로 계산을 해준다.
				if(curr.src == 0) {
					int profit = (CalorieOf[0] <= curr.limit) ? TasteOf[0] : 0;
					profitMemo[0][curr.limit] = profit;
					stack.pop();
				}
				// 인덱스 != 0 인 경우 하위 데이터 존재여부를 확인한다.
				else { 
					int childProcess = 0;
					
					// 하위 상태에 대한 계산이 없을 경우.
					if(!didPush[curr.src-1][curr.limit]) {
						stack.push(new State(curr.src-1, curr.limit));
						didPush[curr.src-1][curr.limit] = true;
						childProcess++; pushCnt++;
					} 
					int withCurrCalLimit = curr.limit - CalorieOf[curr.src];
					if(curr.limit >= CalorieOf[curr.src] && !didPush[curr.src-1][withCurrCalLimit]) {
						stack.push(new State(curr.src-1, withCurrCalLimit));
						didPush[curr.src-1][withCurrCalLimit] = true;
						childProcess++; pushCnt++;
					}
					// 하위 상태에 대한 계산이 있을 경우.
					if(childProcess==0) {
						int withoutCurr = profitMemo[curr.src-1][curr.limit];
						int withCurr = 0;
						if(curr.limit >= CalorieOf[curr.src]) 
							withCurr = TasteOf[curr.src] + profitMemo[curr.src-1][curr.limit - CalorieOf[curr.src]];
						
						profitMemo[curr.src][curr.limit] = Math.max(withoutCurr, withCurr);
						stack.pop();
					}
				}
			}
		}
		
		return profitMemo[N-1][Limit];
	}
	
	static void init() throws IOException {
		
		N = nextInt();
		Limit = nextInt();
		
		TasteOf = new int[N];
		CalorieOf = new int[N];
		
		for(int i=0; i<N; i++) {
			TasteOf[i] = nextInt();
			CalorieOf[i] = nextInt();
		}
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}