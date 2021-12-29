package com.nerd.need_server.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WritePostRequest {

    private String title;
    private String content;
    private String photo;
    private String state;
    private int price;

}
