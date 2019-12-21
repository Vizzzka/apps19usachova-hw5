package ua.edu.ucu.iterator;

import ua.edu.ucu.stream.AsIntStream;

import java.util.Iterator;

public class IteratorDecorator implements AsIntStreamIterator {

    private AsIntStreamIterator iterator;

    public IteratorDecorator(AsIntStreamIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Integer next() {
        return (Integer) this.iterator.next();
    }

    @Override
    public AsIntStreamIterator copy() {
        return new IteratorDecorator(iterator.copy());
    }
}
