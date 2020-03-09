package com.app.controllers;

import com.app.data.models.BlogPostWrapper;
import com.app.services.BlogService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "blogPost"
})

@RestController
@RequestMapping(value ="/posts", consumes = "application/json", produces = "application/json")
public class BlogPostController {

    @Autowired
    @JsonProperty("blogPost")
    BlogService blogService;

    @CrossOrigin
    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity GetAllPosts(@Param("tag") String tag) {
        BlogPostWrapper bpl = new BlogPostWrapper();
        if(tag == null)
            bpl.setBlogPosts(blogService.getAllPosts());
        else bpl.setBlogPosts(blogService.getPostsByTags(tag));

        for(int i = 0; i<bpl.getBlogPosts().size()+1; i++){
            bpl.setPostsCount(i);
        }
        return new ResponseEntity(bpl, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value ="/{slug}", consumes = "application/json", produces = "application/json")
    public ResponseEntity GetBlogPost(@PathVariable String slug){
        BlogPostWrapper bpw = new BlogPostWrapper();
        bpw.setBlogPost(blogService.getPostBySlug(slug));
   //     bpw.setPostsCount();
        return new ResponseEntity(bpw, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity AddPost(@RequestBody BlogPostWrapper blogPost){
        String s ;
        s = Normalizer.normalize(blogPost.getBlogPost().getTitle(), Normalizer.Form.NFD);
        blogPost.getBlogPost().setSlug(s.toLowerCase()
                .replaceAll("\\p{Punct}", "")
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", "-"));

        blogService.addPost(blogPost.getBlogPost());
        return new ResponseEntity(blogPost, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{slug}", consumes = "application/json", produces = "application/json")
    public  ResponseEntity DeletePost(@PathVariable String slug){
        if(blogService.deletePostBySlug(slug)){
            return  new ResponseEntity("Post deleted", HttpStatus.OK);
        } else return  new ResponseEntity("Post not found", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PutMapping(value = "/{slug}", consumes = "application/json", produces = "application/json")
    public ResponseEntity UpdatePost(@PathVariable String slug,@RequestBody BlogPostWrapper blogPost){
        String s ;
        s = Normalizer.normalize(blogPost.getBlogPost().getTitle(), Normalizer.Form.NFD);
        blogPost.getBlogPost().setSlug(s.toLowerCase()
                .replaceAll("\\p{Punct}", "")
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", "-"));
        blogService.updatePost(slug, blogPost.getBlogPost());
        return new ResponseEntity(blogPost, HttpStatus.OK);
    }



}
