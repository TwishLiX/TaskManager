package config;

public class Configuration {
    public static final int MEMORY_VOLUME = 4096;
    public static final int MIN_PROCESS_MEMORY = 10;
    public static final int MAX_PROCESS_MEMORY = 512;
    public static final int MIN_PROCESS_TIME_REQUIRED = 5;
    public static final int MAX_PROCESS_TIME_REQUIRED = 10;
    public static final int PRIORITIES_QUANTITY = 32;
    public static final int PC_CORES = 4;

    public static int memoryProcessesFill = 0;
    public static int blockId = 0;

    private static String actualProcesses;
    private static String rejectedProcesses;
    private static String doneProcesses;
    private static String activeCores;
    private static String memoryBlocks;

    public static String getActualProcesses() {
        return actualProcesses;
    }

    public static void setActualProcesses(String actualProcesses) {
        Configuration.actualProcesses = actualProcesses;
    }

    public static String getRejectedProcesses() {
        return rejectedProcesses;
    }

    public static void setRejectedProcesses(String rejectedProcesses) {
        Configuration.rejectedProcesses = rejectedProcesses;
    }

    public static String getDoneProcesses() {
        return doneProcesses;
    }

    public static void setDoneProcesses(String doneProcesses) {
        Configuration.doneProcesses = doneProcesses;
    }

    public static String getActiveCores() {
        return activeCores;
    }

    public static void setActiveCores(String activeCores) {
        Configuration.activeCores = activeCores;
    }

    public static String getMemoryBlocks() {
        return memoryBlocks;
    }

    public static void setMemoryBlocks(String memoryBlocks) {
        Configuration.memoryBlocks = memoryBlocks;
    }
}
