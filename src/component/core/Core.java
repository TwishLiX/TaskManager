package component.core;

public class Core {
    private int processQuantity;
    private boolean isFree;

    public Core() {
        this.processQuantity = 0;
        this.isFree = true;
    }

    public int getProcessQuantity() {
        return processQuantity;
    }

    public void setProcessQuantity(int processQuantity) {
        this.processQuantity = processQuantity;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        return !isFree ? "busy" : "free";
    }
}
