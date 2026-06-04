package com.techacademy.trainbase.controller;

/**
 * Sorting class containing various sorting algorithms
 */
public class Sorting {

    /**
     * Quick Sort Algorithm - O(n log n) average time complexity
     * Divides the array into partitions and recursively sorts them
     *
     * @param arr the array to be sorted
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Helper method for quick sort - recursive implementation
     *
     * @param arr the array to sort
     * @param low starting index
     * @param high ending index
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get the partition point
            int pi = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /**
     * Partition method - places pivot element at its correct position
     * All elements smaller than pivot are on left, greater on right
     *
     * @param arr the array to partition
     * @param low starting index
     * @param high ending index
     * @return the partition point
     */
    private static int partition(int[] arr, int low, int high) {
        // Choose the rightmost element as pivot
        int pivot = arr[high];

        // Index of smaller element - indicates the right position
        // of pivot found so far
        int i = low - 1;

        // Traverse through all elements
        // Compare each element with pivot
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[high] (pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    /**
     * Quick Sort for generic comparable objects
     *
     * @param arr the array to be sorted
     * @param <T> the type of elements in array (must implement Comparable)
     */
    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Helper method for generic quick sort
     *
     * @param arr the array to sort
     * @param low starting index
     * @param high ending index
     * @param <T> the type of elements
     */
    private static <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /**
     * Partition method for generic types
     *
     * @param arr the array to partition
     * @param low starting index
     * @param high ending index
     * @param <T> the type of elements
     * @return the partition point
     */
    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) < 0) {
                i++;
                // Swap arr[i] and arr[j]
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[high]
        T temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    /**
     * Utility method to print array
     *
     * @param arr the array to print
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /**
     * Main method to demonstrate Quick Sort
     */
    /*public static void main(String[] args) {
        // Test with integer array
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array:");
        printArray(arr);

        quickSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with String array
        String[] strings = {"banana", "apple", "cherry", "date", "elderberry"};
        System.out.println("\nOriginal strings:");
        for (String s : strings) {
            System.out.print(s + " ");
        }
        System.out.println();

        quickSort(strings);

        System.out.println("Sorted strings:");
        for (String s : strings) {
            System.out.print(s + " ");
        }
        System.out.println();
    }*/
}
