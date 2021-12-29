package com.nerd.need_server.controller;

import com.nerd.need_server.request.RegisterRequest;
import com.nerd.need_server.response.BaseResponse;
import com.nerd.need_server.request.LoginRequest;
import com.nerd.need_server.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginRequest loginRequest) throws Exception {
        System.out.println("로그인");
        return new BaseResponse<>("로그인 성공", HttpStatus.OK.value(), authService.login(loginRequest));
    }

    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        authService.register(registerRequest);

        return new BaseResponse<>("회원가입 했어요", 200, null);
    }

}
