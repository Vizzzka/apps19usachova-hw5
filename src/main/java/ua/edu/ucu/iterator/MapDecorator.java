package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntUnaryOperator;

import java.util.Iterator;

public class MapDecorator extends IteratorDecorator {

    private IntUnaryOperator intUnaryOperator;

    public MapDecorator(AsIntStreamIterator iterator,
                        IntUnaryOperator intUnaryOperator) {
        super(iterator);
        this.intUnaryOperator = intUnaryOperator;
    }

    @Override
    public Integer next() {
        return this.intUnaryOperator.apply(super.next());
    }

    @Override
    public AsIntStreamIterator copy() {
        return new MapDecorator(super.copy(), intUnaryOperator);
    }

}
