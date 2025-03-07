package a250305.a5644wirelessCharger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;


/* SWEA 5644 무선충전
 * 
 * 옛날에 팩맨을 아주 빠르게 구현하는 외국인 유튭을 본적이 있는데.
 * 그것과 비슷한 느낌으로 진행했다.
 * 
 * static으로 A, B위치를 선언하고
 * 매번 움직이고 A, B의 최대 충전량을 구한다.
 * 
 * Charger 클래스를 만들었고,
 * power 기준으로 내림차순 정렬된 Charger 배열을 순회하면서 
 * Charger의 Chargeable Bound를 기준으로 충전 가능여부를 판단하는 함수를 구현했다.
 * 
 * 부분부분 나누어서 생각하면 생각보다 깔끔하게 떨어지는 문제로 보임.
 */

public class Solution {

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;

    // 0: stop
    // 1: Up
    // 2: Right
    // 3: Down
    // 4: Left
    static int[] dX = {0, 0, 1, 0, -1};
    static int[] dY = {0, -1, 0, 1, 0};

	// N은 주어지는 배열의 길이
	static int M, BCNum;
    static int[] dirA, dirB;
    static Charger[] Chargers;

    static int ax, ay, bx, by, maxChargeSum;
	public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();

		int T = nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
		    M = nextInt();
            BCNum = nextInt();

            dirA = new int[M];
            dirB = new int[M];
            Chargers = new Charger[BCNum];

            ax=1; ay=1; bx=10; by=10; maxChargeSum=0;

            for(int i=0; i<M; i++) { dirA[i] = nextInt(); }
            for(int i=0; i<M; i++) { dirB[i] = nextInt(); }

            for(int i=0; i<BCNum; i++) {
                int x, y, bound, power;
                x = nextInt();  y = nextInt();
                bound = nextInt(); power = nextInt();
                Chargers[i] = new Charger(x, y, bound, power);
            }

            // 충전 power에 따라 내림차순으로 정렬 통해 각종 복잡한 로직을 줄인다.
            Arrays.sort(Chargers);

            MoveNCharge();

            result.append(maxChargeSum).append("\n");

		}
			
		System.out.println(result);
	}
    

    // M
    // ChargerNum
    // Chargers
    // int[] A, B
    static void MoveNCharge() {
        // 최초위치에서
        addChargeAmount();
        for(int iter=0; iter<M; iter++) {
           // System.out.println(ax + " " + ay + ", " + bx + " " + by);

            // 다음 A, B 좌표를 셋팅
            ax += dX[dirA[iter]];
            ay += dY[dirA[iter]];  
            bx += dX[dirB[iter]];
            by += dY[dirB[iter]];

            addChargeAmount();
        }
    }

    static void addChargeAmount() {

        int chargeNum =0;

        // 현재 위치에서 A, B 충전여부
        boolean isACharged = false;
        boolean isBCharged = false;

        // power에 따라 내림차순으로 정렬된 Chargers를 순회하며
        for(Charger bc : Chargers) {
            boolean AChargeable = false;
            boolean BChargeable = false;

            // 충전되지 않은 사람에 한하여 충전 가능한 지 검사
            if(!isACharged) AChargeable = bc.isChargeable(ax, ay);
            if(!isBCharged) BChargeable = bc.isChargeable(bx, by);
            // 둘 중 한사람만 충전 가능하다면,
            // 충전 가능한자에 충전했음을 표시하고 일반적으로 진행
            if(AChargeable^BChargeable) {
                maxChargeSum += bc.power;
                chargeNum++;
                isACharged = AChargeable;
                isBCharged = BChargeable;
            }
            // 두사람 모두 충전 가능하다면
            // 여튼 거시기하다.
            else if (AChargeable && BChargeable) {
                chargeNum++;
                maxChargeSum += bc.power;
            }
            if(chargeNum == 2) return;
        }
    }


    // Charger Class
    static class Charger implements Comparable<Charger>{
        // 모든 정보를 가지고 있다.
        int x, y, bound, power;
        
        //생성자
        Charger(int x, int y, int bound, int power) {
            this.x = x;
            this.y = y;
            this.bound = bound;
            this.power = power;
        }
        
        // 입력받은 좌표가 충전 범위 안에 있는 지 확인. 만하딴 디스트
        public boolean isChargeable(int px, int py) {
            return Math.abs(this.x-px) + Math.abs(this.y-py) <= bound;
        }

        @Override
        // 내림차순 정렬을 위한 CompareTo 오버라이드
        public int compareTo(Charger o) {
            return o.power-this.power;
        }
    }

	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}