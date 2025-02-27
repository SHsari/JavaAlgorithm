package a250225Study.bj6603Lotto;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder result = new StringBuilder();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int K;
    static int[] selected, arr;
	public static void main(String[] args) throws IOException{

        StringTokenizer line = new StringTokenizer(br.readLine());
        K = Integer.parseInt(line.nextToken());
        selected = new int[6];
        while(K!=0) {
            arr = new int[K];
            for(int i=0; i<K; i++) {
                arr[i] = Integer.parseInt(line.nextToken());
            }
            //한줄 입력 완료

            combination(6, 0);
            result.append("\n");

            line = new StringTokenizer(br.readLine());
            K = Integer.parseInt(line.nextToken());
        }

        System.out.println(result);
    }


    static void combination(int toSelect, int start) {
        if(toSelect == 0) {
            for(int i : selected) {
                result.append(i).append(" ");
            }
            result.append("\n");
            return;
        }

        int depth = 6-toSelect;
        for(int i=start; i<=K-toSelect; i++) {
            selected[depth] = arr[i];
            combination(toSelect-1, i+1);
        }
    }
}
