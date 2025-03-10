import java.io.*;


public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			
			
			
			
			
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}