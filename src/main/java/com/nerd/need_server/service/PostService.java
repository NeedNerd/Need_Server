package com.nerd.need_server.service;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.model.Post;
import com.nerd.need_server.model.User;
import com.nerd.need_server.repository.PostRepository;
import com.nerd.need_server.request.WritePostRequest;
import com.nerd.need_server.response.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;

    DateTimeFormatter dateTimeFormatter;

    public PostService(PostRepository postRepository, AuthService authService) {
        this.postRepository = postRepository;
        this.authService = authService;
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public Integer writePost(WritePostRequest request, String token) throws CustomException {
        LocalDate date = LocalDate.now();
        User user = authService.getUserByToken(token);
        Post post = new Post(
                0,
                request.getTitle(),
                request.getContent(),
                date.format(dateTimeFormatter),
                request.getPhoto(),
                request.getState(),
                request.getPrice(),
                user
        );

        if(request.getState().equals("1") && authService.getInfo(token).getCount() <= 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "한달에 두 번만 나눔 게시글을 올릴 수 있습니다");
        }

        try {
            return postRepository.save(post).getIdx();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public List<PostResponse> getValidPost(String state) {
        return postRepository.getFilteredPost(state).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public List<PostResponse> getValidPost() {
        return postRepository.getValidPost().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public List<PostResponse> getMyPost(User user, String state) {
        return postRepository.getMyFilteredPost(user.getIdx(), state).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public List<PostResponse> getMyPost(User user) {
        return postRepository.getMyPost(user.getIdx()).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public PostResponse getPostByIdx(int idx) throws CustomException {
        return postRepository.findById(idx).map(PostResponse::new).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다"));
    }

    public void modifyState(int postIdx, String state) throws CustomException {
        Optional<Post> post = postRepository.findById(postIdx);
        post.orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND, "게시글이 없어요!"));

        if(post.get().getState().equals(state)) throw new CustomException(HttpStatus.CONFLICT, "변경 사항이 없어요!");

        post.get().setState(state);

        postRepository.save(post.get());
    }

}
