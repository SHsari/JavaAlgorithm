package a250308BTest;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class 인기검색어_송석현 {}

/* 250308 인기검색어.
 *  
 * add(String mKeyword)
 * 검색 키워드(String)를 목록에 추가
 * 
 * top5Keyword(String[] mRet)
 * 최다 검색어 5개를 리턴
 * 
 * 
 * add 호출시
 * 그래프의 노드를 추가한다는 관점으로 접근했습니다.
 * 노드(Keyword) 추가시에 가능한 모든 유사검색어 리스트를 뽑아서
 * 이들 중 그래프의 노드로 존재하는 Keyword가 있다면, (과거에 검색한 검색어라면)
 * 상호 노드의 인접리스트에 추가(무향성 간선) 했습니다.
 * 
 * 
 * top5Keyword 찾는 경우
 * 1. BFS 그래프 탐색을 통해 연결된 노드(Keyword 객체) 리스트를 뽑고 (유사 검색어 그룹 찾기)
 * 해당 리스트(그룹)에서 대표자를 찾습니다.(Keyword에 Comparable 적용)
 * 
 * 2. 그래프를 모두 탐색하여 그룹 목록(List<KeywordGroup>)이 만들어지면,
 * GroupCount(포함된 Keyword의 count값 합)와
 * 대표 Keyword의 String 값으로 정렬하여 top 5 키워드를 얻습니다.
 * - 이를 위해 KeywordGroup Class를 정의했습니다.
 * 
 * 
 * 코드가 잘 작동하는 지 모릅니다. 로직만 확인하심이...
 */

class UserSolution {

    int N; // 저장할 최근 검색어 최대 갯수.
    Queue<String> recentN; // 최근 N개의 키워드를 저장하는 큐
    Map<String, Keyword> strToKeyword; // String으로 keyword 객체에 접근할 수 있는 맵.
    Set<Keyword> visited; // 그래프 탐색 시 방문여부를 확인하기 위한 Set

    public void init(int N) {
        this.N = N;
        recentN = new ArrayDeque<>();
        strToKeyword = new HashMap<>();
    }

    public void addKeyword(String mKeyword) {
        // 일단 큐에 넣습니다.
        recentN.add(mKeyword);

        // 큐의 사이즈를 확인합니다.
        // N보다 커질 경우 그래프를 업데이트 합니다. (삭제 또는 count--)
        if(recentN.size() > N) {
            String toDelete = recentN.poll();
            Keyword delKW = strToKeyword.get(toDelete); 
            
            // 카운트만 줄일경우
            if(delKW.count > 1) {
                delKW.count--;
            }
            // 카운트가 1이어서 아예 그래프에서 삭제 해야하는 경우
            else {
                // 인접노드에 저장된 본인 제거.
                for(Keyword adjKW : delKW.similar) {
                    adjKW.similar.remove(delKW);
                }
                strToKeyword.remove(toDelete);
            }
        }  

        // 아래는 그래프 노드 생성작업입니다.

        // 1. 기존에 이미 검색된 검색어인 경우.
        if(strToKeyword.containsKey(mKeyword)) {

            Keyword curKW = strToKeyword.get(mKeyword);
            curKW.count ++;
        }
        
        // 2. 새로운 검색어인 경우
        else {
            // 일단 객체(노드)를 생성합니다.
            Keyword curKW = new Keyword(mKeyword);

            // 인접한 객체를 찾아서 인접 리스트에 서로를 추가합니다. (노드연결=간선생성)
            //
            // 방식은, 브루트포스입니다.
            // 모든 인접한 키워드를 생성하여 그래프에 존재하는 지 확인합니다. (이전에 검색되었는 지 확인)
            // 검색어의 최대 길이가 10 이라고 지정해주었기 때문에 가능한 로직입니다.
            char[] charArray = mKeyword.toCharArray();
            // 각 i번째 알파벳에 대하여
            for(int i=0; i<mKeyword.length(); i++) {
                char original = charArray[i];
                // 다른 모든 알파벳으로 바꿔보면서 인접 노드가 존재하는 지 확인합니다.
                for(char alphabet='a'; alphabet<='z'; alphabet++) {
                    if(alphabet != original) {
                        charArray[i] = alphabet;
                        String adjWord = new String(charArray);
                        // 인접노드가 존재한다면,
                        if(strToKeyword.containsKey(adjWord)) {
                            // 상호 인접리스트에 추가.
                            Keyword adjKW = strToKeyword.get(adjWord);
                            adjKW.similar.add(curKW);
                            curKW.similar.add(adjKW);
                        }
                    }
                }
                // 알파벳 순회를 마친 후 원래 알파벳을 다시 넣어주기.
                charArray[i] = original;
            }
        }
    }


