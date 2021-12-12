package utils;

import component.memory.MemoryScheduler;

import java.util.Timer;
import java.util.TimerTask;

public class TactGenerator {
    private static int tact = 0;
    private final Timer timer = new Timer();
    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            incTact();
            MemoryScheduler.release();
        }
    };

    public static int getTact() {
        return tact;
    }

    public static void incTact() {
        tact++;
    }

    public static void clearTact() {
        tact = 0;
    }

    public void start() {
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }
}
