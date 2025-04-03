package a250403.boj1463mk1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

/*
 * bOJ 1463 1로 만들기
 * 
 * N을 다음 연산을 통해 1로 만듭니다.
 * 3으로 나눠 떨어질 경우 1/3
 * 2로 나눠 떨어질 경우 1/2
 * 또는  N-1
 * 
 * 1부터 N까지 순회하며 
 * dp[i-1] / dp[i/2] / dp[i/3] 중 최솟값을 고릅니다.
 * 
 * 위의 최솟값에서 +1한 값을 dp[i]로 지정.
 * 
 */
public class Main {

	static BufferedReader br;
	static StreamTokenizer st;
	static int N;
	static int[] memo;
	
	public static void main(String[] args) throws IOException {
		init();
		
		if(N<=3){
			System.out.println(N/2);
			return;
		}
		
		memo[1] = 0;
		memo[2] = 1;
		memo[3] = 1;
		
		for(int n=4; n<=N; n++) {
			if(n%3==0) {
				memo[n] = memo[n/3] + 1;
			}
			if(n%2==0) {
				memo[n] = Math.min(memo[n], memo[n/2]+1);
			}
			memo[n] = Math.min(memo[n], memo[n-1]+1);
		}
		
		System.out.println(memo[N]);

	}

	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        N = nextInt();
        memo = new int[N+1];
        Arrays.fill(memo,  Integer.MAX_VALUE);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}