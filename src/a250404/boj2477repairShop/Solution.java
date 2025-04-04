package a250404.boj2477repairShop;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
	
	static class Desk {
		boolean isVacant;
		int enterTime;
		int workTime;
		Desk(int workTime) {
			this.workTime = workTime;
			enterTime = Integer.MIN_VALUE;
			isVacant = true;
		}
	}	

	static BufferedReader br;
	static StreamTokenizer st;
	static StringBuilder result;
	static int N, M, K, A, B;
	static Desk[] receptions, repairs;

	static PriorityQueue<Desk> vReceptions, vRepairs;
	
	static int[] k;

	static List<Integer> recepN, repairM;
	
	public static void main(String[] args) throws IOException {		
		br = new BufferedReader(new InputStreamReader(System.in));
        st = new StreamTokenizer(br);
        result = new StringBuilder();
        
        int T = nextInt();
		for(int tc=1; tc<=T; tc++) {
			result.append("#").append(tc).append(" ");
			
			
			
		}
	}
	
	//접수창구 거치기.
	static void reception() {	
		
	}
	
	static void setInitStatus() {
		vReceptions = new PriorityQueue<>();
		vRepairs = new PriorityQueue<>();
		
		for(int i=0; i<=N; i++) {
			vReceptions.offer(receptions[i]);
		}
		for(int i=0; i<=M; i++) {
			vRepairs.offer(repairs[i]);
		}
	}

	
	static void init() throws IOException {
		
		N = nextInt();
		M = nextInt();
		K = nextInt();
		A = nextInt();
		B = nextInt();
		
		recepN = new ArrayList<>();
		repairM = new ArrayList<>();
		
		receptions = new Desk[A+1];
		repairs = new Desk[B+1];
		
		k = new int[K+1];
		
		for(int i=1; i<=A; i++) receptions[i] = new Desk(nextInt());
		for(int i=1; i<=B; i++) repairs[i] = new Desk(nextInt());
		for(int i=1; i<=K; i++) k[i] = nextInt();
		
	}

	
	static int nextInt() throws IOException {
		st.nextToken();
		return (int) st.nval;
	}

}