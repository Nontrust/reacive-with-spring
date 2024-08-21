package com.reactive.spring.reactive_with_spring.chapter2.src;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

public class HotHttpSequence {
    public static void main(String[] args) throws InterruptedException {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        Mono<String> httpMono  = getWorldTime(uri).cache();
        httpMono.subscribe(date -> System.out.println("1 : " + date));

        Thread.sleep(2000);
        httpMono.subscribe(date -> System.out.println("2 : " + date));

        Thread.sleep(5000);
    }

    private static Mono<String> getWorldTime(URI uri) {
        ObjectMapper objectMapper = new ObjectMapper();
        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(response->getJsonNode(response, "datetime", objectMapper));
    }

    public static String getJsonNode(String json, String path, ObjectMapper objectMapper){
        try {
            JsonNode jsonNode =  objectMapper.readTree(json).path(path);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
