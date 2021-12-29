package com.nerd.need_server.response;

import com.nerd.need_server.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private int idx;
    private String title;
    private String content;
    private String state;
    private int price;
    private String photo;
    private String date;
    private String writer;
    private int userIdx;
    private String local;
    private String contact;

    public PostResponse(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.photo = post.getPhoto();
        this.idx = post.getIdx();
        this.state = post.getState();
        this.price = post.getPrice();
        this.date = post.getDate();
        this.writer = post.getUser().getName();
        this.userIdx = post.getUser().getIdx();
        this.local = post.getUser().getLocal();
        this.contact = post.getUser().getContact();
    }

}
