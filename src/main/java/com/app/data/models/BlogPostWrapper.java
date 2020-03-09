package com.app.data.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "blogPost",
        "postCount"
})

public class BlogPostWrapper {



    @JsonProperty("blogPost")
    private BlogPost blogPost;

    private List<BlogPost> blogPosts;

    @JsonProperty("blogPost")
    public BlogPost getBlogPost() {
        return blogPost;
    }

    @JsonProperty("blogPost")
    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    @JsonProperty("postsCount")
    private int postsCount;

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    @JsonProperty("postsCount")
    public int getPostsCount() { return postsCount; }

    @JsonProperty("postsCount")
    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public BlogPostWrapper(){
    }
}
