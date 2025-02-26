package a250226.bj1931MeetingRoom;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder result = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		int N =	Integer.parseInt(br.readLine());
		Meeting[] meetings = new Meeting[N];
		
		for(int i=0; i<N; i++) {
			StringTokenizer meetInfo = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(meetInfo.nextToken());
			int end = Integer.parseInt(meetInfo.nextToken());
			meetings[i] = new Meeting(start, end);
		}
		
		Arrays.sort(meetings);
		List<Meeting> result = new ArrayList<>();
		result.add(meetings[0]);
		
		for(int i=1; i<N; i++) {
			if(result.get(result.size() -1).end <= meetings[i].start) {
				result.add(meetings[i]);
			}
		}
		
		System.out.println(result.size());
	}
	
	static class Meeting implements Comparable<Meeting> {
		int start, end;
		
		public Meeting(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int compareTo(Meeting o) {
			return this.end != o.end ? this.end - o.end : this.start - o.start;
		}
	}

}
