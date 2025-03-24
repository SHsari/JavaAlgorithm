package personal.permutation;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();
	static int N, M;
	
	static int[] array;
	static int[] selected;
	
	public static void main(String[] args) throws IOException {
		StringTokenizer nm = new StringTokenizer(br.readLine());
		N = Integer.parseInt(nm.nextToken());
		M = Integer.parseInt(nm.nextToken());
		array = new int[N];
		StringTokenizer line = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			array[i] = Integer.parseInt(line.nextToken());
		}
		selected = new int[M];

		qSort(0, N-1);

		
		perm(M, 0);
		
		System.out.println(result);
	}
	
	static void perm(int toSelect, int visited) {
		if(toSelect==0) {
			for(int num : selected) {
				result.append(num);
				result.append(" ");
			}
			result.append("\n");
			return;
		}
		
		for(int i=0; i<N; i++) {
			if((visited & (1 << i)) == 0) {
				selected[M-toSelect] = array[i];	
				perm(toSelect-1, visited | (1<<i));
			}
		}
	}
	
	static void qSort(int start, int end) {
		if(start>=end) return;
		
		int poleIdx = (int)(Math.random() * (end-start+1)) + start;
		int pole = array[poleIdx];
		swap(poleIdx, end);
		
		int bound = start;
		
		for(int i=start; i<end; i++) {
			if(array[i]<pole) {
				swap(i, bound++);
			}
		}
		
		swap(bound, end);
		qSort(start, bound-1);
		qSort(bound+1, end);
	}
	
	static void swap(int idx1, int idx2) {
		int tmp = array[idx1];
		array[idx1] = array[idx2];
		array[idx2] = tmp;
	}

}
