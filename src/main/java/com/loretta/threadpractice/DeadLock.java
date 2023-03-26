package com.loretta.threadpractice;

public class DeadLock {
    public static void main(String[] args) {
        Makeup cinderella = new Makeup(0, "cinderella");
        Makeup snowWhite = new Makeup(1, "Snow White");

        cinderella.start();
        snowWhite.start();

    }
}

class Lipstick{

}

class Mirror{

}

class Makeup extends Thread{
    static final Lipstick lipstick = new Lipstick();
    static final Mirror mirror = new Mirror();

    int choice;
    String girlName;

    Makeup(int choice,String girlName){
        this.choice = choice;
        this.girlName = girlName;
    }

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeup() throws InterruptedException {
        if (choice == 0) {
            synchronized (lipstick){
                System.out.println(this.girlName + " get lipstick lock");
                Thread.sleep(1000);
            }

            synchronized (mirror){
                System.out.println(this.girlName+" want mirror lock");
            }
        }else {
            synchronized (mirror){
                System.out.println(this.girlName + " get mirror lock");
                Thread.sleep(1000);
            }

            synchronized (lipstick){
                System.out.println(this.girlName+" want lipstick lock");
            }
        }
    }
}
