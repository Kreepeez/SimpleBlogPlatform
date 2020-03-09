package com.app.data.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.persistence.Table;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
        "tag"
)

@Table(name = "blog_post_tags")
public class Tags {

    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTagList(List<String> tags) {
        this.tags = tags;
    }


    public Tags(List<String> tags){
        this.tags = tags;
    }
}
