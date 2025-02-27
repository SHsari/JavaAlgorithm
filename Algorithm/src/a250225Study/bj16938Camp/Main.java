package a250225Study.bj16938Camp;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder result = new StringBuilder();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int N, L, R, X;
    static int count;
    static int[] arr;
    static final int LEAST = 2;
    public static void main(String[] args) throws IOException{
        StringTokenizer nlrx = new StringTokenizer(br.readLine());

        N = Integer.parseInt(nlrx.nextToken());
        L = Integer.parseInt(nlrx.nextToken());
        R = Integer.parseInt(nlrx.nextToken());
        X = Integer.parseInt(nlrx.nextToken());

        arr = new int[N];
        StringTokenizer arrLine = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(arrLine.nextToken());
        }
        // 입력완료

        count =0;
        combination(0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
        System.out.println(count);
    }

    static void combination(int depth, int start, int min, int max, int sum) {

        for(int i=start; i<=N-(LEAST-depth); i++) {

            if(min > arr[i]) min = arr[i];
            if(max < arr[i]) max = arr[i];
            sum += arr[i];

            // 만족하는 경우 실패하는
            if(sum >= R && sum <= L) {
                if(depth >= LEAST && max-min >= X) {
                    count++;
                }
            }
            combination(depth+1, i+1, min, max, sum);
        }
    }
}
