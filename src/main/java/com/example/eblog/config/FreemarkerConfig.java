package com.example.eblog.config;

import com.example.eblog.templates.HotsTemplate;
import com.example.eblog.templates.PostsTemplate;
import com.example.eblog.templates.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    PostsTemplate postsTemplate;
    @Autowired
    HotsTemplate hotsTemplate;

    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
        configuration.setSharedVariable("hots", hotsTemplate);
//        configuration.setSharedVariable("shiro", new ShiroTags());
    }

}