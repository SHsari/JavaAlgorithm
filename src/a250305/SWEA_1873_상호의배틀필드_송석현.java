package a250305;
/*
 * 상호의 배틀필드
 * 정수 H, W (2 ≤ H, W ≤ 20)
 * N(0 < N ≤ 100)
 * 
 * 빡구현이다!
 * 
 * UDRL => 방향
 * S => Shoot
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;


public class SWEA_1873_상호의배틀필드_송석현 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();

	static int H, W, N;
	static char[][] map;

	static final char GROUND = '.';
	static final char BRICK = '*';
	static final char STEEL = '#';
	static final char WATER = '-';
	
	
	/*
	 * 실수한것 
	 * DIRECTION Stirng에서 아래쪽 방향을 지칭하는 'v'
	 * 대문자로 써서 틀림
	 */
	static final char UP = '^';
	static final char DOWN = 'v';
	static final char LEFT = '<';
	static final char RIGHT = '>';
	static final String DIRECTION = "^v<>";
	// 입력시 방향 Char로부터 int 값을 바로 찾도록.
	static final Map<Character, Integer> DirMap= new HashMap<>();
	static {
		DirMap.put('U', 0);
		DirMap.put('D', 1);
		DirMap.put('L', 2);
		DirMap.put('R', 3);
	}
	static final int[] xDir = {0, 0, -1, 1};
	static final int[] yDir = {-1, 1, 0, 0};
	
	// 현재 탱크의 방향과 위치
	static int direction;
	static int x, y;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
			result.append("#").append(tc).append(" ");

			init();
			// 맵 입력완료
			play();
			// 모든 시퀀스 플레이
			print();
			
		}
			
		System.out.println(result);
	}
	
	static void init() throws IOException {
		StringTokenizer hw = new StringTokenizer(br.readLine());
		
		H = Integer.parseInt(hw.nextToken());
		W = Integer.parseInt(hw.nextToken());
		
		map = new char[H+2][W+2];
		
		//맵을 철근으로 둘러싼다.
		Arrays.fill(map[0], STEEL);
		Arrays.fill(map[H+1], STEEL);
		
		boolean didFindTank = false;
		
		for(int row=1; row<=H; row++) {
			StringBuilder line = new StringBuilder();
			line.append("#");
			line.append(br.readLine());
			line.append("#");
			
			String lineString = line.toString();
			
			map[row] = lineString.toCharArray();
			
			/*
			 * 실수한것:
			 * 로직 수정하다가 tank의 현위치를 저장하지 않았음
			 * 0,0에서 무조건 시작하니 Array Out Of Bounds 뜸.
			 */
			// 탱크찾는 로직 아주 비효율적이다.
			if(!didFindTank) {
				int tankColumn;
				if((tankColumn = lineString.indexOf(UP)) != -1) direction = 0;
				else if((tankColumn = lineString.indexOf(DOWN)) != -1) direction = 1;
				else if((tankColumn = lineString.indexOf(LEFT)) != -1) direction = 2;
				else if((tankColumn = lineString.indexOf(RIGHT)) != -1) direction = 3;
				if(tankColumn>0) {
					didFindTank = true;
					map[row][tankColumn] = GROUND;
					x=tankColumn; y=row;
				}
			}
		}
	}
	
	//플레이한다.
	static void play() throws IOException {
		N = Integer.parseInt(br.readLine());
		char[] sequence = br.readLine().toCharArray();
		for(char p : sequence) {
			//움직이는 명령일 경우
			if(DirMap.containsKey(p)) move(DirMap.get(p));
			
			// 쏘는 명령일 경우
			else shoot();
		}

	}
	/*
	 * 실수한 것:
	 * map 접근시 
	 * map[x][y] 로 접근
	 * 
	 * x, y의 실제 의미가 Column Row로 했기 때문에
	 * 
	 * map[y][x] 로 접근 필요
	 * 
	 * 아님 처음 입력 받을 때 부터 [x][y]로 받던지..
	 */
	
	// Ground 만 갈 수 있으므로
	static void move(int dir) {
		direction = dir;
		int nx = x + xDir[dir];
		int ny = y + yDir[dir];
		
		if(map[ny][nx]==GROUND) {
			x=nx; y=ny;
		}
	}	
	
	// 쏠때.
	static void shoot() {
		int dx = xDir[direction];
		int dy = yDir[direction];
		
		int nx = x+ dx;
		int ny = y+ dy;
		while(map[ny][nx] != STEEL) {
			if(map[ny][nx] == BRICK) {
				map[ny][nx] = GROUND;
				break;
			}
			nx+=dx;
			ny+=dy;
		}
	}
	
	
	/*
	 * 테스트케이스 표시 이후 엔터 없이 바로 맵 출력해야함..
	 * 
	 */
	static void print() {
//		StringBuilder result = new StringBuilder();
//		result.append(x + " " + y);
		map[y][x] = DIRECTION.charAt(direction);
		for(int row=1; row<=H; row++) {
			String mapRow = new String(map[row]).substring(1, W+1);
			result.append(mapRow).append("\n");
		}
		//System.out.println(x+ " " + y);
		map[y][x] = GROUND;
		//System.out.println(result);
	}

}
