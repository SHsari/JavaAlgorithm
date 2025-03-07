package a250305;

import java.util.Scanner;

public class AdjListTest {
	
	static class Node {
		int to, weight;
		Node next;
		public Node(int to, int weight, Node next) {
			super();
			this.to = to;
			this.weight = weight;
			this.next = next;
		}
		
		@Override
		public String toString() {
			return "Node [to=" + to + ", weight=" + weight + "]"+"\n next=" + next + "]";
		}
	}
	
	static Node[] adjList;
	static int V;
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		V = sc.nextInt();
		
		adjList = new Node[V];
		int E = sc.nextInt();
		
		for(int i=0; i < E; i++) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int weight = sc.nextInt();
			adjList[from] = new Node(to, weight, adjList[from]);
			adjList[to] = new Node(from, weight, adjList[to]);
		}
		
		
		for(Node head : adjList) {
			System.out.println(head);
		}
	}

}
