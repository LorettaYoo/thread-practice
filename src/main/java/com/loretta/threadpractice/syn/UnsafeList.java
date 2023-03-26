package com.loretta.threadpractice.syn;

import java.util.ArrayList;

public class UnsafeList {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> list.add(Thread.currentThread().getName())).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.size());
    }
}
