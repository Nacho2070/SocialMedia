package com.PostService.PostService.service;

import com.PostService.PostService.dto.PostDto;
import com.PostService.PostService.persistence.entity.BlogPost;
import com.PostService.PostService.persistence.entity.BlogPostRepository;
import com.PostService.PostService.utils.UserDto;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {

    private final BlogPostRepository blogPostRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WebClient webClient;

    public void createPost(PostDto postDto){

        Mono<UserDto> user = fetchUserById(Long.valueOf(postDto.authorId()));
        log.info("User response: {}", user.block().toString());
        UserDto userDto = user.block();
        BlogPost blogPost = BlogPost.builder()
                .title(postDto.title())
                .content(postDto.content())
                .authorId(userDto.id())
                .build();
        System.out.println("Saving post...");
      //  blogPostRepository.save(blogPost);
        kafkaTemplate.send("PostTopic",blogPost.getBlogPostId().toString());
    }


    public List<PostDto> getAllPosts() {
       List<BlogPost> blogPost = blogPostRepository.findAll();
       List<PostDto> postDtos = new ArrayList<>();
       blogPost.forEach(blogPostDto -> {
           PostDto postDto = new PostDto(blogPostDto.getTitle(),blogPostDto.getAuthorId(),blogPostDto.getContent());
           postDtos.add(postDto);
       });
       return postDtos;
    }

    public PostDto getPostById(String postId) {

        BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(RuntimeException::new);

        return new PostDto(blogPost.getTitle(), blogPost.getAuthorId(), blogPost.getContent());

    }

    public void deletePost(String postId) {
        blogPostRepository.deleteById(postId);

    }

    public List<PostDto> getPostsByUserId(Long userId) {
        Mono<UserDto> userDtoMono = fetchUserById(userId);
       if(userDtoMono.block()==null){
           throw new RuntimeException("User not found");
       }
       List<BlogPost> blogPostList = blogPostRepository.findAllByAuthorId(Long.toString(userId));
       List<PostDto> postDtoList = new ArrayList<>();
       blogPostList.forEach(blogPost -> {
           PostDto postDto = new PostDto(blogPost.getTitle(),blogPost.getContent(),blogPost.getAuthorId());
           postDtoList.add(postDto);
       });
       return postDtoList;
    }


    private Mono<UserDto> fetchUserById(Long userId){
        return webClient.get()
                .uri("/authUser/getUser/{id}",userId)
                .exchangeToMono(this::handleResponse);
    }


    private Mono<UserDto> handleResponse(ClientResponse response) {

        if (response.statusCode().is2xxSuccessful()) {
            return response.bodyToMono(UserDto.class);
        }
        else if (response.statusCode().is4xxClientError()) {
            // Handle client errors (e.g., 404 Not Found)
            return Mono.error(new RuntimeException("User not found"));
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
}
