import java.io.*;


public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	public static void main(String[] args) {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			
			
			
			
			
		}
	}

	
	static int nextInt() {
		try { st.nextToken(); }
		catch(IOException e) { e.printStackTrace(); }
		return (int) st.nval;
	}

}