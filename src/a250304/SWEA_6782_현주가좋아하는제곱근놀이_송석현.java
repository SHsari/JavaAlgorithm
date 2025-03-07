package a250304;


/*
 * SWEA 6782 현주는 왜 제곱근 놀이를 좋아할까
 * 
 * N이 주어지면
 * 1. N+1
 * 2. 제곱근 N이 자연수일 때 sqrt(N)
 * 
 * 두가지 연산만을 통해서 N을 차차 2로 줄여나간다.
 * N이 제곱수가 아닌 경우 무조건 1번의 선택지 밖에 없으므로
 * 
 * N보다 큰 가장 작은 제곱수를 구하고
 * 제곱근을 취하고 하는 과정을 반복한다.
 * 
 * 
 * 메모리는 256MB
 * 
 * 10^12 까지 모든 경우의 수를 모두 구해놓기엔 메모리가 부족하기 때문에
 * 10^6 까지 구해놓고, 초과하는 값들은 한번의 sqrt만을 이용해서 정답에 접근할 수 있도록 한다.
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class SWEA_6782_현주가좋아하는제곱근놀이_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();
	
	//10^3의 제곱수까지 미리 값을 구해놓는다.
	static final int MAXN = (int)Math.pow(10, 3);
	
	static int[] memo = new int[MAXN*MAXN+1];
	static { memo[2] = 0; }

	// N은 주어지는 배열의 길이
	static long N;
	
	public static void main(String[] args) throws IOException {
		
		int oldN2 = 2;
		//MAXN^2 즉 10^6까지의 결과값을 미리 생성한다.
		// n-1의 제곱과 n제곱 사이의 정수에 대해서 제곱근 놀이 값을 구한다.
		for(int n=2; n<=MAXN; n++) {
			int n2 = n*n;
			memo[n2] = memo[n] + 1;
			for(int iterN=oldN2+1; iterN<n2; iterN++) {
				memo[iterN] = memo[n2]+n2-iterN;
			}
			oldN2 = n2;
		}
		
		
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			N = nextLong();
			
			//N이 미리 구해놓은 범위에 있다면
			if(MAXN*MAXN>=N) {
				result.append(memo[(int)N]).append("\n");
				continue;
			}
			
			
			// 아니라면
			long sqrtN = (long)Math.ceil(Math.sqrt(N));
			int count = memo[(int)sqrtN] + (int)(sqrtN*sqrtN-N) +1;
			
			result.append(count).append("\n");
		}
			
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
	
	static long nextLong() throws IOException {
		st.nextToken();
		return (long) st.nval;
	}

}