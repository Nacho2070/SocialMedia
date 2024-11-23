package com.NotificationService.NotificationService.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class PostService {
    /*
    private final WebClient webClient;

    private Mono<UserDto> fetchUserById(PostDto postDto){
        return webClient.get()
                .uri("/authUser/getUser/{id}",postDto.authorId())
                .exchangeToMono(this::handleResponse);
    }


    private Mono<UserDto> handleResponse(ClientResponse response) {

        if (response.statusCode().is2xxSuccessful()) {
            return response.bodyToMono(UserDto.class);
        }
        else if (response.statusCode().is4xxClientError()) {
            // Handle client errors (e.g., 404 Not Found)
            return Mono.error(new RuntimeException("Employee not found"));
        }
        else if (response.statusCode().is5xxServerError()) {
            // Handle server errors (e.g., 500 Internal Server Error)
            return Mono.error(new RuntimeException("Server error"));
        }
        else {
            // Handle other status codes as needed
            return Mono.error(new RuntimeException("Unexpected error"));
        }
    }

     */
}
