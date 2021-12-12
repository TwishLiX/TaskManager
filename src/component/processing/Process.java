package component.processing;

import config.Configuration;
import utils.TactGenerator;
import utils.Utils;

import java.util.Comparator;

public class Process {
    private int id;
    private String name;
    private int priority;
    private int duration;
    private int memory;
    private int startTact;
    private int burstTime;
    private ProcessState state;
    private int core;

    public static Comparator<Process> byTime = (o1, o2) -> o2.duration - o1.duration;

    public Process(int id) {
        this.id = id;
        this.name = "P" + this.id + ".exe";
        this.priority = Utils.getRandomNumber(Configuration.PRIORITIES_QUANTITY);
        this.memory = Utils.getRandomNumber(Configuration.MIN_PROCESS_MEMORY, Configuration.MAX_PROCESS_MEMORY);
        this.duration = Utils.getRandomNumber(Configuration.MIN_PROCESS_TIME_REQUIRED, Configuration.MAX_PROCESS_TIME_REQUIRED);
        this.startTact = TactGenerator.getTact();
        this.burstTime = 0;
        this.state = ProcessState.READY;
        this.core = -1;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getDuration() {
        return duration;
    }

    public int getMemory() {
        return memory;
    }

    public int getStartTact() {
        return startTact;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public ProcessState getState() {
        return state;
    }

    public int getCore() {
        return core;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", name: " + name +
                ", priority: " + priority +
                ", duration: " + duration +
                ", memory block: " + memory +
                ", start tact: " + startTact +
                ", burst time: " + burstTime +
                ", state: " + state +
                (core != -1 ? (", core: " + core) : "");
    }
}
