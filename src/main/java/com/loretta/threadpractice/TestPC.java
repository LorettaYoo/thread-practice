package com.loretta.threadpractice;


// 管程法
public class TestPC {
    public static void main(String[] args) {
        SynContainer container = new SynContainer();

        new Producer(container).start();
        new Consumer(container).start();
    }

}

class Producer extends Thread{
    SynContainer container;

    public Producer(SynContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            container.push(new Chicken(i));
            System.out.println("produce "+i+" chicken");
        }
    }
}

class Consumer extends Thread{
    SynContainer container;

    public Consumer(SynContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("consume ----->"+container.pop().id+" chickens");
        }
    }
}

class Chicken{
    int id;
    public Chicken(int id){
        this.id = id;
    }
}

class SynContainer{
    Chicken[] chickens = new Chicken[10];
    int count = 0;

    // 生产者放入产品
    public synchronized void push(Chicken chiken){
        // 容器满了，需要等待消费者消费
        if (count == chickens.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果没有满，需要生产产品
        chickens[count] = chiken;
        count++;

        this.notifyAll();
    }

    // 消费者消费产品
    public synchronized Chicken pop(){
        if (count == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        count--;
        Chicken chicken = chickens[count];

        this.notifyAll();
        return chicken;
    }

}