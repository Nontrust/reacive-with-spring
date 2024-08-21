package com.reactive.spring.reactive_with_spring.chapter2.src.backpressure;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class BackPressureStrategy {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                /** error 전략 (error sign emmit) */
//                .onBackpressureError()
//                .doOnNext(data -> System.out.println("do on next" + data))
                /** drop 전략 (버퍼 대기중인 data drop) */
//                .onBackpressureDrop(drop -> System.out.println("drop : " + drop))
                /** Latest 전략 (버퍼 대기중일 시 마지막을 제외하고 폐기) */
//                .onBackpressureLatest()
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e) {}
                    System.out.println("on Next : " + data);
                }, error -> System.out.println("on error" + error));

        Thread.sleep(2000L);
    }
}
