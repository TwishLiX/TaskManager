package component.core;

import config.Configuration;

public class CPU {
    private static Core[] cores;

    public static void initCores() {
        cores = new Core[Configuration.PC_CORES];
        for(int i = 0; i < cores.length; i++) {
            cores[i] = new Core();
        }
    }

    public static Core[] getCores() {
        return cores;
    }

    public static void setCores(Core[] cores) {
        CPU.cores = cores;
    }

    public static String printCores() {
        StringBuilder coreBusyness = new StringBuilder();
        for (int i = 0; i < cores.length; i++) {
            coreBusyness.append(i + 1).append(" - ").append(cores[i].toString());
            if (i < cores.length - 1) {
                coreBusyness.append(", ");
            }
        }
        return coreBusyness.toString();
    }
}