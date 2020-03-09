package com.app.repositories;

import com.app.data.models.BlogPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BlogRepository extends CrudRepository<BlogPost, String> {

    @Query(value = "select * from posts join blog_post_tags bpt on posts.slug = bpt.blog_post_slug where tags = ?1", nativeQuery = true)
    List<BlogPost> findBlogPostByTags(String tags);

}
