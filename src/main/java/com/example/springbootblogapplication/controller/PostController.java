package com.example.springbootblogapplication.controller;

import com.example.springbootblogapplication.model.Account;
import com.example.springbootblogapplication.model.Post;
import com.example.springbootblogapplication.service.AccountService;
import com.example.springbootblogapplication.service.PostService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model){
        Optional<Post> postOptional = postService.getById(id);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            model.addAttribute("post",post);
            return "post";
        }else{
            return "404";
        }

    }

    @GetMapping("/post/new")
    public String createNewPost(Model model){
        Optional<Account> accountOpt = accountService.findByEmail("user.user@domain.com");

        if(accountOpt.isPresent()) {
            Post post = new Post();
            post.setAccount(accountOpt.get());
            model.addAttribute("post", post);
            return "post_new";
        }else{
            return "404";
        }
    }

    @PostMapping("/post/new")
    public String saveNewPost(@ModelAttribute Post post){
        postService.save(post);
        return "redirect:/post/"+post.getId();
    }

    @GetMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model){
        Optional<Post> postOpt = postService.getById(id);
        if(postOpt.isPresent()){
            Post post = postOpt.get();
            model.addAttribute("post",post);
            return "post_edit";
        }else{
            return "404";
        }

    }

    @PostMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id,@ModelAttribute Post post) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

            postService.save(existingPost);
        }

        return "redirect:/post/" + post.getId();
    }


    @GetMapping("/post/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id){
        Optional<Post> postOpt = postService.getById(id);
        if(postOpt.isPresent()){
            Post post = postOpt.get();

            postService.delete(post);
            return "redirect:/";
        }
        else{
            return "404";
        }
    }

}
