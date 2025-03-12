package a250304;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/* 백준 쿼드트리.
 * N은 정사각형 맵의 항 변의 길이이며 항상 2의 제곱수로 주어진다.
 * 1 <= N <= 64 
 *
 * 재귀방식으로 구현시 깊이가 8 또는 9 까지 나올 것 같다.
 * 
 */

public class BOJ_1992_쿼드트리_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int N;
	static char[][] map;
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		
		for(int i=0; i<N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		String res = quadTree(N, 0, 0);			
		System.out.println(res);
	}
	
	
	/*	
	 * 일단 재귀로 바닥깊이까지 돌립니다.
	 * 체크해야할 문자열의 길이가 1이 되면, 해당 위치의 문자를 문자열로 반환합니다.
	 * 위층에서 재귀 실행한 자식들의 결과값을 비교합니다.
	 * 자식들의 문자열 길이가 모두 1이고 동일하다면 압축하여 반환하고
	 * 그렇지 않을 경우 자식들을 모두 합쳐서 괄호로 감싸 반환합니다.
	 * 
	 * 
	 * 실수한부분:
	 * isMergeable = true
	 * 위를 기본값으로 자식들의 리턴값을 체크하는데, 
	 * if(isMergeable && children[i].length() ==1) 
	 * 		isMergeable = children[i].equals(children[0]);
	 * 
	 * 위와 같은 로직을 사용했습니다. 그러나
	 * 위의 경우 children[i]의 length가 모두 1이 아닌 경우에
	 * isMergeable의 값을 false로 변경하지 않아서 추후 잘못 압축된 값을 반환하는 만행이 존재했습니다.
	 * 
	 */
	
	static String quadTree(int n, int row, int col) {
		
		StringBuilder result = new StringBuilder();
		
		if(n==1) {return result.append(map[row][col]).toString(); }
		
		int nextN = n/2;
		//for문으로 자식함수들을 실행하기 위한 next Row, Column Array들
		int[] rowArr = {row, row, row+nextN, row+nextN};
		int[] colArr = {col, col+nextN, col, col+nextN};
		
		// 자식함수들의 리턴값을 받을 String 어레이
		String[] children = new String[4];
		
		// 자식함수의 결과값을 통해 머지가 가능한 지 여부를 판단하는 boolean
		boolean isMergeable = false;
		
		// 0번 먼저 검사하고
		children[0] = quadTree(nextN, rowArr[0], colArr[0]);
		if(children[0].length() == 1) isMergeable = true;
		
		// 1번부터 for문 돌립니다.
		for(int i=1; i<4; i++) {
			children[i] = quadTree(nextN, rowArr[i], colArr[i]);
			if(isMergeable) {
				if(children[i].length() == 1) {
					isMergeable = children[i].equals(children[0]);
				} else isMergeable = false;
			}
		}
		
		//압축 가능할 때 결과값
		if(isMergeable) return children[0];
		
		//불가능 시 결과값
		else {
			result.append("(");
			for(int i=0; i<4; i++) {
				result.append(children[i]);
			}
			result.append(")");
			return result.toString();
		}

	}

}