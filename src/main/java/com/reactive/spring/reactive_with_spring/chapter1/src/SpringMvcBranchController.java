package com.reactive.spring.reactive_with_spring.chapter1.src;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.LongStream;

@RequestMapping("/v1/books/data")
@RestController
public class SpringMvcBranchController {
    private final ConcurrentMap<Long, Book> datasource;

    public SpringMvcBranchController() {
        ConcurrentMap<Long, Book> datasource = new ConcurrentHashMap<>();
        LongStream.rangeClosed(1,10)
                .mapToObj(i -> new Book(i, i+"번째 책"))
                .forEach( b -> datasource.put(b.id(), b));

        this.datasource = datasource;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/blocking/{book-id}")
    public ResponseEntity<Book> getBook(@PathVariable("book-id") long bookId) throws InterruptedException {
        Thread.sleep(5000);
        Book book = datasource.get(bookId);

        return ResponseEntity.ok(book);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/non-blocking/{book-id}")
    public Mono<Book> getNonBlockingBook(@PathVariable("book-id") long bookId) throws InterruptedException {
        Thread.sleep(5000);
        Book book = datasource.get(bookId);

        return Mono.just(book);
    }
}
