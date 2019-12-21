package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;
import ua.edu.ucu.iterator.*;

import java.util.ArrayList;
import java.util.List;

public class AsIntStream implements IntStream {

    private IteratorDecorator iteratorDecorator;

    public AsIntStream(IteratorDecorator iteratorDecorator) {
        this.iteratorDecorator = iteratorDecorator;
    }

    public static IntStream of(int... values) {
        return new AsIntStream(new IteratorDecorator(new DefaultIterator(values)));
    }

    public IteratorDecorator getIteratorDecorator() {
        return iteratorDecorator;
    }

    @Override
    public Double average() {
        if (isEmpty())
            throw new IllegalArgumentException("no elements");
        double sum = this.sum();
        return sum / this.count();
    }

    @Override
    public Integer max() {
        if (isEmpty())
            throw new IllegalArgumentException("no elements");
        return reduce(Integer.MIN_VALUE, (maxi, x) -> Math.max(x, maxi));
    }

    @Override
    public Integer min() {
        if (isEmpty())
            throw new IllegalArgumentException("no elements");
        return reduce(Integer.MAX_VALUE, (mini, x) -> Math.min(x, mini));
    }

    @Override
    public long count() {
        return this.toArray().length;
    }

    @Override
    public Integer sum() {
        if (isEmpty())
            throw new IllegalArgumentException("no elements");
        return reduce(0, (sum, x) -> sum += x);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return new AsIntStream(new FilterDecorator(this.iteratorDecorator.copy(), predicate));
    }

    @Override
    public void forEach(IntConsumer action) {
        int[] newValues = this.toArray();
        for (int value : newValues) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(new MapDecorator(this.iteratorDecorator.copy(), mapper));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntStream(new FlatMapDecorator(this.iteratorDecorator.copy(), func));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int[] newValues = this.toArray();
        int res = identity;
        for (int value : newValues) {
            res = op.apply(res, value);
        }
        return res;
    }

    @Override
    public int[] toArray() {
        // not to count if no terminate
        List<Integer> list = new ArrayList<>();
        while (this.iteratorDecorator.hasNext()) {
            list.add(this.iteratorDecorator.next());
        }
        Integer[] IntNewValues = list.toArray(new Integer[0]);
        int[] newValues = new int[IntNewValues.length];
        for (int i = 0; i < IntNewValues.length; ++i) {
            newValues[i] = IntNewValues[i];
        }
        this.iteratorDecorator = new IteratorDecorator(new DefaultIterator(newValues));
        return newValues;
    }

    private boolean isEmpty() {
        return !this.iteratorDecorator.hasNext();
    }
}
