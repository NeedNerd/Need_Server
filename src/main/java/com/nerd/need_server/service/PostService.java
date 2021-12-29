package com.nerd.need_server.service;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.model.Post;
import com.nerd.need_server.model.User;
import com.nerd.need_server.repository.PostRepository;
import com.nerd.need_server.request.WritePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PostService {

    private final PostRepository postRepository;

    DateTimeFormatter dateTimeFormatter;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public Integer writePost(WritePostRequest request, User writer) throws CustomException {
        LocalDate date = LocalDate.now();
        Post post = new Post(
                0,
                request.getTitle(),
                request.getContent(),
                date.format(dateTimeFormatter),
                request.getPhoto(),
                request.getState(),
                writer
        );

        try {
            return postRepository.save(post).getIdx();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
