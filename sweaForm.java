import java.io.*;


public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;


	// N은 주어지는 배열의 길이
	static int N;
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			N = nextInt();
			
			
			
			
			
		}
			
		System.out.println(result);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}