package ru.kryuchkov.maksim.ustest.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SelectionUtilsTest {

    private final SelectionUtils selectionUtils = new SelectionUtils();

    @Test
    void getNthSmallestNumber_fixed() {
        long[] numbers = {1L, 4L, 6L, 3L, 2L, 6L, 1L};
        int n = 4;

        Optional<Long> result = selectionUtils.getNthSmallestNumber(numbers, n);

        assertTrue(result.isPresent());
        assertEquals(3L, result.get());
    }

    @Test
    void getNthSmallestNumber_fixed_lastElement() {
        long[] numbers = {1L, 4L, 6L, 3L, 2L, 6L, 1L, 7L};
        int n = 8;

        Optional<Long> result = selectionUtils.getNthSmallestNumber(numbers, n);

        assertTrue(result.isPresent());
        assertEquals(7L, result.get());
    }

    @Test
    void getNthSmallestNumber_fixed_nZero() {
        long[] numbers = {1L, 4L, 6L, 3L, 2L, 6L, 1L, 7L};
        int n = 0;

        Optional<Long> result = selectionUtils.getNthSmallestNumber(numbers, n);

        assertFalse(result.isPresent());
    }

    @Test
    void getNthSmallestNumber_fixed_nNegative() {
        long[] numbers = {1L, 4L, 6L, 3L, 2L, 6L, 1L, 7L};
        int n = -4;

        Optional<Long> result = selectionUtils.getNthSmallestNumber(numbers, n);

        assertFalse(result.isPresent());
    }

    @Test
    void getNthSmallestNumber_empty() {
        long[] numbers = {};
        int n = 2;

        Optional<Long> result = selectionUtils.getNthSmallestNumber(numbers, n);

        assertFalse(result.isPresent());
    }

    @Test
    void getNthSmallestNumber_nullNumbers() {
        Optional<Long> result = selectionUtils.getNthSmallestNumber(null, 2);

        assertFalse(result.isPresent());
    }

    static Stream<Arguments> arguments_getNthSmallestNumber() {
        Random random = new Random();
        ArrayList<Arguments> args = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int arrLength = random.nextInt(10) + 10;
            int index = random.nextInt(arrLength) + 1;
            long[] arr = new long[arrLength];
            for (int j = 0; j < arrLength; j++) {
                arr[j] = random.nextLong();
            }
            List<Long> sortedArr = Arrays.stream(arr).boxed().sorted().toList();
            args.add(Arguments.of(arr, index, sortedArr.get(index - 1)));
        }
        return args.stream();
    }

    @ParameterizedTest
    @MethodSource("arguments_getNthSmallestNumber")
    void getNthSmallestNumber(long[] numbers, int n, long expected) {
        Optional<Long> result = selectionUtils.getNthSmallestNumber(numbers, n);
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }
}