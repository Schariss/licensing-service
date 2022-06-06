package com.optimagrowth.license;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 15; i++) {
            int rand = new Random().nextInt(3);
            System.out.println(rand);
            System.out.println("\t------------------");
        }

    }
}
