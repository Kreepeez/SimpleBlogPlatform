package com.app.services;

import com.app.data.models.BlogPost;
import com.app.data.models.BlogPostWrapper;
import com.app.data.models.Tags;
import com.app.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public List<BlogPost> getAllPosts() {
        BlogPostWrapper bpw = new BlogPostWrapper();
        List<BlogPost> recentPosts = new ArrayList<>();
        List<BlogPost> allPosts = (List<BlogPost>) blogRepository.findAll();

        if(allPosts.size() >= 5){
            for (int i = allPosts.size()-1; i >= allPosts.size()-5; i-- ){
                recentPosts.add(allPosts.get(i));
            }
        }else
            recentPosts = allPosts;

        bpw.setBlogPosts(recentPosts);
        return bpw.getBlogPosts();
    }


    public List<BlogPost> getPostsByTags(String tags){
      BlogPostWrapper bpw = new BlogPostWrapper();
      List<BlogPost> postsByTags = blogRepository.findBlogPostByTags(tags);
      bpw.setBlogPosts(postsByTags);
      return bpw.getBlogPosts();
    }

    public Tags getAllTags(){
      List<BlogPost> allPosts = (List<BlogPost>) blogRepository.findAll();
      List<String> tagss = new ArrayList<>();
      for(int i = 0; i<allPosts.size(); i++){
          for(int j = 0; j< allPosts.get(i).getTags().size(); j++) {
              tagss.add(allPosts.get(i).getTags().get(j));
          }
      }
      List<String> newList = removeDuplicates((ArrayList<String>) tagss);
      Tags tags = new Tags(newList);

        return tags;
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> list) {

        ArrayList<String> newList = new ArrayList<String>();
        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    public BlogPost getPostBySlug(String slug) {
        List<BlogPost> allPosts = (List<BlogPost>) blogRepository.findAll();
        BlogPost blogBySlug = null;
        for (BlogPost blogPost : allPosts) {
            if (blogPost.getSlug().equals(slug))
                try {
                      blogBySlug = blogPost;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
        }
        return blogBySlug;
    }

    public boolean addPost(BlogPost blogpost) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

        BlogPost newPost = new BlogPost(blogpost.getSlug(), blogpost.getTitle(), blogpost.getDescription(),
                blogpost.getBody(), blogpost.getTags(), dateFormat.format(date), dateFormat.format(date) );

        if (blogpost.getTitle() != null && blogpost.getDescription() != null && blogpost.getBody() != null) {
            blogRepository.save(newPost);
            return true;
        } else return false;
    }

    public boolean deletePostBySlug(String slug){
        List<BlogPost> allPosts = (List<BlogPost>)blogRepository.findAll();
        boolean success = false;
        for(BlogPost blogPost : allPosts){
            if(blogPost.getSlug().equals(slug)) {
                blogRepository.delete(blogPost);
                success = true;
            } else success = false;
        }
        return success;
    }

    public boolean updatePost(String slug, BlogPost updatedPost){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

        List<BlogPost> allPosts = (List<BlogPost>) blogRepository.findAll();
        for(BlogPost blogPost : allPosts){
            if(blogPost.getSlug().equals(slug)){

                if(updatedPost.getTitle() == null)
                updatedPost.setTitle(blogPost.getTitle());

                if(updatedPost.getDescription() == null)
                updatedPost.setDescription(blogPost.getDescription());

                if(updatedPost.getTags() == null)
                updatedPost.setTags(blogPost.getTags());

                if(updatedPost.getBody() == null)
                updatedPost.setBody(blogPost.getBody());

                updatedPost.setCreatedAt(blogPost.getCreatedAt());
                updatedPost.setUpdatedAt(dateFormat.format(date));
                deletePostBySlug(slug);
                blogRepository.save(updatedPost);
            }
        }
        return true;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<String> bpTags = new ArrayList<>();
        bpTags.add("iOS");
        bpTags.add("AR");
        BlogPost bp = new BlogPost("augmented-reality-ios-application",
                "Augmented Reality iOS Application",
                "Rubicon Software Development and Gazzda furniture are proud to launch an augmented reality app.",
                "The app is simple to use, and will help you decide on your best furniture fit.",
                bpTags,
                "2018-05-18T03:22:56.637Z",
                "2018-05-18T03:48:35.824Z");
        blogRepository.save(bp);

        bpTags.clear();
        bpTags.add("iOS");
        bpTags.add("AR");
        bpTags.add("Gazzda");
        BlogPost bp2 = new BlogPost("augmented-reality-ios-application-2",
                "Augmented Reality iOS Application 2",
                "Rubicon Software Development and Gazzda furniture are proud to launch an augmented reality app.",
                "The app is simple to use, and will help you decide on your best furniture fit.",
                bpTags,
                "2018-04-18T03:22:56.637Z",
                "2018-04-18T03:48:35.824Z");
        blogRepository.save(bp2);
    }

}
