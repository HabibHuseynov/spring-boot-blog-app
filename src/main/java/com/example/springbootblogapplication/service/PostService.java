package com.example.springbootblogapplication.service;

import com.example.springbootblogapplication.model.Post;
import com.example.springbootblogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    public Optional<Post> getById(long id){
        return postRepo.findById(id);
    }

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public Post save(Post post){
        if( post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepo.save(post);
    }

    public void delete(Post post){ postRepo.delete(post);}


}
