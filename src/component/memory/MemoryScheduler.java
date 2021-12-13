package component.memory;

import component.core.CPU;
import component.processing.Process;
import component.processing.ProcessState;
import component.processing.Queue;
import config.Configuration;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MemoryScheduler {
    private static final ArrayList<MemoryBlock> memoryBlocks = new ArrayList<>();

    public static void add(MemoryBlock memoryBlock){
        memoryBlocks.add(memoryBlock);
    }

    public static boolean fillMemoryBlock(Process process) {
        int blockId;
        for (int i = 0; i < memoryBlocks.size(); i++) {
            if (memoryBlocks.get(i).getState() == BlockState.EMPTY) {
                if (memoryBlocks.get(i).getMemoryVolume() >= process.getMemory()) {
                    memoryBlocks.get(i).setState(BlockState.FILLED);
                    memoryBlocks.get(i).setActiveProcessId(process.getId());
                    memoryBlocks.get(i).setMemoryVolume(process.getMemory());
                    memoryBlocks.get(i).setEnd(memoryBlocks.get(i).getStart() + memoryBlocks.get(i).getMemoryVolume());
                    blockId = memoryBlocks.get(i).getId();
                    if (memoryBlocks.size() > 1)
                        memoryBlocks.sort(MemoryBlock.byEnd);
                    for (int j = 0; j < memoryBlocks.size(); j++) {
                        if (memoryBlocks.get(j).getId() == blockId) {
                            if (j != memoryBlocks.size() - 1) {
                                memoryBlocks.get(j + 1).setStart(memoryBlocks.get(j).getEnd());
                                memoryBlocks.get(j + 1).setMemoryVolume(memoryBlocks.get(j + 1).getEnd() - memoryBlocks.get(j + 1).getStart());
                            } else {
                                add(new MemoryBlock(memoryBlocks.get(j).getEnd(), Configuration.MEMORY_VOLUME));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void blockBefore(int index) {
        memoryBlocks.get(index).setStart(memoryBlocks.get(index).getStart() - memoryBlocks.get(index - 1).getMemoryVolume());
        memoryBlocks.get(index).setMemoryVolume(memoryBlocks.get(index).getMemoryVolume() + memoryBlocks.get(index - 1).getMemoryVolume());
        memoryBlocks.remove(memoryBlocks.get(index - 1));
    }

    private static void blockAfter(int index) {
        memoryBlocks.get(index).setEnd(memoryBlocks.get(index).getEnd() + memoryBlocks.get(index + 1).getMemoryVolume());
        memoryBlocks.get(index).setMemoryVolume(memoryBlocks.get(index).getMemoryVolume() + memoryBlocks.get(index + 1).getMemoryVolume());
        memoryBlocks.remove(memoryBlocks.get(index + 1));
    }

    private static void freePastBlock(int index){
        if (index == memoryBlocks.size() - 1 && memoryBlocks.get(index).getStart() == memoryBlocks.get(index - 1).getEnd()) {
            if (memoryBlocks.get(index - 1).getState() == BlockState.EMPTY) {
                blockBefore(index);
            }
        }
    }

    private static void freeNextBlock(int index){
        if (index == 0 && memoryBlocks.get(index).getEnd() == memoryBlocks.get(index + 1).getStart()) {
            if (memoryBlocks.get(index + 1).getState() == BlockState.EMPTY) {
                blockAfter(index);
            }
        }
    }

    private static void freeBothBlocks(int index){
        if (index > 0 && index < memoryBlocks.size() - 1) {
            if (memoryBlocks.get(index).getEnd() == memoryBlocks.get(index + 1).getStart() &&
                    memoryBlocks.get(index).getStart() == memoryBlocks.get(index - 1).getEnd()) {
                if (memoryBlocks.get(index + 1).getState() == BlockState.EMPTY) {
                    blockAfter(index);
                }
                if (memoryBlocks.get(index - 1).getState() == BlockState.EMPTY) {
                    blockBefore(index);
                }
            }
        }
    }

    public static void release() {
        if (Queue.getActualProcesses().size() > 0) {
            memoryBlocks.sort(MemoryBlock.byEnd);
            for (int i = 0; i < Queue.getActualProcesses().size(); i++) {
                Queue.getActualProcesses().get(i).setBurstTime(Queue.getActualProcesses().get(i).getBurstTime() + 1);
                if (Queue.getActualProcesses().get(i).getBurstTime() == Queue.getActualProcesses().get(i).getDuration()) {
                    for (int j = 0; j < memoryBlocks.size(); j++) {
                        if (memoryBlocks.get(j).getActiveProcessId() == Queue.getActualProcesses().get(i).getId()) {
                            memoryBlocks.get(j).setState(BlockState.EMPTY);
                            memoryBlocks.get(j).setActiveProcessId(-1);
                            if (memoryBlocks.size() > 1) {
                                freeNextBlock(j);
                                freePastBlock(j);
                                freeBothBlocks(j);
                            }
                            CPU.getCores()[Queue.getActualProcesses().get(i).getCore() - 1].setProcessQuantity(
                                    CPU.getCores()[Queue.getActualProcesses().get(i).getCore() - 1].getProcessQuantity() - 1);
                            if (CPU.getCores()[Queue.getActualProcesses().get(i).getCore() - 1].getProcessQuantity() == 0)
                                CPU.getCores()[Queue.getActualProcesses().get(i).getCore() - 1].setFree(true);
                            Configuration.memoryProcessesFill -= Queue.getActualProcesses().get(i).getMemory();
                            Queue.getActualProcesses().get(i).setState(ProcessState.FINISHED);
                            Queue.getActualProcesses().get(i).setCore(-1);
                            Queue.getDoneProcesses().add(Queue.getActualProcesses().get(i));
                            Queue.getActualProcesses().remove(Queue.getActualProcesses().get(i));
                        }
                    }
                }
            }
        }
    }

    public static ArrayList<MemoryBlock> getMemoryBlocks() {
        return memoryBlocks;
    }

    public static String printMemoryBlocks() {
        return memoryBlocks.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}