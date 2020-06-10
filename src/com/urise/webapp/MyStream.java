package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyStream {
    public static void main(String[] args) {
        int[] values = {3, 5, 9, 5, 1, 1};
        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(3);
        list.add(58);
        list.add(4);

        MyStream myStream = new MyStream();
        System.out.println(myStream.minValue(values));
        System.out.println(myStream.oddOrEven(list));
    }

    int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (first, second) -> first * 10 + second);
    }

    List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();
        return integers.stream().reduce(0, (first, second) -> {
            if (second % 2 == 0) {
                even.add(second);
            } else {
                odd.add(second);
            }
            return first + second;
        }) % 2 == 0 ? odd : even;
    }
}
