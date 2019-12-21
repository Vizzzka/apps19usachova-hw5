package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntToIntStreamFunction;

public class FlatMapDecorator extends IteratorDecorator {

    private IntToIntStreamFunction toStream;
    private int[] currentStreamArray;
    private int currentStreamPosition;

    public FlatMapDecorator(AsIntStreamIterator iterator,
                            IntToIntStreamFunction toStream) {
        super(iterator);
        this.toStream = toStream;
        this.currentStreamPosition = 0;
        this.currentStreamArray = new int[0];
    }

    @Override
    public boolean hasNext() {
        return currentStreamPosition != currentStreamArray.length
                || super.hasNext();
    }

    @Override
    public Integer next() {
        if (!this.hasNext()) {
            return null;
        }
        if (currentStreamPosition == currentStreamArray.length) {
            int value = super.next();
            this.currentStreamPosition = 0;
            this.currentStreamArray = this.toStream.
                    applyAsIntStream(value).toArray();
        }
        return this.currentStreamArray[currentStreamPosition++];
    }

    @Override
    public AsIntStreamIterator copy() {
        return new FlatMapDecorator(super.copy(), toStream);
    }

}
