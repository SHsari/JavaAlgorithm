package a250410.swea5648atomicDecay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Solution {
	/* SWEA 5648 원자 충돌 */

	/*
	 * 원자들이 공간상에서 상하좌우로 날아다닌다.
	 * 다음과 같은 속성을 가진 N개의 원자들이 주어진다.
	 * 1. 초기 x, y 좌표
	 * 2. 초기 방향 dir(0~3) -> (상, 하, 좌, 우)
	 * 3. 충돌시 발생하는 에너지 K (1~100)
	 * 
	 * 초기 x, y좌표는 -1000 ~ 1000 (포함)범위의 정수값이며
	 * 한개 이상의 원자와 충돌시 에너지 K를 발생시키고 소멸한다.
	 * 모든 원자는 동일한 속도를 가진다.
	 * 원자는 방향을 바꿀 수 없으며 공간의 제약은 없다고 가정한다.
	 * 초기 상태가 주어지면 충돌로 인해 발생하는 에너지 총량을 구하여라.
	 * 
	 * 
	 * 1. 원자들의 초기상태 입력받기
	 * 		1-1. 원자들을 이동방향에 따라 4개의 배열에 나누어 입력받는다
	 * 		1-2. 좌표에 2를 곱해서 충돌시간을 무조건 자연수로 유지한다.
	 * 
	 * 2. 위의 원자 배열에서 충돌가능한 임의의 2개의 원자에 대해 모든 충돌을 기록한다 ( 좌표, 시간 ).
	 * 		2-1. 원자들의 초기상태만(좌표와 방향)을 이용하여 예측한다.
	 * 		2-2. 특정 원자가 시간상으로 앞선 충돌에 개입되면 시간상 이후에 있는 충돌이 무효가 될 수 있다. 일단 등록한다.
	 * 
	 * 3. 충돌을 시간순으로 정렬하고, 동일 좌표, 시간에 일어난 충돌은 병합한다.
	 * 
	 * 4. 시간순으로 정렬된 충돌을 순회한다.
	 * 		4-1. 충돌별로 개입된 원자들을 순회한다.
	 * 		4-1-1. 소멸되지 않은 원자가 2개 이상 있으면 충돌이 유효한 것으로 판단.
	 * 		4-1-2. 충돌에 개입된 원자에 소멸 표시를 한다. (isCollided = true)
	 * 		4-2. 순회를 마치고 충돌에 대한 총 에너지값을 반환한다.
	 * 		
	 */

	 
	 // * 충돌 시간, 충돌 좌표, 충돌 원자를 원소로 가지는 Collision class
	static class Collision implements Comparable<Collision> {
		int time, x, y;

		// 충돌을 병합하는 경우, 원자리스트를 중복없이 합쳐야 하기 때문에 Set으로 정의.
		Set<Atom> involvedAtoms = new HashSet<>();

		// 충돌시간과 두개의 원자 쌍으로 충돌이 처음으로 정의된다.
		Collision(int time, Atom a, Atom b) {
			this.time = time;
			// 충돌 좌표는 충돌시간과 이동방향으로 유추.
			x = a.x + dx[a.dir] * time;
			y = a.y + dy[a.dir] * time;
			involvedAtoms.add(a);
			involvedAtoms.add(b);
		}
		

		// 이 충돌에서 발생하는 에너지를 반환하는 함수.
		public int getCEnergy() {
			// 충돌을 정렬하는 과정에서 우선순위에서 밀려
			// atom Set이 비워질 수 있다.
			if(involvedAtoms.isEmpty()) return 0;
		
			// 충돌을 발생시킨 원자들의 갯수를 센다.
			int aCnt =0;
			// 유효한 원자들을 담을 배열
			Atom colAtom[] = new Atom[involvedAtoms.size()];
			for(Atom a : involvedAtoms) {
				// 원자가 앞서 발생한 충돌에 이미 소멸되었다면,
				if(a.isCollided) continue;
				// 그렇지 않은 경우.
				else colAtom[aCnt++] = a;
			}
			// 2개이상의 원자가 있어야 충돌이 유효하다.
			if(aCnt > 1) {
				int CE =0;
				for(int i=0; i<aCnt; i++) {
					colAtom[i].isCollided = true;
					CE += colAtom[i].energy;
				}
				return CE;
			}
			return 0;
		}

		 
		// 충돌 시간을 기준으로 오름차순으로 정렬
		// 정렬 중, 좌표와 시간이 동일한 충돌을 발견할 경우 병합한다.
		@Override
		public int compareTo(Collision o) {
			if(this.equals(o)) {
				// 좌표, 시간이 동일한 충돌이라면 병합.
				this.involvedAtoms.addAll(o.involvedAtoms);
				o.involvedAtoms.clear();
				return -1;
			}
			else return this.time-o.time;
		}

		// 병합시 사용하기 위해 equals() Override
		@Override
		public boolean equals(Object o) {
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			else {
				Collision other = (Collision) o;
				// 좌표, 시간으로만 동일 여부 판단
				return this.time == other.time && this.x == other.x && this.y==other.y;
			}
		}
	}
	
	// * 원자의 초기 상태를 저장하는 Atom class
	static class Atom {
		int x, y, dir, energy;
		boolean isCollided;
		
		Atom(int x, int y, int dir, int e) {
			this.x = x; this.y = y;
			this.dir = dir; this.energy = e;
			isCollided = false;
		}

		// 충돌에서 Set에 넣기 위해 equals Override
		@Override
		public boolean equals(Object o) {
			if(this == o) return true;
			else if ( o == null || this.getClass() != o.getClass()) return false;
			else {
				Atom other = (Atom) o;
				return this.x == other.x && this.y == other.y && this.dir==other.dir ;
			}
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	
	static int dx[] = {0, 0, -1, 1};
	static int dy[] = {1, -1, 0, 0};
	// 방향별 원자의 갯수를 저장해놓은 dirIdx 배열
	static int N, atomNumAtDir[];
	// atoms[dir][index] 로 접근한다.
	static Atom atoms[][];
	// 시간순으로 정렬하게될 collision 배열
	static List<Collision> collisions;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			// 입력받기
			init();
			// 모든 충돌가능한 두 원자쌍을 이용해 충돌리스트 뽑기.
			setCollisionList();
			// 충돌 리스트를 시간순으로 정렬하기
			Collections.sort(collisions);

			// 충돌리스트를 순회하며 Energy 합 구하기.
			int CE=0;
			Collision before = null;
			for(Collision c : collisions) {
				if(c.equals(before)) continue;
				else CE +=c.getCEnergy();
			}

			result.append(CE).append("\n");
			
		}
		System.out.println(result);
	}

	static void setCollisionList() {
		// 같은 방향의 경우 충돌이 불가능하다.
		// 4개 방향 중 2개씩을 뽑아 해당하는 개별 원자들에 대해
		// 충돌이 가능한지 여부를 확인하고 충돌리스트에 추가한다.

		//1. 상, 하
		int d1 = 0, d2 = 1;
		// 위로 가는 원자들과 아래로 가는 원자들의 모든 조합.
		for(int i=0; i<atomNumAtDir[d1]; i++) {
		for(int j=0; j<atomNumAtDir[d2]; j++) {
			Atom a = atoms[d1][i], b = atoms[d2][j];
			// 충돌이 가능한 조건이면,
			if(a.x == b.x && a.y<b.y) {
				int cTime = (b.y - a.y) / 2;
				collisions.add(new Collision(cTime, a, b));
			}
		}}
		
		// 2. 상, 좌;
		d1 = 0; d2 = 2;
		for(int i=0; i<atomNumAtDir[d1]; i++) {
		for(int j=0; j<atomNumAtDir[d2]; j++) {
			Atom a = atoms[d1][i], b = atoms[d2][j];	
			if(a.y < b.y && a.x < b.x) {
				if(b.y-a.y == b.x-a.x) {
					int cTime = b.y-a.y;
					collisions.add(new Collision(cTime, a, b));
				}
			}
		}}
		
		//3. 상, 우;
		d1 = 0; d2 = 3; 
		for(int i=0; i<atomNumAtDir[d1]; i++) {
		for(int j=0; j<atomNumAtDir[d2]; j++) {
			Atom a = atoms[d1][i], b = atoms[d2][j];		
			if(a.y < b.y && a.x > b.x) {
				if(b.y-a.y == a.x-b.x) {
					int cTime = b.y-a.y;
					collisions.add(new Collision(cTime, a, b));
				}
			}
		}}
		
		//4. 하, 좌;
		d1 = 1; d2 = 2; 
		for(int i=0; i<atomNumAtDir[d1]; i++) {
		for(int j=0; j<atomNumAtDir[d2]; j++) {
			Atom a = atoms[d1][i], b = atoms[d2][j];
			if(a.y > b.y && a.x < b.x) {
				if(a.y-b.y == b.x-a.x) {
					int cTime = a.y - b.y;
					collisions.add(new Collision(cTime, a, b));
				}
			}
		}}
		
		//5. 하, 우;
		d1 = 1; d2 = 3; 
		for(int i=0; i<atomNumAtDir[d1]; i++) {
		for(int j=0; j<atomNumAtDir[d2]; j++) {
			Atom a = atoms[d1][i], b = atoms[d2][j];	
			if(a.y > b.y && a.x > b.x) {
				if(a.y-b.y == a.x - b.x) {
					int cTime = a.y - b.y;
					collisions.add(new Collision(cTime, a, b));
				}
			}
		}}
		
		//6. 좌, 우;
		d1 = 2; d2 = 3; 
		for(int i=0; i<atomNumAtDir[d1]; i++) {
		for(int j=0; j<atomNumAtDir[d2]; j++) {
			Atom a = atoms[d1][i], b = atoms[d2][j];
			if(a.y==b.y && a.x > b.x) {
				int cTime = (a.x - b.x) / 2;
				collisions.add(new Collision(cTime, a, b));
			}
		}}
	}
	
	static void init() throws IOException {
		N = nextInt();
		// 4개의 방향으로 나눠지는 원자들.
		atoms = new Atom[4][N];
		// 방향별로 갯수도 센다.
		atomNumAtDir = new int[4];

		collisions = new ArrayList<>();

		for(int i=0; i<N; i++) {
			int x, y, dir, e;
			// 좌표에*2를 해준 이유는,
			// 정면충돌시 시간이 0.5 단위가 되는 것을 막기 위함.
			x = nextInt()*2;
			y = nextInt()*2;
			dir = nextInt();
			e = nextInt();
			atoms[dir][atomNumAtDir[dir]++] = new Atom(x, y, dir, e);
		}
	}

	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}