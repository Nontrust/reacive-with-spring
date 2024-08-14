package com.reactive.spring.reactive_with_spring.chapter1.src;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

@Slf4j
@Component
public class Presentation {

    private final URI uri;
    private final RestTemplate restTemplate;

    public Presentation(URI uri, RestTemplate restTemplate) {
        this.uri = uri;
        this.restTemplate = restTemplate;
    }


    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            StopWatch stopWatch = new StopWatch();
            System.out.println("Blocking code 요청 시작 시간" + LocalDateTime.now());
            stopWatch.start();

            LongStream.rangeClosed(1,5)
                    .mapToObj(this::getBook)
                    .forEach(book -> System.out.println(LocalDateTime.now() +":: name ::"+ book.name()));

            stopWatch.stop();

            System.out.println(stopWatch.prettyPrint());

            System.out.println("MonoList :: Non - Blocking code 요청 시작 시간" + LocalDateTime.now());

            stopWatch.start();

            List<Mono<Book>> monoBooks = LongStream.rangeClosed(1, 5)
                    .mapToObj(this::getNonBlockingBook)
                    .toList();

            Mono.zip(monoBooks, objects ->
                                Arrays.stream(objects).map(object->(Book) object)
                                        .peek(System.out::println)
                                        .toList())
                            .doOnTerminate(() -> {
                                System.out.println("Non-Blocking code 요청 종료 시간: " + LocalDateTime.now());
                                stopWatch.stop();
                                System.out.println(stopWatch.prettyPrint());
                            }).subscribe();


            /*
            System.out.println("Flux :: Non - Blocking code 요청 시작 시간" + LocalDateTime.now());
            stopWatch.start();
            Flux<Book> bookFlux = Flux
                    .fromStream(LongStream.rangeClosed(1, 5)
                            .mapToObj(
                                    l -> getNonBlockingBook(l)
                                            .subscribeOn(Schedulers.parallel())
                                            .block()
                            ));

            Flux.zip(bookFlux, Subscriber::onComplete)
                    .doOnTerminate(() -> {
                        System.out.println("Non-Blocking code 요청 종료 시간: " + LocalDateTime.now());
                        stopWatch.stop();
                        System.out.println(stopWatch.prettyPrint());
                    }).subscribe();

             */
        };
    }


    Book getBook(long bookId) {
        URI bookUri = UriComponentsBuilder.fromUri(uri)
                .path("/blocking/{book-id}")
                .build()
                .expand(bookId)
                .encode()
                .toUri();

        ResponseEntity<Book> response = restTemplate.getForEntity(bookUri, Book.class);
        return response.getBody();

    }

    Mono<Book> getNonBlockingBook(long bookId) {
        URI bookUri = UriComponentsBuilder.fromUri(uri)
                .path("/non-blocking/{book-id}")
                .build()
                .expand(bookId)
                .encode()
                .toUri();

        return WebClient.create()
                .get()
                .uri(bookUri)
                .retrieve()
                .bodyToMono(Book.class);
    }
}
