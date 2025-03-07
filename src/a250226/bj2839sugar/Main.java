package a250226.bj2839sugar;

import java.io.*;
/* BaekJoon 2839 SUGAR
 *
 * 8 이상의 자연수는 모두 3a+5b 형태로 나타낼 수 있다.
 * N%5 = 1인 경우
 * N%5 = 2인 경우 
 * N%5 = 3인 경우 5*(N/5) + 3*1 
 * N%4 = 4인 경우..
 */


public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static int N;
	
	public static void main(String[] args) throws IOException {
		N = nextInt();
		// 불가능한 오직 두가지 경우의 수
		if(N==4 || N==7) {
			System.out.println("-1");
			return;
		}
		
		
		
		int remainder = N%5;
		int count=N/5;
		if(remainder == 1 || remainder == 3) {
			count += 1;
		} else if(remainder == 2 || remainder == 4) {
			count += 2;
		}
		System.out.println(count);
		
	}
	
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int)st.nval;
	}

}
