package com.nerd.need_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "contact")
    private String contact;

    @Column(name = "local")
    private String local;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "user")
    private List<Post> post = new ArrayList<>();

    public User(String id, String password, String contact, String local, String name) {
        this.id = id;
        this.password = password;
        this.contact = contact;
        this.local = local;
        this.name = name;
    }
}
