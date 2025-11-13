package ru.kryuchkov.maksim.ustest.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Component
public class SelectionUtils {

    private final Random random = new Random();

    public Optional<Long> getNthSmallestNumber(long[] numbers, int n) {
        if (numbers == null || numbers.length < n) {
            return Optional.empty();
        }
        if (n == 1) {
            return Optional.of(Arrays.stream(numbers).min().getAsLong());
        }
        return Optional.of(randomizedSelect(numbers, 0, numbers.length - 1, n));
    }

    private long randomizedSelect(long[] arr, int left, int right, int i) {
        if (left == right) {
            return arr[left];
        }
        int pivotIndex = randomizedPartition(arr, left, right);
        int k = pivotIndex - left + 1;
        if (i == k) {
            return arr[pivotIndex];
        }
        if (i < k) {
            return randomizedSelect(arr, left, pivotIndex - 1, i);
        }
        return randomizedSelect(arr, pivotIndex + 1, right, i - k);
    }

    private int randomizedPartition(long[] arr, int p, int r) {
        int pivotIndex = random.nextInt(p, r);
        return partition(arr, p, r, pivotIndex);
    }

    private int partition(long[] arr, int left, int right, int pivotIndex) {
        swap(arr, pivotIndex, right);
        long pivotValue = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivotValue) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private void swap(long[] arr, int i, int j) {
        long temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
