package com.CommentsService.CommentsService.service;

import com.CommentsService.CommentsService.dto.CommentDTO;
import com.CommentsService.CommentsService.dto.UserDto;
import com.CommentsService.CommentsService.persistence.Comment;
import com.CommentsService.CommentsService.persistence.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WebClient webClient;

    public Comment addComment(CommentDTO commentRequest) {

        Comment comment = Comment.builder()
                .ownerId(commentRequest.ownerId())
                .commenterId(commentRequest.commenterId())
                .postId(commentRequest.postId())
                .content(commentRequest.content())
                .build();

        Mono<UserDto> commenter = fetchUserById(comment.getCommenterId());
        Mono<UserDto> receiver = fetchUserById(comment.getOwnerId());

        log.info("Commenter {}", commenter.block());
        log.info("Receiver {}", receiver.block());

        if(commenter == null || receiver == null) {
            throw new RuntimeException("The commenter or receiver not found");
        }

        // Send a notification via kafka
        kafkaTemplate.send("commentPost",commenter.block().username()+" "+receiver.block().email());

        return commentRepository.save(comment);

    }

    // delete a comment by id.
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // get the comments by post id.
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // get the number of comments on a post by post id.
    public Long countCommentsByPostId(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    public void deleteByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
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
