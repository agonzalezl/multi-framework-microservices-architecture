package gateway;

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

    public Mono<String> validate() {
        return this.webClient.get().uri("/validate").retrieve().bodyToMono(String.class);
    }

}