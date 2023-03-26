package com.loretta.threadpractice;


// 管程法
public class TestPC2 {
    public static void main(String[] args) {
        TV tv = new TV();

        new Player(tv).start();
        new Watcher(tv).start();
    }

}

class Player extends Thread {
    TV tv;

    public Player(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                this.tv.play("playing");
            } else {
                this.tv.play("advertisement");
            }
        }
    }
}

class Watcher extends Thread {
    TV tv;

    public Watcher(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            tv.watch();
        }
    }
}

class TV {
    String voice;
    boolean flag = true;

    public synchronized void play(String voice) {
        if (!flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("play: " + voice);

        this.notifyAll();
        this.voice = voice;
        this.flag = !this.flag;
    }

    public synchronized void watch() {
        if (flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("watch: " + voice);

        this.notifyAll();
        this.flag = !this.flag;
    }
}

