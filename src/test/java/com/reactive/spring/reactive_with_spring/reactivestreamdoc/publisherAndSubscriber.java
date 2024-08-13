package com.reactive.spring.reactive_with_spring.reactivestreamdoc;

import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class publisherAndSubscriber {
    public class publishDocs{
        Publisher<Integer> publisher = new Publisher<Integer>() {
            @Override
            /*
             publisher에 subscribe가 들어와 있음
             Kafka와 같이 topic을 이용한 pub / sub 구조가 아닌
             파라미터를 등록하는 형태
             */
            public void subscribe(Subscriber<? super Integer> subscriber) {}
        };
    }

    public class subscribeDocs {
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            /*
             구독 시작 시점에 어떤 처리를 하는 역할
             */
            public void onSubscribe(Subscription subscription) {}

            @Override
            /*
             publisher가 통지한 데이터 처리
             */
            public void onNext(Integer integer) {}

            @Override
            /*
             pubisher가 통지한 데이터 통지 중 에러발생 시 처리
             */
            public void onError(Throwable throwable) {}

            @Override
            /*
             publisher가 데이터 통지를 완료했을 시 호출
             후처리가 필요하다면 처리코드 작성
             */
            public void onComplete() {}
        };


        
    }
    public class subscriptionDocs {
        Subscription subscription = new Subscription() {
            @Override
            /*
             요청 데이터의 갯수를 지정
             */
            public void request(long l) {}

            @Override
            /*
             구독 해지
             */
            public void cancel() {}
        };
    }
    
    public class processorDocs {
        Processor processor = new Processor() {
            // Publisher와 Susbscriber의 인터페이스를 모두 가지고있음
            @Override
            public void subscribe(Subscriber subscriber) {}

            @Override
            public void onSubscribe(Subscription subscription) {}

            @Override
            public void onNext(Object o) {}

            @Override
            public void onError(Throwable throwable) {}

            @Override
            public void onComplete() {}
        };
    }
}
