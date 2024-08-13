package com.reactive.spring.reactive_with_spring.declarerative;

import java.util.Arrays;
import java.util.List;

public class declarerative {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,3,20,19,11);
        int sum = numbers.stream()
                .filter(number -> number > 6 && (number % 2 != 0))
                .mapToInt(i->i)
                .sum();

        System.out.println(sum);
    }
}
