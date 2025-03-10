package a250221.swea3421burger;


import java.io.*;
//import java.util.Set;
//import java.util.HashSet;

/* SWEA 3421 수제버거 장인
 * 
 * 비트마스크와 부분집합을 이용했습니다.
 * 재료별로 충돌이 있는 다른 재료들을 bitmask로 저장합니다.
 * 
 * 부분집합 재귀 함수로 재료들을 순회하며 새로운 재료를 추가할 때마다
 * 1. 새 재료가 기존 재료와 충돌이 있는 지 확인
 * 2. 충돌이 없다면 새 재료의 충돌 bitmask를 기존 bitmask와 or 연산으로 추가.
 */

public class SWEA_3421_수제버거장인_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int[] conflicts;
	//static Set<Integer> visitedComb;
	static int combCount;
	
	static int ingredientsNumber;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			
			init();
			combination(0, 0, 0);
			result.append(combCount).append("\n");
		}
		
		System.out.println(result);
	}

	// 부분집합 함수.
	static void combination(int currentComb, int depth, int conflictList) {
		
		// 모든 재료에 대한 첨가 여부를 결정했을 때 재귀를 종료합니다.
		if(depth == ingredientsNumber) return;
		
		int conflictCount = Integer.bitCount(conflictList);
		int combinationCount = Integer.bitCount(currentComb);
		
		// 들어간 재료의 수와 충돌로 인해 넣지 못하는 재료의 수의 합이 전체 재료의 수와 같다면 재귀를 종료합니다.
		if(conflictCount + combinationCount == ingredientsNumber) return;
		
		
		// 재귀 진행 가능시.
		// 일단  index == depth 인 재료를 넣지 않는 경우를 생각합니다.
		combination(currentComb, depth+1, conflictList);
		
		// 현재 재료에 대한 confilct 없다면,
		if((conflictList & (1<<depth)) == 0) {
			//현재 재료를 추가하고
			currentComb |= 1<<depth;
			//충돌 리스트에 현재 재료와의 충돌을 추가.
			conflictList |= conflicts[depth];
			
			combCount++;
			combination(currentComb, depth+1, conflictList);
		}
	}
	
	//매 TestCase마다 입력을 받고 초기화해주는 함수 init();
	
	static void init() throws IOException {
		ingredientsNumber = nextInt();
		int M = nextInt();
		
		conflicts = new int[ingredientsNumber];
		
		for(int i=0; i<M; i++) {
			int a = nextInt()-1;
			int b = nextInt()-1;
			// 비트마스크 연산으로 추가.
			conflicts[a] |= 1<<b;
			conflicts[b] |= 1<<a;
		}
		combCount =1;
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}