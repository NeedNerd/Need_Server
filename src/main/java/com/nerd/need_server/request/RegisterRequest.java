package com.nerd.need_server.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String id;
    private String password;
    private String contact;
    private String local;
    private String name;

}
