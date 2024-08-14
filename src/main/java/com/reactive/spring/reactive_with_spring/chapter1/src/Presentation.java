package com.reactive.spring.reactive_with_spring.chapter1.src;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
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
    public CommandLineRunner run(){
        return (String... args) -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            System.out.println("요청 시작 시간" + LocalDateTime.now());

            LongStream.rangeClosed(1,5)
                    .mapToObj(this::getBook)
                    .forEach(book -> System.out.println(LocalDateTime.now() +":: name ::"+ book.name()));

            stopWatch.stop();

            System.out.println(stopWatch.prettyPrint());
            System.exit(1);
        };
    }


    Book getBook(@PathVariable("book-id") long bookId){
        URI bookUri = UriComponentsBuilder.fromUri(uri)
                .path("/blocking/{book-id}")
                .build()
                .expand(bookId)
                .encode()
                .toUri();

        ResponseEntity<Book> response = restTemplate.getForEntity(bookUri, Book.class);
        return response.getBody();

    }
}
