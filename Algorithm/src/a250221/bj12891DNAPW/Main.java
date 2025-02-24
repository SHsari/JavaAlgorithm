package a250221.bj12891DNAPW;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static int S, P;
	static final Map<Character, Integer> DNAIDX = Map.of('A', 1, 'C', 2, 'G', 3, 'T', 4);
	
	public static void main(String[] args) throws IOException {
		StringTokenizer sp = new StringTokenizer(br.readLine());
		
		Map<Character, Integer> dnaMap = new HashMap<>();

		
		S = Integer.parseInt(sp.nextToken());
		P = Integer.parseInt(sp.nextToken());
		
		String line = br.readLine();
		for(int i=0; i<S; i++) {
			char c = line.charAt(i);
			dnaMap.put(c, dnaMap.getOrDefault(c, 0)+1);
		}
		
		

	}

}
