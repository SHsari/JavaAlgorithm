package a250227.bj16987eggegg;

import java.io.*;

/* 백준 16987 계란으로 계란치기
 * 아솨라비ㅏㅇ
 * 
 */

public class Main {

	static BufferedReader br;
	static StreamTokenizer st;
	static int N;
	static int[] str, weight;
	static int maxCount;
	
	public static void main(String[] args) throws IOException {

			init();
			bfDFS(0, 0);
			System.out.println(maxCount);

	}
	
	static boolean bfDFS(int depth, int count) {
		if(depth==N) {
			updateMax(count);
			return false;
		}
		else if(str[depth] <= 0) {
			if(bfDFS(depth+1, count)) return true;
		}
		else {
			for(int egg=0; egg<N; egg++) {
				if(egg != depth && str[egg]>0) {
					int nextCount = count;
					str[egg] -= weight[depth];
					str[depth] -= weight[egg];
					if(str[egg]<=0) nextCount++;
					if(str[depth]<=0) nextCount++;
					if(nextCount==N) {
						updateMax(count);
						return true;
					}
					else if(bfDFS(depth+1, nextCount)) return true;
				}
			}
		}
		return false;
	}
	
	private static void updateMax(int newCnt) {
		if(newCnt>maxCount) maxCount=newCnt;
	}
	
	static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        maxCount =0;
        N = nextInt();
        str = new int[N];
        weight = new int[N];
        for(int i=0; i<N; i++) {
        	str[i] = nextInt();
        	weight[i] = nextInt();
        }
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}