import java.io.*;


public class Main {

	static BufferedReader br;
	static StreamTokenizer st;
	
	public static void main(String[] args) throws IOException {

			

	}

	static void init() {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}