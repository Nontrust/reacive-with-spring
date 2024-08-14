package com.reactive.spring.reactive_with_spring.chapter1.src;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequestMapping("/v1/books")
@RestController
public class SpringMvcHeadController {
    private final RestTemplate restTemplate;
    private final URI uri;

    public SpringMvcHeadController(RestTemplate restTemplate, URI baseUri) {
        this.restTemplate = restTemplate;
        this.uri = baseUri;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{book-id}")
    public ResponseEntity<Book> getBook(@PathVariable("book-id") long bookId){
        URI bookUri = UriComponentsBuilder.fromUri(uri)
                .path("/blocking/{book-id}")
                .build()
                .expand(bookId)
                .encode()
                .toUri();
        ResponseEntity<Book> response = restTemplate.getForEntity(bookUri, Book.class);
        Book book = response.getBody();

        return ResponseEntity.ok(book);
    }
}
