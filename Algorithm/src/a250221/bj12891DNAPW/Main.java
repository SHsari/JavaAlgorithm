package a250221.bj12891DNAPW;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static int S, P;
	static final char[] charList = {'A','C','T','G'};
	
	public static void main(String[] args) throws IOException {
		StringTokenizer sp = new StringTokenizer(br.readLine());
		
		Map<Character, Integer> dnaMap = new HashMap<>();
		for(char c : charList) {
			dnaMap.put(c, 0);
		}
		
		S = Integer.parseInt(sp.nextToken());
		P = Integer.parseInt(sp.nextToken());
		
		String line = br.readLine();
		for(int i=0; i<S; i++) {
			char c = line.charAt(i);
			dnaMap.put(c, dnaMap.getOrDefault(c, 0)+1);
		}
		
		

	}

}
