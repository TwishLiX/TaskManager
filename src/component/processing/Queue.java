package component.processing;

import component.core.CPU;
import component.memory.MemoryScheduler;
import config.Configuration;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Queue {
    private static final List<Process> actualProcesses = new ArrayList<>();
    private static final List<Process> rejectedProcesses = new ArrayList<>();
    private static final List<Process> doneProcesses = new ArrayList<>();
    private static int lastID = 1;

    public static void add() {
        Process process = new Process(lastID++);
        if (rejectedProcesses.size() > 1) {
            rejectedProcesses.sort(Process.byTime);
        }
        if (rejectedProcesses.size() > 0 && !isMaxDuration(process)) {
            process = rejectedProcesses.get(0);
            rejectedProcesses.remove(rejectedProcesses.get(0));
        }
        if (!MemoryScheduler.fillMemoryBlock(process)) {
            process.setState(ProcessState.WAITING);
            rejectedProcesses.add(process);
        } else {
            process.setState(ProcessState.RUNNING);
            process.setCore(Utils.getRandomArrayElement(CPU.getCores().length) + 1);
            CPU.getCores()[process.getCore() - 1].setProcessQuantity(
                    CPU.getCores()[process.getCore() - 1].getProcessQuantity() + 1);
            CPU.getCores()[process.getCore() - 1].setFree(false);
            actualProcesses.add(process);
            Configuration.memoryProcessesFill += process.getMemory();
        }
    }

    public static void add(final int processQuantity) {
        for (int i = 0; i < processQuantity; i++) {
            add();
        }
    }

    private static boolean isMaxDuration(Process process) {
        return process.getDuration() > rejectedProcesses.get(0).getDuration();
    }

    public static List<Process> getActualProcesses() {
        return actualProcesses;
    }

    public static List<Process> getRejectedProcesses() {
        return rejectedProcesses;
    }

    public static List<Process> getDoneProcesses() {
        return doneProcesses;
    }

    public static int getLastID() {
        return lastID;
    }

    public static String printActualProcesses() {
        if (actualProcesses.size() > 0) {
            return actualProcesses.stream().map(Object::toString)
                    .collect(Collectors.joining("\n"));
        }
        return "No processes\n";
    }

    public static String printRejectedProcesses() {
        if (rejectedProcesses.size() > 0) {
            return rejectedProcesses.stream().map(Object::toString)
                    .collect(Collectors.joining("\n"));
        }
        return "No processes\n";
    }

    public static String printDoneProcesses() {
        if (doneProcesses.size() > 0) {
            return doneProcesses.stream().map(Object::toString)
                    .collect(Collectors.joining("\n"));
        }
        return "No processes\n";
    }
}

