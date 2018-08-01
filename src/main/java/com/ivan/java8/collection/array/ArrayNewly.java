package com.ivan.java8.collection.array;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * Created by feiFan.gou on 2017/12/26 19:39.
 */
class ArrayNewly {

    @Test
    void sort() {

        String[] strArr = new String[]{"kdn", "ad", "nn34", "ddsd", "dfd", "sssssssccd"};

        //自然顺序
        Arrays.parallelSort(strArr);
        //指定索引排序, [from-to]
        Arrays.parallelSort(strArr, 2, 5);
        //指定排序规则
        Arrays.parallelSort(strArr, Comparator.comparing(String::length));
        System.out.println(Arrays.toString(strArr));
    }

    @Test
    void splIterator() {

        // Create an array list for doubles.
        ArrayList<Integer> al = new ArrayList<>();

        // Add values to the array list.
        al.add(1);
        al.add(2);
        al.add(-3);
        al.add(-4);
        al.add(5);

        // Obtain a Stream to the array list.
        Stream<Integer> str = al.stream();

        // getting Spliterator object on al
        Spliterator<Integer> splitr1 = str.spliterator();

        // estimateSize method
        System.out.println("estimate size : " + splitr1.estimateSize());

        // getExactSizeIfKnown method
        System.out.println("exact size : " + splitr1.getExactSizeIfKnown());

        // hasCharacteristics and characteristics method
        System.out.println(splitr1.hasCharacteristics(splitr1.characteristics()));
//
        System.out.println("Content of arraylist :");
        // forEachRemaining method
        splitr1.forEachRemaining(System.out::println);

        // Obtaining another  Stream to the array list.
        Stream<Integer> str1 = al.stream();
        splitr1 = str1.spliterator();

        // trySplit() method
        Spliterator<Integer> splitr2 = splitr1.trySplit();

        // If splitr1 could be split, use splitr2 first.
        if(splitr2 != null) {
            System.out.println("Output from splitr2: ");
            splitr2.forEachRemaining(System.out::println);
        }

        // Now, use the splitr
        System.out.println("\nOutput from splitr1: ");
        splitr1.forEachRemaining(System.out::println);
    }

    /**
     * 计数排序
     */
    @Test
    void countingSort() {

        int max = 8;
        int[] a = new int[]{2, 1, 4, 5, 7, 2, 6};
        int[] count = new int[max];
        for (int anA : a) {
            count[anA]++;
        }
        for (int i = max, k = a.length; k > 0; ) {
            while (count[--i] == 0) ;
            int value = i;
            int s = count[i];
            do {
                a[--k] = value;
            } while (--s > 0);
        }
        System.out.println(Arrays.toString(a));
    }

    @Test
    void insertionSort() {

        int[] a = new int[]{2, 1, 4, 5, 3, 2, 6};
        for (int i = 0, j = i; i < a.length - 1; j = ++i) {
            int ai = a[i + 1];
            while (ai < a[j]) {
                a[j + 1] = a[j];
                if (j-- == 0)
                    break;
            }
            a[j + 1] = ai;
        }
    }


}
