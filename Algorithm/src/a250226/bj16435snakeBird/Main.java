package a250226.bj16435snakeBird;

import java.io.*;
import java.util.Arrays;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StreamTokenizer st = new StreamTokenizer(br);
	static int N;
	static int[] list;
	public static void main(String[] args) throws IOException {
		N = nextInt();
		int length = nextInt();
		list = new int[N];
		
		for(int i=0; i<N; i++) {
			list[i] = nextInt();
		}
		Arrays.sort(list);
		for(int height : list) {
			if(height<=length) length++;
		}
		System.out.println(length);
	}
	
	

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int)st.nval;
	}

}
