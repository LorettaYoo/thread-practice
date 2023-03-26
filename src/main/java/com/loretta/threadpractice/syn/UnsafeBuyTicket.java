package com.loretta.threadpractice.syn;

public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();

        new Thread(buyTicket,"I").start();
        new Thread(buyTicket,"You").start();
        new Thread(buyTicket,"She").start();
    }
}

class BuyTicket implements Runnable {
    private int ticketNums = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void buy() throws InterruptedException {
        if (ticketNums <= 0) {
            flag = false;
            return;
        }

        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName() + " get the " + ticketNums--);
    }
}