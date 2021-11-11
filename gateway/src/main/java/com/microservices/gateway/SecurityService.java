package com.microservices.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

@Service
public class SecurityService {

    private final WebClient webClient;

    public SecurityService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://security:8080").build();
    }
/*
    public Mono<String> validate(String token) throws Exception {

    //public .toEntity(String.class);  validate(String token) {

        return this.webClient.post().uri("/validate").header("token", token).retrieve().bodyToMono(String.class);
        
    }*/


    
    public Mono<String> validate(String token) {
        
       // return this.webClient.get().uri("/validate").bodyToMono(String.class);
        
        
        return this.webClient.get().uri("/validate").header("token", token)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    //Mono<String> value = response.bodyToMono(String.class).defaultIfEmpty("");
                    //System.out.println("Hola!!! "+value);
                    return Mono.just("");
                    //return  value;//Mono.empty().then(); //response.bodyToMono(String.class);
                } else { // maybe this is not necessary
                    return response.createException().flatMap(Mono::error);
                    //return response.bodyToMono(String.class);
                    //return Mono.just("");
                    //return  response.bodyToMono(String.class).defaultIfEmpty("");
                }
            });

        
    }
            
}