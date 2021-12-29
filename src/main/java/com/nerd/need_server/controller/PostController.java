package com.nerd.need_server.controller;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.request.WritePostRequest;
import com.nerd.need_server.response.BaseResponse;
import com.nerd.need_server.service.AuthService;
import com.nerd.need_server.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @PostMapping("")
    public BaseResponse<Integer> writePost(@RequestHeader("Authorization") String token, @RequestBody WritePostRequest body) throws CustomException {
        return new BaseResponse<>("글을 작성했어요!", HttpStatus.OK.value(), postService.writePost(body, authService.getUserByToken(token)));
    }


}
