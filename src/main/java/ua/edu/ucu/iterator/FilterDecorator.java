package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntPredicate;

import java.util.Iterator;

public class FilterDecorator extends IteratorDecorator{

    private IntPredicate predicate;
    private Integer currentValue;

    public FilterDecorator(AsIntStreamIterator iterator, IntPredicate predicate) {
        super(iterator);
        this.predicate = predicate;
        while (super.hasNext()) {
            int value = super.next();
            if (this.predicate.test(value)) {
                currentValue = value;
                break;
            }
        }
    }

    @Override
    public Integer next() {
        int value = currentValue;
        while (super.hasNext()) {
            currentValue = super.next();
            if (this.predicate.test(currentValue)) {
                return value;
            }
        }
        currentValue = null;
        return value;
    }

    @Override
    public boolean hasNext() {
        return currentValue != null;
    }

    @Override
    public AsIntStreamIterator copy() {
        return new FilterDecorator(super.copy(), predicate);
    }

}
