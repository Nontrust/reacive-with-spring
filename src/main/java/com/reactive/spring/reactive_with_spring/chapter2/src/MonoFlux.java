package com.reactive.spring.reactive_with_spring.chapter2.src;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

public class MonoFlux {
    public static void main(String[] args) {
        Flux<String> sequence = Flux.just("Hello","reactive","Programming");
        sequence.map(f  -> f.toLowerCase())
                .subscribe(System.out::println);

        Mono.just("Hello Reactor")
                .subscribe(System.out::println);

        Mono.empty()
                .subscribe(mono -> System.out.println("emitted on next signal")
                        ,error -> System.out.println("on error")
                        ,() -> System.out.println("emitted on complete signal"));
    }
}
