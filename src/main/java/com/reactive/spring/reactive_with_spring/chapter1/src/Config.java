package com.reactive.spring.reactive_with_spring.chapter1.src;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Configuration
public class Config {

    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }



    @Bean
    public UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.newInstance();
    }

    @Bean
    public URI baseUri(UriComponentsBuilder uriComponentsBuilder){
        return uriComponentsBuilder
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/v1/books")
                .build()
                .encode().toUri();
    }

}
