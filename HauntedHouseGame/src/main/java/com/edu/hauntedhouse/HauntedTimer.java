package com.edu.hauntedhouse;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


public class HauntedTimer {
    private static Timer timer;
    private static AtomicInteger timeLeft;

    public static void initTimer(int secs) {
        timeLeft = new AtomicInteger(secs);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int tl = timeLeft.decrementAndGet();
                if (tl == 0) {
                    System.out.println("You lost, better luck next time!");
                    System.exit(0);
                }
            }
        };

        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    public static void addTime(int secs){
        timeLeft.addAndGet(30);
    }

    public static int getTime(){
        return timeLeft.get();
    }
}
