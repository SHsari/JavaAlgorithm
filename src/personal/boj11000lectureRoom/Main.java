package personal.boj11000lectureRoom;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

public class Main {
    
    static BufferedReader br;
    static StreamTokenizer st;

    static int N;
    static List<Lecture> lectures;

    static boolean didAssign[];

    public static void main(String[] args) throws IOException {
        init();
        Collections.sort(lectures);

        int time = lectures.get(0).st;

        Queue<Lecture> simultaneous = new ArrayDeque<>();
        
    }

    static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);

        st.nextToken();
        N = (int) st.nval;
        lectures = new LinkedList<>();
        didAssign = new boolean[N];

        for(int i=0; i<N; i++) {
            st.nextToken();
            int s = (int) st.nval;
            st.nextToken();
            int e = (int) st.nval;

            lectures.add(new Lecture(s, e));
        }
    }

    static class Lecture implements Comparable<Lecture> {
        int st, end;

        Lecture(int st, int end) {
            this.st = st;
            this.end = end;
        }

        @Override
        public int compareTo(Lecture o) {
            return this.st - o.st;
        }
    }
}

