package com.reactive.spring.reactive_with_spring.chapter2.src;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxExample {
    public static void main(String[] args) {
        Flux.just(6,9,3)
                .map(num -> num%2)
                .subscribe(System.out::println);

        Flux.fromArray(new Integer[] {3,6,7,9})
                .filter(num -> num > 6)
                .map(num ->  num * 2)
                .subscribe(System.out::println);

        Flux<String> flux = Mono.justOrEmpty("Non")
                .concatWith(Mono.justOrEmpty("Trust"));
        flux.subscribe(System.out::println);

        Flux.concat(
                Flux.just("이종대","부장","PL"),
                Flux.just("김대영","부장","AA"),
                Flux.just("김학민","차장","FE TL"),
                Flux.just("김혜정","과장","FE"),
                Flux.just("신원철","대리","BE"),
                Flux.just("이충호","대리","BE"),
                Flux.just("김영지","대리","FE"),
                Flux.just("정승호","주임","FE")
        ).collectList()
                .subscribe(System.out::println);
    }
}
