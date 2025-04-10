package a250410.swea5648atomicDecay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Solution_timeout {
	
	static class Point {
		int x, y;
		Point(int x, int y) {
			this.x  = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			return 31*x +y;
		}
		@Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;                      // 동일한 객체인지 체크
		    if (obj == null || getClass() != obj.getClass())    // null이거나 클래스가 다르면 false
		        return false;
		    Point other = (Point) obj;                          // Point로 캐스팅
		    return this.x == other.x && this.y == other.y;      // x와 y값 비교
		}
		@Override
		public String toString() {
			return "(" +x+", "+y+")";
		}
	}
	
	static class Atom {
		static int[] dx = {0, 0, -1, 1};
		static int[] dy = {1, -1, 0, 0};
		static Map<Point, Atom> atoms = new HashMap<>();

		
		static int iterate() {
			Map<Point, Atom> newAtoms = new HashMap<>();
			Set<Point> collideP = new HashSet<>();
			int collideE = 0;
			for(Atom atom : atoms.values()) {
				atom.move();
				if(!newAtoms.containsKey(atom.p)) {
					newAtoms.put(atom.p, atom);
				}else {
					collideE += atom.energy;
					collideP.add(atom.p);
				}
			}
			
			for(Point colP :collideP) {
				Atom toRemove = newAtoms.get(colP);
				collideE += toRemove.energy;
				newAtoms.remove(toRemove.p);
			}
			
			atoms = newAtoms;
			return collideE;
		}
		
		int dir, energy;
		Point p;

		public Atom(int x, int y, int dir, int energy) {
			this.p = new Point(x,y);
			this.dir = dir;
			this.energy = energy;
			atoms.put(p, this);
		}
		
		public void move() {
		    p.x += dx[dir];
		    p.y += dy[dir];
		}

		@Override
		public String toString() {
			return "Atom ["+ p +" dir=" + dir + ", energy=" + energy + "]";
		}
	}

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	/*
	 * "충돌 가능성"
	 * 
	 * 1. 같은 column 또는 row에 있으면서 방향이 반대일때,
	 * 2. 
	 * 
	 *  "불가능 경우"
	 *  방향이 같은 경우 _. 모두 불가능
	 *  분산되는 경우 (거리가 멀어지는 경우)
	 * 
	 */
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			
			init();
			
			int energy=0;
			for(int time=0; time<2001; time++) {
				energy+=Atom.iterate();
			}
			result.append(energy).append("\n");
		}
		System.out.println(result);
	}
	
	static void init() throws IOException {
		int atomNum = nextInt();
		
		for(int i=0; i<atomNum; i++) {
			int x, y, dir, e;
			x = nextInt();
			y = nextInt();
			dir = nextInt();
			e = nextInt();
			new Atom(x, y, dir, e);
		}
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}