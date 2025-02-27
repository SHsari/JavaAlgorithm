package a250227;

import java.io.*;

/* BOJ_6987 월드컵
 * 직접 경기를 돌려보는 방법밖엔 없다.
 * 미리 입력받은 결과에 어긋난다면 프루닝(백트랙킹)
 *  유효한 경우의 수를 찾았다면 즉시 탐색 중단.
 */

public class BOJ_11572_월드컵_송석현{

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StreamTokenizer st = new StreamTokenizer(br);

    static final int TEAMS = 6;
    static final int MATCHES = 15;
    // matches: 6개 팀의 15개 경기 조합
    static final int[][] matches = new int[MATCHES][2]; 
    static { // mat
        int idx = 0;
        for (int team1 = 0; team1 < TEAMS - 1; team1++) {
            for (int team2 = team1 + 1; team2 < TEAMS; team2++) {
                matches[idx][0] = team1;
                matches[idx][1] = team2;
                idx++;
            }
        }
    }
    
    // results는 입력받은 승/무/패 갯수
    // [teamNumber][승/무/패 번호] 로 접근
    static int[][] results; 


    public static void main(String[] args) throws IOException {
        
        StringBuilder sb = new StringBuilder();


        for (int t = 0; t < 4; t++) {
            results = new int[TEAMS][3]; // 6개 팀의 [승, 무, 패] 정보 저장
            int sum = 0; // 전체 경기 개수 확인

            for (int i = 0; i < TEAMS; i++) {
                results[i][0] = nextInt();// 승
                results[i][1] = nextInt(); // 무
                results[i][2] = nextInt(); // 패
                sum += results[i][0] + results[i][1] + results[i][2];
            }

            // 전체 경기 수가 30이 아닌경우 바로 불가능 처리.
            if (sum != 30) {
                sb.append("0 ");
                continue;
            }

            boolean isValid = dfs(0); 
            sb.append(isValid ? "1 " : "0 ");
        }

        System.out.println(sb.toString());
    }

    // 백트래킹 탐색
    static boolean dfs(int matchIdx) {

        // 경기를 마지막까지 치룸으로써 유효함이 증명됐다면
        if (matchIdx == MATCHES) {
            return true;
        }

        int team1 = matches[matchIdx][0];
        int team2 = matches[matchIdx][1];

        // 1팀 승리의 경우
        if (results[team1][0] > 0 && results[team2][2] > 0) {
            results[team1][0]--; results[team2][2]--;
            if(dfs(matchIdx + 1)) return true;
            results[team1][0]++; results[team2][2]++;
        }

        // 무승부
        if (results[team1][1] > 0 && results[team2][1] > 0) {
            results[team1][1]--; results[team2][1]--;
            if(dfs(matchIdx + 1)) return true;
            results[team1][1]++; results[team2][1]++;
        }

        // away승
        if (results[team1][2] > 0 && results[team2][0] > 0) {
            results[team1][2]--; results[team2][0]--;
            if(dfs(matchIdx + 1)) return true;
            results[team1][2]++; results[team2][0]++;
        }

        return false;
    }

    static int nextInt() throws IOException {
        st.nextToken();
        return (int)st.nval;
    }
}