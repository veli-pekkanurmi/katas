package com.kattis;
import java.util.Scanner;

public class TakeTwoStones {

    public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int input = sc.nextInt();
    System.out.println(winner(input));

    }
    
    public static String winner (int value) {
        
       String winner = "";
        if ((value%2) == 0 ) {
            winner = "Bob";
        } else {
            winner = "Alice";
        }
        return winner;
    }
    
}