package com.reactive.spring.reactive_with_spring.chapter2.src.sink;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        final int TASK = 5;

        Flux.create((FluxSink<String> sink) -> {
            IntStream.rangeClosed(1,TASK)
                    .forEach(n -> sink.next(doTask(n)));
        }).subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> System.out.println("create() :: " + n) )
                .publishOn(Schedulers.parallel())
                .map(result -> result + " is success!")
                .doOnNext(n -> System.out.println("map() :: " + n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> System.out.println("on next" + data));

        Thread.sleep(5000);
    }

    private static String doTask(int taskNumber) {
        //now task
        //complete to task

        return "task" +taskNumber + "result";
    }
}
