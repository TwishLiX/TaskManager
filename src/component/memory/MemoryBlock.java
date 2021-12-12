package component.memory;

import config.Configuration;

import java.util.Comparator;

public class MemoryBlock {
    private int start;
    private int end;
    private int memoryVolume;
    private BlockState state;
    private int id;
    private int activeProcessId;
    public static Comparator<MemoryBlock> byEnd = Comparator.comparingInt(o -> o.end);

    public MemoryBlock(final int start, final int end) {
        this.id = Configuration.blockId++;
        this.start = start;
        this.end = end;
        this.memoryVolume = this.end - this.start;
        this.state = BlockState.EMPTY;
        this.activeProcessId = -1;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public BlockState getState() {
        return state;
    }

    public void setState(BlockState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActiveProcessId() {
        return activeProcessId;
    }

    public void setActiveProcessId(int activeProcessId) {
        this.activeProcessId = activeProcessId;
    }

    public int getMemoryVolume() {
        return memoryVolume;
        //return end - start;
    }

    public void setMemoryVolume(int memoryVolume) {
        this.memoryVolume = memoryVolume;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", start: " + start +
                ", end: " + end +
                ", state: " + state +
                ", process ID: " + activeProcessId;
    }
}
