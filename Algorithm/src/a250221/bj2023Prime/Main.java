package a250221.bj2023Prime;

/*
 * 싱기방귀 소수
 * 에라토스어쩌구채로 N 자리까지 소수 탐색 한 후에
 * N자리 소수들부터 순회하면서 그것이
 * REAL AMAZING 한지 판단해본다
 * 
 */

import java.util.Arrays;
import java.util.Scanner;



public class Main {
    static Scanner sc = new Scanner(System.in);

    static Boolean[] isPrime;
    static int N;

    public static void main(String args[]) {
        N = sc.nextInt();
        int limit = (int)(Math.pow(10, N));

        isPrime = new Boolean[limit];
        Arrays.fill(isPrime, true);

        eratosthenes(limit);


        for(int i=limit/10*2; i<limit; i++) {
            if(isPrime[i]) {
                if(isAmazing(i)) {
                    System.out.println(i);
                }
            }
        }

        Runtime rt = Runtime.getRuntime();

        long total_mem = rt.totalMemory();

        long free_mem = rt.freeMemory();

        long used_mem = total_mem - free_mem;

        System.out.println("Amount of used memory: " + used_mem + "Bytes");

    } 

    //limit 미만까지 소수여부 판단.
    static void eratosthenes(int limit) {
        for(int seed=2; seed<=Math.sqrt(limit); seed++) {
            if(isPrime[seed]){
                for(int multiple=seed*2; multiple<limit; multiple+=seed) {
                    isPrime[multiple] = false;
                }
            }
        }
    }

    static Boolean isAmazing(int prime) {
        for(int div10=prime/10; div10>0; div10/=10) {
            if(!isPrime[div10]) return false;
        }
        return true;
    }
}
