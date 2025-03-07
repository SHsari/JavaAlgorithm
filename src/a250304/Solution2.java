package a250304;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/* SWEA 2112 보호필름
 * 문제가 어떤 도메인에 있는 문제인지 짐작도 안가는 이기분..
 * 부분집합인가..?
 * 매번 경우의 수 마다 확인해야 하나....?
 * 
 * 
 * 3 <= D <= 13
 * 1 <= W <= 20
 * 1 <= K <= D
 * 
 */


public class Solution2 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static StringBuilder result = new StringBuilder();

	static int D, W, K;
	static int[][] film;
	
	public static void main(String[] args) throws IOException {
		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append("tc").append(" ");
			
			D = nextInt();
			W = nextInt();
			K = nextInt();
			
			film = new int[D][K];
			
			for(int depth=0; depth<D; depth++) {
				for(int wNum=0; wNum<W; wNum++) {
					
					
				}
			}
			
		}
			
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}