package com.loretta.threadpractice.syn;

public class UnsafeBank {
    public static void main(String[] args) {
        Account account = new Account(100, "salary");

        Drawing david = new Drawing(account, 50, "david");
        Drawing sam = new Drawing(account, 100, "sam");

        david.start();
        sam.start();
    }
}

class Account {
    int money;
    String name;

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}

class Drawing extends Thread {
    final Account account;
    int drawingMoney;
    int nowMoney;

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
        synchronized (account){
            if (account.money - drawingMoney < 0) {
                System.out.println(Thread.currentThread().getName() + " balance is insufficient!!!");
                return;
            }

            // sleep 放大问题发生性
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            account.money = account.money - drawingMoney;
            nowMoney = nowMoney + drawingMoney;

            System.out.println(account.name+" account balance: "+ account.money);
            System.out.println(this.getName() +" money in hand: "+nowMoney);
        }
    }
}


