package com.simbirsoft.test.app;

import com.simbirsoft.test.entity.Dictionary;

import java.util.Scanner;

public class CountWords {
    private static Scanner scanner = new Scanner(System.in);
    private static Dictionary dictionary;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            System.out.println("Enter webpage URL");
            String url = scanner.next();
            dictionary = new Dictionary(url);
            dictionary.count();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
