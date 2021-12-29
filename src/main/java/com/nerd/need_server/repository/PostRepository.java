package com.nerd.need_server.repository;

import com.nerd.need_server.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT p FROM Post p WHERE p.state = '0' OR p.state = '1' ORDER BY p.idx DESC")
    List<Post> getValidPost();

    @Query(value = "SELECT p FROM Post p WHERE p.state = :option ORDER BY p.idx DESC")
    List<Post> getFilteredPost(String option);

    @Query(value = "SELECT p FROM Post p WHERE p.user.idx = :idx ORDER BY p.idx DESC ")
    List<Post> getMyPost(int idx);

    @Query(value = "SELECT p FROM Post p WHERE p.user.idx = :idx AND p.state = :option ORDER BY p.idx DESC ")
    List<Post> getMyFilteredPost(int idx, String option);

}
