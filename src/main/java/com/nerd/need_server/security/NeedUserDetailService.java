package com.nerd.need_server.security;

import com.nerd.need_server.model.User;
import com.nerd.need_server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NeedUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public NeedUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserById(username);

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), Collections.emptyList());
    }
}
