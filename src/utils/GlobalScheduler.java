package utils;

import component.core.CPU;
import component.memory.MemoryBlock;
import component.memory.MemoryScheduler;
import component.processing.Queue;
import config.Configuration;

public class GlobalScheduler extends Thread {

    public static void programStart() {
        CPU.initCores();
        TactGenerator timer = new TactGenerator();
        timer.start();
        MemoryScheduler.add(new MemoryBlock(0, Configuration.MEMORY_VOLUME));
    }

    @Override
    public void run(){
        while (true){
            Queue.add(2);
            Configuration.setActualProcesses(Queue.printActualProcesses());
            Configuration.setRejectedProcesses(Queue.printRejectedProcesses());
            Configuration.setDoneProcesses(Queue.printDoneProcesses());
            Configuration.setMemoryBlocks(MemoryScheduler.printMemoryBlocks());
            Configuration.setActiveCores(CPU.printCores());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
