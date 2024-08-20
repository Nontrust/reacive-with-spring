package com.reactive.spring.reactive_with_spring.chapter2.src;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotSequence {
    public static void main(String[] args) throws InterruptedException {
        String[] singer = {"Singer A","Singer B","Singer C","Singer D","Singer E","Singer F"};

        Flux<String> conertFlux = Flux.fromArray(singer)
                .delayElements(Duration.ofSeconds(1))
                .share();

        conertFlux.subscribe( singger -> System.out.println("Subscribe " + singger + "`s song"));
        Thread.sleep(2000);

        conertFlux.subscribe( singger -> System.out.println("Subscribe 2 " + singger + "`s song"));
        Thread.sleep(5000);
    }
}
