package com.nerd.need_server.controller;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.model.User;
import com.nerd.need_server.request.WritePostRequest;
import com.nerd.need_server.response.BaseResponse;
import com.nerd.need_server.response.PostResponse;
import com.nerd.need_server.service.AuthService;
import com.nerd.need_server.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @PostMapping("")
    public BaseResponse<Integer> writePost(@RequestHeader("Authorization") String token, @RequestBody WritePostRequest body) throws CustomException {
        return new BaseResponse<>("글을 작성했어요!", HttpStatus.OK.value(), postService.writePost(body, token));
    }

    @GetMapping("/valid")
    public BaseResponse<List<PostResponse>> getAllPost(@RequestParam(value = "state", required = false) Optional<String> state) {
        return state.map(
                s -> new BaseResponse<>("조회했어요!", HttpStatus.OK.value(), postService.getValidPost(s))
        ).orElseGet(() ->
                new BaseResponse<>("조회했어요!", HttpStatus.OK.value(), postService.getValidPost())
        );
    }

    @GetMapping("/my")
    public BaseResponse<List<PostResponse>> getMyPost(@RequestHeader("Authorization") String token, @RequestParam(value = "state", required = false) Optional<String> state) {
        User user = authService.getUserByToken(token);
        return state.map(
                s -> new BaseResponse<>("조회했어요", HttpStatus.OK.value(), postService.getMyPost(user, s))
        ).orElseGet(() ->
                new BaseResponse<>("조회했어요", HttpStatus.OK.value(), postService.getMyPost(user))
        );
    }

    @GetMapping("/{idx}")
    public BaseResponse<PostResponse> getPostByIdx(@PathVariable int idx) throws CustomException {
        return new BaseResponse<>("조회했어요", HttpStatus.OK.value(), postService.getPostByIdx(idx));
    }

    @PatchMapping("/{idx}")
    public BaseResponse<Void> modifyState(@RequestParam String state, @PathVariable int idx) throws CustomException {
        postService.modifyState(idx, state);
        return new BaseResponse<>("변경했어요", HttpStatus.OK.value(), null);
    }


}
