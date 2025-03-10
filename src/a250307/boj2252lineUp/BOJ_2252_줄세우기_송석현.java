package a250307.boj2252lineUp;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

/*
 * 30분 고민한 뒤에야 위상 정렬 문제임을 깨달았습니다.
 * 방향그래프가 주어졌는데, 이걸 어떻게 순서대로 효율적으로 출력하지..?
 * 징쟈 신박하네요
 */

public class BOJ_2252_줄세우기_송석현 {

	static BufferedReader br;
	static StreamTokenizer st;
    //N명의 학생들, M은 키를 비교한 횟수.
	static int N, McmpTime;
    static int[] inDegree;
    static List<List<Integer>> nextList;

	public static void main(String[] args) throws IOException {

        init();
        System.out.println(topologyBFS());

	}

    // 왠지 위상정렬은 BFS 를 통한 결과가 더 정직하다고 느끼기 때문에 BFS로 하겠습니다.
    static StringBuilder topologyBFS() {
        StringBuilder sequence = new StringBuilder();
        Queue<Integer> zeroInQ = new ArrayDeque<>();

        for(int node=0; node<N; node++) {
            if(inDegree[node]==0) {
                zeroInQ.add(node);
                sequence.append(node+1).append(" ");
            }
        }

        while(!zeroInQ.isEmpty()) {
            int curNode = zeroInQ.poll(); {
                
                List<Integer> nextNodes = nextList.get(curNode);

                for(int nextNode : nextNodes) {
                    
                    if(--inDegree[nextNode] == 0) {
                        zeroInQ.add(nextNode);
                        sequence.append(nextNode+1).append(" ");
                    }
                }
            }
        }
        
        return sequence;
    }


    // 입력받는 init 함수입니다.
    static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);

        N = nextInt();
        McmpTime = nextInt();
    
        inDegree = new int[N];
        nextList = new ArrayList<>();
        // N명의 학생에 대해 본인 다음에 서야할 학생 리스트를 초기화 합니다.
        for(int i=0; i<N; i++) {
            nextList.add(new ArrayList<>());
        }

        for(int i=0; i<McmpTime; i++) {
            int prev = nextInt()-1;
            int next = nextInt()-1;

            inDegree[next] ++;
            nextList.get(prev).add(next);
        }
    }
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}
}
