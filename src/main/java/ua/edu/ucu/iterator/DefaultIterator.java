package ua.edu.ucu.iterator;

import ua.edu.ucu.stream.AsIntStream;

import java.util.Arrays;
import java.util.Iterator;

public class DefaultIterator implements AsIntStreamIterator {

    private int[] values;
    private int currentElement;
    public int size;

    public DefaultIterator(int... values) {
        this.values = values;
        this.size = this.values.length;
        currentElement = 0;
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
