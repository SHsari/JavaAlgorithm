package a250220.swea1218;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class Solution {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	private static final Map<Character, Character> BracketPairs = new HashMap<>();

	static {
		BracketPairs.put('}', '{');
		BracketPairs.put(']', '[');
		BracketPairs.put(')', '(');
		BracketPairs.put('>', '<');
	}
	
	static Stack<Character> stack = new Stack<>();
	
	public static void main(String[] args) throws IOException{
		final int T =10;
		for(int tc=1; tc<=T; tc++) {
			int isValid =1;
			sb.append("#" + tc + " ");
			
			int length = Integer.parseInt(br.readLine());
			char[] line = br.readLine().toCharArray();
			
			for(int i=0; i<length; i++) {
				if(BracketPairs.containsValue(line[i])) {
					stack.push(line[i]);
				}
				else if(BracketPairs.containsKey(line[i])) {
					if (stack.isEmpty() || stack.pop() != BracketPairs.get(line[i])) {
						isValid = 0;
						break;
					}
				}
			}
			sb.append(isValid + "\n");
				
		}
		System.out.println(sb);
	}

}
