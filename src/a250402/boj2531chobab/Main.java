package a250402.boj2531chobab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Map;
import java.util.HashMap;


public class Main {
	/*
	 * B형 인기검색어 문제가 떠오르는ㄷ...
	 * 최근 N개 검색어에 대한 Map을 구성했었는데..
	 * 
	 * Map(Set)과 슬라이딩 윈도우를 사용했습니다.
	 * 
	 * N개의 회전하는 그릇에 대해서 
	 * 0부터 N-1까지의 모든 점을 시작점으로 하여 K개 그릇에 대한 초밥종류를 확인합니다.
	 * 맵은 다음과 같습니다.
	 * Map<초밥종류, 해당 초밥의 갯수>
	 * 
	 * 따라서 사이즈 K의 윈도우 안에, 어떤 종류의 초밥이 몇개 들어있는지 Map을 통해 알 수 있습니다.
	 * 초밥 종류의 갯수는 맵의 사이즈입니다.
	 * 
	 */

	static BufferedReader br;
	static StreamTokenizer st;
	
	// maxTypeNum: d
	// windowSize: k
	static int N, maxTypeNum, windowSize;
	
	// 초밥 타입의 범위가 2<= maxTypeNum <=3000 (문제에서 d)
	// 따라서 char로 선언합니다.
	static char eventChobab;
	static char[] belt;
	static Map<Character, Integer> selected;
	
	public static void main(String[] args) throws IOException {
		
		init();
		
		int maxSetSize=0;
		
		//일단 첫 번째 슬라이딩 윈도우를 셋팅합니다.
		setFirstSet();
		
		// 첫번째 슬라이딩 윈도우의 시작 인덱스는 0입니다.
		int startPoint =0;
		
		do {
			// 먹을 수 있는 종류의 갯수 구하기.
			int currentSize = selected.size();
			if(!selected.containsKey(eventChobab)) currentSize++;
			
			// max값 업데이트
			if(currentSize > maxSetSize) maxSetSize=currentSize;
			
			// 슬라이딩 윈도우 한칸 옮기기.
			nextSet(startPoint++);
		} while(startPoint<N);
		System.out.println(maxSetSize);

	}
	
	// 슬라이딩 윈도우 한칸 이동 함수.
	static void nextSet(int startPoint) {
		remove1At(belt[startPoint]);
		int nextChobab = (startPoint + windowSize) % N;
		add1At(belt[nextChobab]);
	}
	
	// 첫번째 슬라이딩 윈도우 셋팅.
	static void setFirstSet() {
		for(int i=0; i<windowSize; i++) {
			add1At(belt[i]);
		}
	}
	
	// 맵에 특정 초밥을 하나 빼줍니다.
	// 해당 초밥의 갯수가 1 이면 맵에서 아예 제거,
	// 1보다 클경우 갯수-1 을 다시 저장합니다.
	static void remove1At(char key) {
		int num = selected.get(key);
		if(num == 1) {
			selected.remove(key);
		} else selected.put(key, num-1);
	}
	
	// 맵에 특정 초밥을 추가합니다.
	// -> 새로 넣거나, 해당 초밥이 이미 있으면 갯수++ 을 해줍니다.
	static void add1At(char key) {
		if(!selected.containsKey(key)) {
			selected.put(key, 1);
		} else {
			int num = selected.get(key);
			selected.put(key, num+1);
		}
	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        N = nextInt();
        maxTypeNum = nextInt();
        windowSize = nextInt();
        eventChobab = (char) nextInt();
        belt = new char[N];
        
        selected = new HashMap<>();
        
        for(int i=0; i<N; i++) {
        	belt[i] = (char)nextInt();
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}