package com.reactive.spring.reactive_with_spring.chapter2.src.backpressure;

import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class BackPressureBufferStrategy {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(300L))
                .doOnNext(data -> System.out.println("original FLux" + data))
                // buffer 내부 over flow 를 만든 데이터를 drop
                .onBackpressureBuffer(2,
                        drop -> System.out.println("over Flow & drop" + drop),
                        BufferOverflowStrategy.DROP_OLDEST) // buffer에 처음 들어온 데이터 제거
//                        BufferOverflowStrategy.DROP_LATEST) // buffer에 마지막에 들어온 데이터 제거
                .doOnNext(data -> System.out.println("emmit by buffer" + data))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("on Next : " + data);
                }, error -> System.out.println("on error" + error));

        Thread.sleep(3000L);
    }
}
