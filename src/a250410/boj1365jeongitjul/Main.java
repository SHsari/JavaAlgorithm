package a250410.boj1365jeongitjul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


public class Main {
	/* 
	 * LIS 문제였는데, 파악이 안돼서 인접리스트로 어떻게든 풀어볼라고 했다.
	 * Node Class와 해당 클래스 안에 멤버로 인접 리스트 선언하여 (인접: 겹침)
	 * 인접한 노드들이 많은것부터 정렬하여 한개씩 지우는 형태를 취했는데, 
	 * 이 방식은 분명히 정답을 도출하겠지만 메모리 초과가 났다.
	 * 
	 * 두번째 방식은 cross되는 접점의 갯수만을 가지고 풀 수 있을까 하여 시도했다.
	 * i번째 선이 몇개의 다른 선과 cross 되는지는 순차적으로 입력을 받으며
	 * 1부터 i-1까지의 선의 종점위치를 확인하여 정확히 Count 할 수 있다.
	 * 그러나 Cross의 전체 합과 각자가 cross된 횟수만을 이용해서
	 * 몇개의 선(node)들을 삭제해야하는 지 정확히 알 수없다.
	 * 
	 * 
	 * LIS 방식을 도입해서 O(N^2)으로 풀었는데 65%에서 시간초과남.
	 * => 로직을 미세하게 수정했더니 통과,
	 * => binarySearch와 LinearSearch 모두 구현
	 * 
	 */
	static BufferedReader br;
	static StreamTokenizer st;
	
	public static void main(String[] args) throws IOException {
		int minDelete = solve();
		
		System.out.println(minDelete);
	}

	static int solve() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        int N = nextInt();
        int array[] = new int[N+1];
        int minEndAtLength[] = new int[N+1];
        
        Arrays.fill(minEndAtLength, Integer.MAX_VALUE);
        int maxLength=1;
        for(int i=1; i<=N; i++) {
        	array[i]=nextInt();

        	int localMax = binarySearch(array[i], minEndAtLength, maxLength);
        	maxLength = Math.max(maxLength, localMax);
        }
        
        return N-maxLength;
	}
	
	// 이분탐색.
	static int binarySearch(int lastValue, int[] d, int maxLength) {
		// lastValue 보다 크지 않은 최대값을 찾는다.
		int hi = maxLength, lo = 1;
		int maxIndex =0;
		// while문 조건 아주 의심스럽다.
		while(hi>=lo) {
			int testIdx = (hi+lo) /2;
			if(d[testIdx] < lastValue) {
				maxIndex = testIdx;
				lo = testIdx+1;
			} else {
				hi = testIdx-1;
			}
		}
		d[++maxIndex] = lastValue;
		return maxIndex;
	}
	
	// 기본 선형탐색
	static int linearSearch(int lastValue, int[] d, int maxLength) {
		// d[] 는 정렬된 배열이란 것이 특징이다.
		// lastValue보다 작은 최대 index를 찾는다.
		int localMaxLen = 0;
		for(int len=maxLength; len>0; len--) {
			if(d[len] < lastValue) {
				localMaxLen = len + 1;
				break;
			}
		}
		d[localMaxLen] = lastValue;
		return localMaxLen;
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}