package com.reactive.spring.reactive_with_spring.chapter2.src;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ColdSequence {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("KOREA","JAPAN","CHINA"))
                .map(String::toLowerCase);

        coldFlux.subscribe(country -> System.out.println("country : "+ country));
        System.out.println("----------------------------------");

        Thread.sleep(2000L);
        coldFlux.subscribe(country -> System.out.println("country 2: "+ country));
    }
}
