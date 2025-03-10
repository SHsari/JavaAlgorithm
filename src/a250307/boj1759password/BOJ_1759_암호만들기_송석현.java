package a250307.boj1759password;
import java.io.*;
import java.util.Arrays;


public class BOJ_1759_암호만들기_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
    static StringBuilder result;
	
    // L은 암호의 길이 length
    // C는 주어지는 Character의 갯수
    static int L, C;
    static char[] charArray;

    static final String VOWEL = "aeiou";

	public static void main(String[] args) throws IOException {
        init();
        generate(0, 0, new StringBuilder());
        System.out.println(result);

    }

    static void generate(int start, int depth, StringBuilder password) {
        if(depth==L) {
            int vowels=0, consonants=0;
            for(int i=0; i<L; i++) {
                char c = password.charAt(i);
                if(VOWEL.indexOf(c) != -1) vowels ++;
                else consonants ++;
            }
            if(vowels>=1 && consonants>=2){
                result.append(password.toString()).append("\n");
            }
            return;
        }

        for(int i=start; i<C; i++) {
            password.append(charArray[i]);
            generate(i+1, depth+1, password);

            password.deleteCharAt(password.length()-1);
        }
    }

    static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
			
        L = nextInt();
        C = nextInt();

        charArray = new char[C];
        for(int i=0; i<C; i++) {
            charArray[i] = nextChar();
        }
        Arrays.sort(charArray);
    }
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

    static char nextChar() throws IOException{
        st.nextToken();
        return st.sval.charAt(0);
    }
}
