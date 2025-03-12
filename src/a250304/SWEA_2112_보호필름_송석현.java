package a250304;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

/* SWEA 2112 보호필름
 * 문제가 어떤 도메인에 있는 문제인지 짐작도 안가는 이기분..
 * 부분집합인가..?
 * 매번 경우의 수 마다 확인해야 하나....?
 * 
 * 
 * 3 <= D <= 13
 * 1 <= W <= 20
 * 1 <= K <= D // 강도
 * 
 * 약품 투입위치 * 약품종류 * 검사시간
 * DCn * 2^n * W*D
 * 13C6 = 1716
 * 2^20 = 2048
 * 20*13 = 260
 * 
 * 913735680
 * ~9*10^8
 * 
 * 달 뤼자.
 */


public class SWEA_2112_보호필름_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	/*
	 * D: depth
	 * W: width
	 * K: minimum Strength
	 * film OG는 처음 주어진 필름의 형상을 기록해놓는 변수
	 * film New 직접 약품 넣고 검사할때 사용하는 변수
	 * 
	 * selectedRow는 약품을 넣을 row 배열을 저장하는 변수
	 * toSelect는 selectedRow 배열을 순회 및 결정할 때
	 * 몇개의 row를 선택할 지 확인하는 변수이며 0부터 K까지 순회하면서 
	 * 약품 투입 row의 조합을 찾으므로 반복이 멈추는 toSelect값이 곧 답이된다.
	 * testres
	 */
	
	static int D, W, K;
	static boolean[][] filmOG;
	static boolean[][] filmNew;
	static int[] selectedRow;
	static int toSelect;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
		
			init();
			
			if(K==1) {
				result.append(0).append("\n");
				continue;
			}
			
			boolean testResult=false;
			for(toSelect=0; toSelect<K; toSelect++) {
				
				//약품 투입 횟수를 1씩 늘려가며 순회
				
				// selectedRow를 0, 1, 2  이런식으로
				// 최초의 조합으로 셋팅.
				initSelect();	
				do {
					// selectedRow에 순서대로 어떤 약품을 넣을 지 모든 
					// 경우의 수를 순회하는 함수 true 리턴시 조건 만족하는 경우를 발견했다는 것이다.
					if(putDrugRecursion(0)) {
						testResult =true;
						break;
					}
					// nextSelect는 다음 selectedRow의 컴비네이션을 만들어준다.
					// nCm에서 최종 m개의 조합에 도달했을 경우 false return;
				} while(nextSelect());
				if(testResult) break;
			}
			
			result.append(toSelect).append("\n");
		}
			
		System.out.println(result);
	}

	/*
	 * 부분집합 함수입니다
	 * 약품의 2종류 이므로
	 * 약품투입이 정해진 Row에 대해서
	 * A약품을 넣는 경우,B를 넣는 경우에 대해서 DFS 하는 함수입니다.
	 * 
	 */
	static boolean putDrugRecursion(int depth) {
		if(depth == toSelect) {
			
			//print();
			if(qcTest()) {

				return true;
			}
			return false;
		}
		
		int row = selectedRow[depth];
		
		Arrays.fill(filmNew[row], true);
		if(putDrugRecursion(depth+1)) return true;
		
		Arrays.fill(filmNew[row], false);
		if(putDrugRecursion(depth+1)) return true;
		
		filmNew[row] = filmOG[row].clone();
		
		return false;
	}
	
	/*
	 * nCm 상황에서 m이 늘어나며 순회하는데,
	 * 
	 * 처음 m이 바뀌었을 때, selectedRow 배열을 m사이즈에 맞게 초기화하고
	 * m==3일 경우
	 * 0,1,2 이런식으로 처음 조합을 만들어줍니다!
	 */
	
	static void initSelect() {
		selectedRow = new int[toSelect];
		for(int i=0; i<toSelect; i++) {
			selectedRow[i] = i;
		}
	}
	
	/*
	 * nextCombination!
	 * 약품 투입할 row를 결정하는 함수.
	 * 근데 한번 호출할때마다 다음 조합으로 iterate 합니다!
	 */
	static boolean nextSelect() {
		for(int sub=1; sub<=toSelect; sub++) {
			int nth = toSelect-sub;
			int limit = D-sub;
			
			if(selectedRow[nth] < limit) {
				selectedRow[nth]++;
				nth++;
				for(; nth<toSelect; nth++) {
					selectedRow[nth] = selectedRow[nth-1] +1;
				}
				
				return true;
			}
		} 
		return false;
	}
	
	
	/*
	 * 강도 테스트 하는 함수!
	 * width(가로)의 index별로 진행하는 함수를 따로 만들었습니다.
	 * 로직은 간단합니다.
	 * width에 대해서 index를 순회하면서 해당 인덱스가 검사를 통과하는지 확인합니다.
	 */
	static boolean qcTest() {
		for(int wIdx=0; wIdx<W; wIdx++) {
			if(!qcTestAtIndex(wIdx)) {
				return false;
			}
		}
		return true;
	}
	/*
	 * 가로축에서 특정 index의 강도검사 통과 여부를 반환하는 함수
	 * qcTest에서 결과판단을 편하게 하기위해 함수를 분리했습니다.
	 */
	private static boolean qcTestAtIndex(int index) {
		int serialCount=1;
		boolean preValue = filmNew[0][index];
		
		for(int depth=1; depth<D; depth++) {
			if(filmNew[depth][index] == preValue) {
				if(++serialCount == K) return true;
			}else {
				preValue = !preValue;
				serialCount =1;
			}
		}
		return false;
	}

	
	static void init() throws IOException {
		D = nextInt();
		W = nextInt();
		K = nextInt();
		
		filmOG = new boolean[D][W];
		filmNew = new boolean[D][W];
		
		for(int depth=0; depth<D; depth++) {
			for(int wNum=0; wNum<W; wNum++) {
				filmOG[depth][wNum] = nextInt() == 1;
			}
			filmNew[depth] = filmOG[depth].clone();
		}
	}
	
	/*
	 * 디버깅을 위한 프린트함수
	 */
	static void print() {
		StringBuilder sb = new StringBuilder();
		sb.append("selectedRow Num: " ).append(toSelect);
		sb.append("\n").append(Arrays.toString(selectedRow));
		
		for(int i=0; i<D; i++) {
			sb.append("\n").append(Arrays.toString(filmNew[i]));
		}
		
		System.out.println(sb);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}