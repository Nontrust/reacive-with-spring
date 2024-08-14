package com.reactive.spring.reactive_with_spring.updownstream;

import reactor.core.publisher.Flux;

public class exam  {
    public static void main(String[] args) {
        // Just와 Filter중 just가 upstream , filter가 downstream
        Flux
                .just(1,2,3,4,5,6) // 퍼블리셔
                .filter(n -> n%2 == 0)
                .map(n -> n*2)
                .subscribe(System.out::println);
    }
}

