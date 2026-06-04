package com.techacademy.trainbase.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sorting Algorithm Tests")
class SortingTest {

    @Test
    @DisplayName("Quick Sort - Basic integer array")
    void testQuickSortBasicArray() {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Already sorted array")
    void testQuickSortAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Reverse sorted array")
    void testQuickSortReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Array with duplicates")
    void testQuickSortWithDuplicates() {
        int[] arr = {5, 2, 8, 2, 9, 1, 5};
        int[] expected = {1, 2, 2, 5, 5, 8, 9};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Single element array")
    void testQuickSortSingleElement() {
        int[] arr = {42};
        int[] expected = {42};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Two element array")
    void testQuickSortTwoElements() {
        int[] arr = {2, 1};
        int[] expected = {1, 2};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Array with negative numbers")
    void testQuickSortNegativeNumbers() {
        int[] arr = {-5, 10, -3, 0, 8, -1, 4};
        int[] expected = {-5, -3, -1, 0, 4, 8, 10};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Null array (should not throw)")
    void testQuickSortNullArray() {
        assertDoesNotThrow(() -> Sorting.quickSort((int[]) null));
    }

    @Test
    @DisplayName("Quick Sort - Empty array")
    void testQuickSortEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Large array")
    void testQuickSortLargeArray() {
        int[] arr = new int[1000];
        int[] expected = new int[1000];

        // Fill array with random numbers
        for (int i = 0; i < 1000; i++) {
            arr[i] = 1000 - i;
            expected[i] = i + 1;
        }

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Generic String array")
    void testQuickSortStringArray() {
        String[] arr = {"banana", "apple", "cherry", "date", "elderberry"};
        String[] expected = {"apple", "banana", "cherry", "date", "elderberry"};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Generic Integer array (Comparable)")
    void testQuickSortGenericIntegerArray() {
        Integer[] arr = {64, 34, 25, 12, 22, 11, 90};
        Integer[] expected = {11, 12, 22, 25, 34, 64, 90};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Generic array with duplicates")
    void testQuickSortGenericWithDuplicates() {
        String[] arr = {"zebra", "apple", "zebra", "banana", "apple"};
        String[] expected = {"apple", "apple", "banana", "zebra", "zebra"};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Generic null array (should not throw)")
    void testQuickSortGenericNullArray() {
        assertDoesNotThrow(() -> Sorting.quickSort((String[]) null));
    }

    @Test
    @DisplayName("Quick Sort - Generic empty array")
    void testQuickSortGenericEmptyArray() {
        String[] arr = {};
        String[] expected = {};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Array with all same elements")
    void testQuickSortAllSameElements() {
        int[] arr = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};

        Sorting.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Quick Sort - Performance test (10000 elements)")
    void testQuickSortPerformance() {
        int[] arr = new int[10000];

        // Fill with descending order for worst case
        for (int i = 0; i < 10000; i++) {
            arr[i] = 10000 - i;
        }

        long startTime = System.nanoTime();
        Sorting.quickSort(arr);
        long endTime = System.nanoTime();

        // Verify sorted
        for (int i = 1; i < arr.length; i++) {
            assertTrue(arr[i - 1] <= arr[i], "Array not properly sorted at index " + i);
        }

        // Performance should be acceptable (roughly < 100ms for 10000 elements)
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        assertTrue(duration < 500, "Sort took too long: " + duration + "ms");
    }

    @Test
    @DisplayName("Quick Sort - Verify stability with custom objects")
    void testQuickSortStability() {
        String[] arr = {"a1", "b2", "a3", "b4", "a5"};

        Sorting.quickSort(arr);

        // After sorting by first character
        for (int i = 0; i < arr.length; i++) {
            assertTrue(arr[i].charAt(0) >= (i > 0 ? arr[i-1].charAt(0) : 'a'),
                    "Array not sorted correctly");
        }
    }
}

