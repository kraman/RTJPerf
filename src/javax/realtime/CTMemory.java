package javax.realtime;

public class CTMemory extends LTMemory {

    public CTMemory(long initialSize, long maxSize) {
        super(initialSize, maxSize);
    }

    public CTMemory(long size) {
        super(size, size);
    }

    public CTMemory(long size, boolean tss) {
        super(size, size);
    }

}
