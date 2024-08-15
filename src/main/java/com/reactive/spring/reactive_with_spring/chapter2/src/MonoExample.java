package com.reactive.spring.reactive_with_spring.chapter2.src;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

public class MonoExample {

    public static void main(String[] args) throws Exception {
        RestTemplate restTemplate =  new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        ObjectMapper objectMapper = new ObjectMapper();

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();
        Mono.just(restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(httpHeaders), String.class))
                .map(response ->  getJsonNode(response.getBody(), "datetime", objectMapper))
                .subscribe(
                        data -> System.out.println(data)
                        ,error -> System.out.println(error)
                        , () -> System.out.println("onComplete")

                );
        ;

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
