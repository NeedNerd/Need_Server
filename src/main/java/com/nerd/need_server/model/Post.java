package com.nerd.need_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private String date;

    @Column(name = "photo")
    private String photo;

    @Column(name = "state")
    private String state;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx")
    private User user;

}
