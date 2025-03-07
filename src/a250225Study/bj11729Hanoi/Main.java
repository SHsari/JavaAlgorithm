package a250225Study.bj11729Hanoi;

import java.io.*;

public class Main {
    static StringBuilder result = new StringBuilder();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());
        int count = (int)(Math.pow(2, N)-1);
        result.append(count).append("\n");
        hanoi(N, 1, 3);

        System.out.println(result);
    }

    static void hanoi(int N, int from, int to) {
        if(N==0){return;}

        int nextTo = 6-from-to;
        hanoi(N-1, from, nextTo);
        result.append(from).append(" ").append(to).append("\n");
        hanoi(N-1, nextTo, to);

    }

}
