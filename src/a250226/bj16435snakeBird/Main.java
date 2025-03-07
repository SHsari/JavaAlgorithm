package a250226.bj16435snakeBird;

import java.io.*;
import java.util.Arrays;

/* Baekjoon 16435 SnakeBird
 * 
 * 새가 먹이를 먹으면 키가 1 자란다.
 * 먹이마다 높이가 다른데, 
 * 새의 키가 먹이보다 높거나 같아야 먹이를 먹을 수 있다.
 * 
 * 먹이들의 높이가 배열로 주어질 때 가능한 최대의 먹이를 먹은 새의 키높이를 구하라
 * 
 * 먹이들의 높이를 오름차순으로 정렬 
 * 정렬된 배열을 순회하며 새의 키높이와 비교
 */

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

		// 먹이들을 높이기준 오름차순으로 정렬
		Arrays.sort(list);

		// 배열 좌측부터 순회하며 새와 비교.
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
