package a250305.swea1247best;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/* SWEA 1247 최적경로 문제입니다.
 * DP로 푸는 방식이 궁금하여 시도했으나 실패했습니다.
 * DP가 뭐 아주 새롭고 대단한 방식이 아니라
 * 일종에 메모하면서 풀기로 생각하면 좋을 것 같다는 결론입니다.
 * 
 * 지금 문제와 같은 순열을 통한 완전탐색으로 해결되는 문제에서 DP를 적용했을 때 얼만큼 빨라지는 지 확인해보면
 * 이해가 더 정확히 될 것 같네요.
 * 
 * 
 * 여튼 결론은 인접행렬, 순열, 프루닝을 통해 풀었습니다.
 */


public class Solution {

    // static 공간에는 선언만.
	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

    // N: 돌아다녀야 할 고객 위치의 갯수
    // 위치 하나는 x, y 두개의 정수로 표시됩니다.
	static int N;
	static int startX, startY, endX, endY;
    static int minDistSum;
	
	static int[] nx, ny;
	static int[] startDist, endDist;
	static int[][] distArr;
	static boolean[] visited;
    static int[] selected;

	/*
     * 실수한것:
     * 문제를 잘 읽지 않고 풀다가
     * 회사-귀가위치-방문필요한 고객위치
     * 순서로 입력 받지 않고 귀가위치를 마지막에 받는 만행을 저질렀습니다.
     */
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");

            minDistSum = Integer.MAX_VALUE;
			N = nextInt();

            visited = new boolean[N];
            selected = new int[N];

            nx = new int[N];
			ny = new int[N];
			
			distArr = new int[N][N];
					
			startX = nextInt();
			startY = nextInt();
            endX = nextInt();
			endY = nextInt();
			
			for(int i=0; i<N; i++) {
				nx[i] = nextInt();
				ny[i] = nextInt();
			
				for(int j=0; j<i; j++) {
					distArr[i][j] = getMDist(nx[i], ny[i], nx[j], ny[j]);
					distArr[j][i] = distArr[i][j];
				}
			}

            endDist = new int[N];
            for(int i=0; i<N; i++) {
                endDist[i] = getMDist(nx[i], ny[i], endX, endY);
            }


            // Manhattan Distance를 사용하기 때문에 발생하는 최적화 가능성이 있습니다.
            // 두 점 A, B를 꼭짓점으로 하는 좌표상의 직사각형 안에 점C가 존재한다면, 
            // A->B 또는 역방향 이동시 점C를 무조건 포함시킬 수 있습니다.
            // 어떻게 따로 적용이 될 지는 미지수입니다.
            // 아 근데.. 아 필요가 없는 생각인가..? 순열이 하는게 그것인가?
            startPermutation();

            result.append(minDistSum).append("\n");
		}
			
		System.out.println(result);
	}

    static void startPermutation() {
        for(int firstNode=0; firstNode<N; firstNode++) {
            int distSum = getMDist(nx[firstNode], ny[firstNode], startX, startY);
            visited[firstNode] = true;
            permutation(1, firstNode, distSum);
            visited[firstNode] = false;
        }
    }

    /*
     * 실수한것:
     * minDistSum과 distSum을 비교할 때,
     * distSum에다 마지막 노드~귀가위치를 더하지 않고 비교하는
     * 만행을 저질렀습니다.
     */
    static void permutation(int depth, int preNode, int distSum) {
        
        // distSum이 minDistSum 보다 클때
        // 바로 순열 탐색을 프루닝합니다.
        if(distSum >= minDistSum) {
            return;
        }

        // 모든 선택을 마쳤을 때,
        // minDistSum 값에 새로운 경로 값을 반영합니다.
        else if(depth == N) {
            distSum += endDist[preNode];
            if(minDistSum>distSum) {
                minDistSum = distSum;
                return;
            }
        }


        // 이외의 경우 기본적인 재귀 순열 알고리즘 입니다.
        for(int curNode=0; curNode<N; curNode++) {
            if(!visited[curNode]) {
                visited[curNode] = true;

                permutation(depth+1, curNode, distSum + distArr[preNode][curNode]);

                visited[curNode] = false;
            }
        }
    }

    	
	static int getMDist(int x1, int y1, int x2, int y2) {
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}
