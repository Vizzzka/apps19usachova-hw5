package ua.edu.ucu.iterator;

import java.util.Arrays;

public class DefaultIterator implements AsIntStreamIterator {

    private int[] values;
    private int currentElement;
    private int size;

    public DefaultIterator(int... values) {
        this.values = values;
        this.size = this.values.length;
        this.currentElement = 0;
    }

    @Override
    public boolean hasNext() {
        return this.currentElement != this.size;
    }

    @Override
    public Integer next() {
        if (this.size == this.currentElement) {
            return null;
        }
        return this.values[this.currentElement++];
    }

    @Override
    public AsIntStreamIterator copy() {
        return new DefaultIterator(Arrays.copyOf(values, values.length));
    }
}
