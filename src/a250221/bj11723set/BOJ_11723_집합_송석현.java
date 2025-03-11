package a250221.bj11723set;

import java.io.*;
import java.util.Set;
import java.util.HashSet;

/* 
 * BOJ 11723 집합
 * hashSet을 이용해서 Set을 구현했습니다.
 * 
 * 
 * solve2() 함수를 통해서 비트연산으로 구현하겠습ㄴ다.
 */


public class BOJ_11723_집합_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
	
	// 정해진 명령어들
	static final String ADD = "add",
			REMOVE = "remove",
			CHECK = "check",
			TOGGLE = "toggle",
			ALL = "all",
			EMPTY = "empty";
	static final int ALLINT = 0b111111111111111111110;
	
	public static void main(String[] args) throws IOException {
			solve2();
	}

	static void solve2() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        //명령어의 갯수를 받습니다.
        int N = nextInt();
        // 사용할 set 쎄또
        int bitmask = 0;
        
        StringBuilder result = new StringBuilder();
        
        for(int i=0; i<N; i++) {
        	String command = nextWord();
        	
        	if(command.equals(ADD)) {
        		bitmask |= 1 << nextInt();
        	} else if(command.equals(REMOVE)) {
        		bitmask &= ~(1 << nextInt());
        	} else if(command.equals(CHECK)) {
        		String isIn = (bitmask & (1 << nextInt())) == 0? "0" : "1";
        		result.append(isIn).append("\n");
        	} else if(command.equals(TOGGLE)) {
        		int num = 1 << nextInt();
        		if((bitmask & num) != 0) bitmask &= ~num;
        		else bitmask |= num;
        	} else if(command.equals(ALL)) {
        		bitmask = ALLINT;
        	} else if(command.equals(EMPTY)) {
        		bitmask = 0;
        	}
        }
        System.out.println(result);
	}
	
	
	static void solve() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        
        //명령어의 갯수를 받습니다.
        int N = nextInt();
        // 사용할 set 쎄또
        Set<Integer> setto = new HashSet<>();
        
        StringBuilder result = new StringBuilder();
        
        for(int i=0; i<N; i++) {
        	String command = nextWord();
        	
        	if(command.equals(ADD)) {
        		setto.add(nextInt());
        	} else if(command.equals(REMOVE)) {
        		setto.remove(nextInt());
        	} else if(command.equals(CHECK)) {
        		String isIn = setto.contains(nextInt())? "1" : "0";
        		result.append(isIn).append("\n");
        	} else if(command.equals(TOGGLE)) {
        		int num = nextInt();
        		if(setto.contains(num)) setto.remove(num);
        		else setto.add(num);
        	} else if(command.equals(ALL)) {
        		setto = new HashSet<>();
        		for(int num=1; num<=20; num++) {
        			setto.add(num);
        		}
        	} else if(command.equals(EMPTY)) {
        		setto = new HashSet<>();
        	}
        }
        System.out.println(result);
	}
	
	static String nextWord() throws IOException {
		st.nextToken();
		return st.sval;
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}