    /*
     * Keyword 노드로 이뤄진 그래프를 BFS로 탐색하여
     * keywordGroup List<Keyword>와 리스트에 속한 각 키워드 Count값의 합,
     * groupCount를 얻습니다.
     * 
     * List<Keyword>에서 대표자를 찾습니다
     * 
     * 대표자 String, groupCount로 이뤄진 객체 
     * KeywordGroup, 그리고 List<KeywordGroup>을 생성하여
     * 정렬한 후, top5를 얻습니다.
     */
    public int top5Keyword(String[] mRet) {

        // 키워드 그룹 리스트.
        List<KeywordGroup> kwGroup = new ArrayList<>();

        // visited 배열 초기화
        visited = new HashSet<>();

        // 일반적인 BFS 로직
        for(Keyword startKW : strToKeyword.values()) {
            if(!visited.contains(startKW)) {
                // 미방문 노드를 발견한 경우: 새로운 그룹이므로
                List<Keyword> group = new ArrayList<>();
                int groupCount = keywordBFS(startKW, group);

                // 대표자를 찾습니다.
                // max 쓰면 되는데 저 시험때는 sort 썼습니다.;;
                Keyword groupHead = Collections.max(group);
                
                // 그룹 리스트에 찾은 그룹을 추가.
                kwGroup.add(new KeywordGroup(groupCount, groupHead.word));
            }
        }

        // 모든 그룹을 다 찾았습니다. top5 group을 찾습니다.
        Collections.sort(kwGroup);
        int returnSize = (kwGroup.size()>=5)? 5 : kwGroup.size();
        for(int i=0; i<returnSize; i++) {
            mRet[i] = kwGroup.get(i).headKeyword;
        }
        return returnSize;
    }

    /*
     * startKeyword로 부터 시작하여 연결된 노드들을 BFS로 탐색합니다.
     * 아주 스탠다드한 BFS입니다.
     *  + 방문한 Keyword(node)들의 count값의 합을 리턴합니다 (groupCount).
     */
    int keywordBFS(Keyword startKW, List<Keyword> group) {

        Queue<Keyword> kwQ = new ArrayDeque<>();
        visited.add(startKW);
        group.add(startKW);
        int groupCount = startKW.count;
        while(! kwQ.isEmpty()) {
            Keyword curKW = kwQ.poll();

            for(Keyword adjKW : curKW.similar) {
                if(!visited.contains(adjKW)) {
                    visited.add(adjKW);
                    kwQ.add(adjKW);
                    // 전달받은 그룹리스트에도(레퍼런스) 추가.
                    group.add(adjKW);
                    groupCount += adjKW.count;
                }
            }
        }
        return groupCount;
    }

    class Keyword implements Comparable<Keyword> {
        int count; // 동일 키워드 검색횟수
        String word; // 검색 키워드 스트링
        List<Keyword> similar; // 유사 검색어 리스트: 간선 리스트라 볼 수 있습니다.

        Keyword(String word) {
            similar = new ArrayList<>();
            this.word = word;
            this.count = 1;
        }

        // 그룹에서 대표자 키워드를 찾을 때 사용할 CompareTo
        @Override
        public int compareTo(Keyword o) {
            if(this.count != o.count) {
                // count 기준 내림차순
                return o.count - this.count;
            }
            else {
                // 사전순
                return this.word.compareTo(o.word);
            }
        }
    }

    class KeywordGroup implements Comparable<KeywordGroup> {
        // 그룹 전체 검색횟수 합과
        // 대표 키워드로만 이뤄진 간단한 클래스입니다.
        // top 5 키워드 찾을 때, sort를 편하게 하기 위해 구현한 클래스입니다.
        int count;
        String headKeyword;

        KeywordGroup(int count, String headKeyword) {
            this.count = count;
            this.headKeyword = headKeyword;
        }

        @Override
        public int compareTo(KeywordGroup o) {
            // count기준 내림차순
            if(this.count != o.count) {
                return o.count - this.count;
            }
            else { // 사전순
                return this.headKeyword.compareTo(o.headKeyword);
            }
        }
    }
}
