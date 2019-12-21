package ua.edu.ucu.iterator;

import java.util.Arrays;
import java.util.NoSuchElementException;

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
    public Integer next() throws NoSuchElementException {
        if (this.size == this.currentElement) {
            throw new NoSuchElementException();
        }
        return this.values[this.currentElement++];
    }

    @Override
    public AsIntStreamIterator copy() {
        return new DefaultIterator(Arrays.copyOf(values, values.length));
    }
}
