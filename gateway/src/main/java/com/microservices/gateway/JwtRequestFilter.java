package com.microservices.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;

import reactor.core.publisher.Mono;

//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// https://oril.co/blog/spring-cloud-gateway-security-with-jwt/

@RefreshScope
@Component
public class JwtRequestFilter implements GatewayFilter {

    @Autowired
    private SecurityService security;

    //@Autowired
    //private RouterValidator routerValidator;//custom route validator
    //@Autowired
    //private JwtUtil jwtUtil;


    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        System.out.println("Filterino");

            try{
                Mono<String> res = security.validate();
                return res.flatMap(value -> {
                    exchange.getRequest().mutate().header("x-auth-user-id", "jwt").build();
                    return chain.filter(exchange);
                });
            }catch(Exception e){
                e.printStackTrace();
            }


            return chain.filter(exchange);
    }
}



/*

Current best version
  //.onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"))  )
                    
.flatMap(value -> {
                        exchange.getRequest().mutate().header("x-auth-user-id", jwt).build();
                        return chain.filter(exchange);
                    });

*/






            /*

*/


            //if(value==""){
            //    return chain.filter(exchange);
            //}else{
            //    ServerHttpResponse response = exchange.getResponse();
            //    response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //    return response.setComplete();
            //}
            //exchange.getRequest().mutate().header("x-auth-user-id", value).build();
            //return exchange;
            

            /*
            return res.map(value -> {

                if(value==""){
                    exchange.getRequest().mutate().header("x-auth-user-id", value).build();
                    return exchange;
                }
               
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }).flatMap(chain::filter);
            */

            //res.map(value -> {
                
                /*
                if(value==""){
                    exchange.getRequest().mutate().header("x-auth-user-id", value).build();
                    return exchange;
                }
               
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();


                return chain.filter(exchange);
            });

            Mono<String> response = headersSpec.exchangeToMono(response -> {
                if (response.statusCode()
                  .equals(HttpStatus.OK)) {
                    return response.bodyToMono(String.class);
                } else if (response.statusCode()
                  .is4xxClientError()) {
                    return Mono.just("Error response");
                } else {
                    return response.createException()
                      .flatMap(Mono::error);
                }
              });





        }catch(Exception e){
            e.printStackTrace();
            
        }



/*









*/




        //ServerHttpResponse response = exchange.getResponse();
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //return response.setComplete();

        /*
        if (!isNotSecured.test(request)) {

            if(!request.getHeaders().containsKey("Authorization")) {

                //ServerHttpResponse response = exchange.getResponse();
                //response.setStatusCode(HttpStatus.UNAUTHORIZED);
                //return response.setComplete();

            }
            


        }

*/

        /*
        if (isSecured.test(request)) {
            if (this.isAuthMissing(request))
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

            final String token = this.getAuthHeader(request);

            if (jwtUtil.isInvalid(token))
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);

            this.populateRequestWithHeaders(exchange, token);
        }*/
        //return chain.filter(exchange);
    //}


    /*PRIVATE*/
/*
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
*/

/*
public static final List<String> openApiEndpoints= List.of(
    "/authorization/"
);
*/


//}

