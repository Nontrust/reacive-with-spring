package com.reactive.spring.reactive_with_spring.declarerative;

import java.util.Arrays;
import java.util.List;

public class imperative{
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,3,20,19,11);
        int sum = 0;

        for(int number : numbers){
            if(number > 6 && (number % 2 != 0)){
                sum += number;
            }
        }
        System.out.println(sum);
    }
}

