package a250307.swea2382microbeIsolation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.List;
import java.util.ArrayList;

/*
 * SWEA2382 미생물 격리
 * 
 * N*N 의 맵
 * 군집의 좌표, 군집 내 미생물 수, 이동방향
 * 매시간 방향으로  이동
 * 끝에 닿았을 경우 군집 내 미생물 수 절반 감소 (홀수일 경우 버리면 된다)
 * 
 * 두개 이상의 군집이 한 위치에 모이면 합쳐짐.
 * 이동방향의 경우 가장 큰 수의 군집것을 유지.
 * (군집 크기가 동일한 경우는 없다)
 * 
 * M시간동안 미생물 군집 격리. M시간 후 남아있은 미생물 수 총합.
 * 
 * 세로위치(1), 가로위치(1), 미생물 수(7), 이동방향(상)
 * 1 2 3 4
 * 상, 하, 좌, 우,
 */

public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	static int[] dRow = {-1, 1, 0, 0};
	static int[] dCol = {0, 0, -1, 1};

	// 한 변의 길이 N, 격리시간 M, 군집갯수 K
	static int N, M, K;
	static int microbeCount;
	static int map[][];
	static Microbe[] klusters;
	
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			N = nextInt();
			M = nextInt();
			K = nextInt();
			
			map = new int[N][N];
			klusters = new Microbe[K];
			
			for(int idx=0; idx<K; idx++) {
				Microbe mk = klusters[idx];
				mk.row = nextInt(); mk.col = nextInt();
				mk.count = nextInt();
				mk.dir = nextInt()-1;
			}
			
			
		}
			
		System.out.println(result);
	}
	
	
	static class Microbe {
		int row, col, count, dir;
	}
	
	static void iterate() {
		
	}
	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}