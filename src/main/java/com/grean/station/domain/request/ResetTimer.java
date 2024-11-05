//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class ResetTimer {
    int totalTime;
    int leftTime;
    boolean running;

    public ResetTimer(int timer) {
        this.totalTime = timer;
        this.leftTime = this.totalTime;
        this.running = true;
        (new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        if (ResetTimer.this.leftTime > 0 && ResetTimer.this.running) {
                            Thread.sleep(1000L);
                            --ResetTimer.this.leftTime;
                            continue;
                        }
                    } catch (Exception var2) {
                    }

                    return;
                }
            }
        })).start();
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getLeftTime() {
        return this.leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
