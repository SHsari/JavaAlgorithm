package a250226.swea3796mountain;

/* SWEA 3796 Mountain
 * 
 * 배열이 주어졌을 때,
 * 
 * 지역 최대값과 지역 최솟값의 위치를 기억하는 문제이다.
 * 
 * 가능한 구간의 경우의수.. 음..
 * 
 */
import java.io.*;


public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();


	// N은 주어지는 배열의 길이
	static int N;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			N = nextInt();

			// 배열을 순회하면서 직전에 발견한 local MinPoint와 Max Point를 저장할 변수
			int localMinPoint = -1;
			int localMaxPoint = -1;
			
			// 직전에 발견한 지역 최대, 최솟값의 index를 통해
			// 오르는 구간, 내려가는 구간의 너비를 저장할 변수
			int iBound=0, jBound=0;
			
			// 구간들을 이용해 구한 경우의 수의 합(결과값)
			int sum =0;
			
			
			// old는 배열 순회 시 직전값.
			// now 는 배열 순회 시 현재값.
			int old = Integer.MAX_VALUE;
			int now = nextInt();
			int idx = 0;

			// for문 실행 전 일단 localMinPoint를 찾기 위한 코드
			while(old>=now && ++idx<N) {
				old = now;
				now = nextInt();
			}
			// 현재 오르막인지, 내리막인지를 알아야 지역최대, 최소를 알 수 있다.
			// 바보같은 컴퓨터
			boolean isAscending = true;
			localMinPoint = idx-1;
			old = now;
			idx++;
			for(; idx<N; idx++) {
				//System.out.println(idx);
				now = nextInt();
				if(isAscending) {
					if(old>now) {
						isAscending = false;
						localMaxPoint = idx-1;
						iBound = localMaxPoint-localMinPoint;
						//System.out.println("max: " + (idx-1));
					}
				} else {
					if(old<now) {
						isAscending = true;
						localMinPoint = idx-1;
						jBound = localMinPoint-localMaxPoint;
						sum+=iBound*jBound;
						//System.out.println("min: " + (idx-1));
					}
				}
				old = now;
			}
			// 마지막 인덱스가 내리막인 경우.
			// 직전 지역 최댓값이 결과에 반영되지 않기 때문에 따로 코드가 필요하다
			// 일반적인 경우와 인덱스 1차이가 나서 걍 밖으로 빼버렸다.
			if(!isAscending) {
				localMinPoint = N-1;
				jBound = localMinPoint-localMaxPoint;
				sum+=iBound*jBound;
			}
			
			
			result.append("#").append(tc).append(" ");
			result.append(sum).append("\n");
		}
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}