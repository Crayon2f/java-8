package com.ivan.java8.collection.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SortLearn {

    @Test
    void testInsertSort() {

        int array[] = {200, 5, 6, 100, 4, 3, 1, 8};
//        insertSort(array);
        writeMyself(array);
        System.out.println(Arrays.toString(array));

    }


    private void writeMyself(int[] arr) {

        int index, temp;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            index = i;
            while (index > 0 && temp < arr[index - 1]) { //保证没有到首
                arr[index] = arr[index - 1];
                index --;
            }
            arr[index] = temp;
        }
    }














    private void insertSort(int[] arr) {

//        int i, j;
//        int n = arr.length;
//        int target;
//
//        //假定第一个元素被放到了正确的位置上
//        //这样，仅需遍历1 - n-1
//        for (i = 2; i < n; i++) {
//            j = i;
//            target = arr[i];
////            System.out.println(String.format("target ==> %d", target));
//            System.out.println("=================");
//            while (j > 0 && target < arr[j - 1]) {
////                System.out.println(String.format("arr[j] ==> %d", arr[j]));
////                System.out.println(String.format("arr[j - 1] ==> %d", arr[j - 1]));
//                arr[j] = arr[j - 1];
//                j--;
//                System.out.println(Arrays.toString(arr));
//            }
//            arr[j] = target;
//        }


        for (int i = 1; i < arr.length; i++) {
            //插入的数
            int insertVal = arr[i];
            //被插入的位置(准备和前一个数比较)
            int index = i - 1;
            //如果插入的数比被插入的数小
            while (index >= 0 && insertVal < arr[index]) {
                //将把arr[index] 向后移动
                arr[index + 1] = arr[index];
                //让index向前移动
                index--;
            }
            //把插入的数放入合适位置
            arr[index + 1] = insertVal;
        }

    }



}
