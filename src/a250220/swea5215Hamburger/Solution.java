package a250220.swea5215Hamburger;
import java.io.*;
import java.util.StringTokenizer;


/*
 * SWEA 5215. 햄버거 다이어트
 * 
 * T : testCase
 * N : 재료의 수 ( 1 <= N <= 20 )
 * L : 제한 칼로리 ( 1 <= L <= 10^4 )
 * 
 * Ti : 만족도 
 * Ki : 칼로리
 * 
 * 전형적인 knapSack problem 인가부다
 * 
 * 조합으로 풀되 프루닝을 적극적으로 사용해보자
 */
 
public class Solution {
     
    static int N, MAXCAL;
     
    //결과값이 될 maxUtilScore
    static int maxUtilScore;
 
    //매 재귀 호출마다 업데이트 될 값.
    static int utilSum;
    static int calSum;
    static int remCal;
    static int remUtil;
     
    static int[] utilities;  // 1000 이하
    static int[] calories;   // 1000 이하
     
     
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
         
        int T = Integer.parseInt(br.readLine());
         
        for(int tc=1; tc<=T; tc++) {
            maxUtilScore=0;
            remUtil = 0;
            remCal = 0;
            StringTokenizer nl = new StringTokenizer(br.readLine());
            N = Integer.parseInt(nl.nextToken());
            MAXCAL = Integer.parseInt(nl.nextToken());
             
            utilities = new int[N];
            calories = new int[N];
            
            // 단순 입력을 위한 For문
            // N개의 utility, Calorie 쌍을 입력받는다.
            for(int i=0; i<N; i++) {
                StringTokenizer ucPair = new StringTokenizer(br.readLine());
                utilities[i] = Integer.parseInt(ucPair.nextToken());
                calories[i] = Integer.parseInt(ucPair.nextToken());
                remCal += calories[i];
                remUtil += utilities[i];
            }
            // 입력 완료
            // 재료를 다 넣어도 제한값을 만족한다면.
            if(remCal < MAXCAL) {
                sb.append("#" + tc + " ");
                sb.append(remUtil + "\n");
                continue;
            }
 
            // Calorie 기준으로 내림차순으로 정렬.
            // pruning을 빨리 할 수 있다.
            quickSort(0, N-1);
 
            // System.out.println();
            // for(int i=0; i<N; i++) {
            //  System.out.println(utilities[i] + ", " + calories[i]);
            // }
            // System.out.println();
 
             
            // 최대 utility 업데이트
            findCombination(0);
             
            sb.append("#" + tc + " ");
            sb.append(maxUtilScore + "\n");
        }
         
        System.out.println(sb);
    }
     
    // 조합을 사용해서 풀기.
    // index 0부터 재료를 순회하면서 사용할 때, 하지 않을 때에 따라 분기한다.
    // pruning을 적극적으로 써보자.
 
    // false return시 더 이상 탐색할 필요가 없음
    // true return시 일반적으로 진행한다.
 
    static boolean findCombination(int start){
 
        // 최고깊이에 도달했을 때
        if(start==N) {
            updateMaxUtil();
            return true;
        }
         
        // 남아있는 Calorie 가 너무 적어 어떤것도 추가선택이 불가능 할 때,
        if(calories[N-1]+calSum > MAXCAL) {
            updateMaxUtil();
            return true;
        }
 
        // 남은것을 모두 선택해도 이전 최고값을 갱신할 수 없을 때
        if(utilSum + remUtil < maxUtilScore) {
            return false;
        }
 
 
        // 남은 선택지를 모두 선택해도 제한값을 만족하는 경우
        if(calSum + remCal <= MAXCAL) {
            utilSum += remUtil;
            updateMaxUtil();
            utilSum -= remUtil;
            // calories를 내림차순 정렬을 했으므로.
            // 이후에 발생하는 재귀가 의미가 없어지는데..??
            return false;
        }
 
        boolean needFurtherSearch = true;
        int idx;
        // 위의 상황이 만족하지 않아서 추가 탐색이 필요한 경우
        for(idx=start; idx<N; idx++) {
            remUtil -= utilities[idx];
            remCal -= calories[idx];
 
            utilSum += utilities[idx];
            calSum += calories[idx];
             
            //여기서부터 업데이트 로직 블럭
            if(calSum<=MAXCAL) { //제한값을 넘지 않았을 때
                needFurtherSearch = findCombination(idx+1);
                utilSum-=utilities[idx];
                calSum-=calories[idx];
            } else { //제한값 보다 클 때
                calSum -= calories[idx];
                utilSum -= utilities[idx];
                updateMaxUtil();
            }
            //원복 등 완료하고 추가 탐색여부 확인
            if(!needFurtherSearch) {
                idx++;
                break;
            }
        }
 
        for(int i=start; i<idx; i++) {
            remUtil += utilities[i];
            remCal += calories[i];
        }
        return true;
    }
 
    static void updateMaxUtil() {
        if(utilSum>maxUtilScore) {
            maxUtilScore = utilSum;
        }
    }
 
    static void quickSort(int start, int end) {
        if(start>=end) return;
 
        int poleIndex = (int)(Math.random() * (end-start+0.9)) + start;
        swap(poleIndex, end);
        int pole = calories[end];
        int idxL=start;
        int idxR=end;
        for(int i=start; i<=idxR; i++) {
            if(calories[i] > pole) {
                swap(idxL++, i);
            }
            else if(calories[i] < pole) {
                swap(i--, idxR--);
            }
        }
        quickSort(start, idxL-1);
        quickSort(idxR+1, end);
    }
     
    static void swap(int idx1, int idx2) {
        int tmp = calories[idx1];
        calories[idx1] = calories[idx2];
        calories[idx2] = tmp;
        tmp = utilities[idx1];
        utilities[idx1] = utilities[idx2];
        utilities[idx2] = tmp;
    }
}