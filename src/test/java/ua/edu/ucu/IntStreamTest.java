package ua.edu.ucu;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.stream.AsIntStream;
import ua.edu.ucu.stream.IntStream;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class IntStreamTest {


    private IntStream stream, emptyStream;
    private double delta = 0.0001;

    @Before
    public void init () {
        stream = AsIntStream.of(1, 0, 2, 3, -5, -6);
        emptyStream = AsIntStream.of();
    }

    @Test
    public void testOf() {
        int[] expected = {1, 0, 2, 3, -5, -6};
        assertArrayEquals(expected, stream.toArray());
        assertArrayEquals(new int[0], emptyStream.toArray());
    }

    @Test
    public void testMaxMin() {
        int expectedMin = -6, expectedMax = 3;
        assertEquals(expectedMin, stream.min(), delta);
        assertEquals(expectedMax, stream.max(), delta);
    }

    @Test
    public void testAverage() {
        double expectedAverage = -5.0 / 6;
        assertEquals(expectedAverage, stream.average(), delta);
    }

    @Test
    public void testCountEmpty() {
        assertEquals(0, emptyStream.count());
    }

    @Test
    public void testReduce() {
        int expectedReduce = -5;
        assertEquals(expectedReduce, stream.reduce(0, (sum, x) -> sum += x));
    }

    @Test
    public void testFilter() {
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, stream.filter(x -> x > 0).toArray());
    }

    @Test
    public void testMap() {
        int[] expected = {2, 1, 3, 4, -4, -5};
        assertArrayEquals(expected, stream.map(x -> x + 1).toArray());
    }

    @Test
    public void testFlatMap() {
        int[] expected = {1, 2, 0, 0, 2, 4, 3, 6, -5, -10, -6, -12};
        assertArrayEquals(expected, stream.flatMap(x ->  AsIntStream.of(x, x * 2)).toArray());
    }

    @Test
    public void testFewOperation() {
        int[] expected = {2, 4, 3, 6, 4, 8};
        assertArrayEquals(expected, stream.filter(x -> x > 0)
                .map(x -> x + 1)
                .flatMap(x -> AsIntStream.of(x, x * 2))
                .toArray());
    }

    @Test
    public void testExtraFewOperation() {
        int[] expected = {6, 8};
        assertArrayEquals(expected, stream.filter(x -> x > 0)
                .map(x -> x + 1)
                .flatMap(x -> AsIntStream.of(x, x * 2))
                .filter(x -> x >= 6)
                .toArray());
    }

    @Test
    public void testFewTerminals() {
        int expectedMin = -6;
        double expectedAverage = -5.0 / 6;
        assertEquals(expectedMin, stream.min(), delta);
        assertEquals(expectedAverage, stream.average(), delta);
    }

    @Test
    public void testNotImmutable() {
        int[] expectedOldStreamArray = {1, 0, 2, 3, -5, -6};
        IntStream newStream = stream.filter(x -> x > 1).map(x -> x - 1);
        int[] expectedNewStreamArray = {1, 2};

        assertArrayEquals(expectedNewStreamArray, newStream.toArray());
        assertArrayEquals(expectedOldStreamArray, stream.toArray());
    }

    @Test
    public void testForeach() {
        Integer[] expectedArray = {2, 1, 3, 4, -4, -5};

        List<Integer> list = new ArrayList<>();
        stream.map(x -> x + 1).forEach(list::add);
        assertArrayEquals(expectedArray, list.toArray(new Integer[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMinMax() {
        emptyStream.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAverage() {
        emptyStream.average();
    }

    @Test
    public void testOperationOnEmpty() {
        int[] expectedArray = new int[0];
        assertArrayEquals(expectedArray, emptyStream.filter(x -> x < 0).toArray());
        assertArrayEquals(expectedArray, stream.filter(x -> x > 10).toArray());
    }

}
