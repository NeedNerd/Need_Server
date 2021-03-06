package com.nerd.need_server.service;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.model.User;
import com.nerd.need_server.repository.UserRepository;
import com.nerd.need_server.request.LoginRequest;
import com.nerd.need_server.request.RegisterRequest;
import com.nerd.need_server.response.InfoResponse;
import com.nerd.need_server.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final DateTimeFormatter dateTimeFormatter;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public String login(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "일치하는 회원 정보가 없습니다");
        }

        return jwtUtil.generateToken(loginRequest.getId());
    }

    public void register(RegisterRequest registerRequest) throws Exception {

        User user = new User(registerRequest.getId(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getContact(), registerRequest.getLocal(), registerRequest.getName());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.CONFLICT, "동일한 아이디가 이미 있어요");
        }
    }

    public User getUserByToken(String token) {
        return userRepository.getUserById(jwtUtil.extractUsername(token));
    }

    public InfoResponse getInfo(String token) {
        InfoResponse infoResponse = new InfoResponse();
        User user = getUserByToken(token);

        infoResponse.setUserIdx(user.getIdx());
        infoResponse.setName(user.getName());

        String beforeMonth = LocalDate.now().minusMonths(1).format(dateTimeFormatter);
        System.out.println(beforeMonth);
        int count = (int) (2 - user.getPost().stream().filter(it->it.getState().equals("1") && beforeMonth.compareTo(it.getDate())<0).count());

        infoResponse.setCount(count);

        return infoResponse;
    }
}
