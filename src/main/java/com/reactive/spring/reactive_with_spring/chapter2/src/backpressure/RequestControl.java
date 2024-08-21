package com.reactive.spring.reactive_with_spring.chapter2.src.backpressure;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class RequestControl {
    public static void main(String[] args) throws Exception {
        Flux.range(1,5)
                .doOnRequest(data ->System.out.println("do on request ::: "+data))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("on hook ::: " + value);
                        request(1);
                    }
                });
    }
}
