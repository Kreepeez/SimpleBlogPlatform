package com.app.controllers;


import com.app.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    BlogService blogService;

    @GetMapping
    public ResponseEntity GetAllTags(){
      return new ResponseEntity(blogService.getAllTags(), HttpStatus.OK);
    }
}
