package a250226.swea3796mountain;

import java.io.*;


public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	static int N;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			N = nextInt();

			int localMinPoint = -1;
			int localMaxPoint = -1;
			
			int iBound=0, jBound=0;
			
			int sum =0;
			
			// for문 실행 전 localMinPoint를 찾기 위한 코드
			int old = Integer.MAX_VALUE;
			int now = nextInt();
			int idx = 0;
			while(old>=now && ++idx<N) {
				old = now;
				now = nextInt();
			}
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