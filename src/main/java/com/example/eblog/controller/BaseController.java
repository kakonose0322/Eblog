package com.example.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eblog.service.*;
import com.example.eblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zwp
 * @create 2020-05-21 21:15
 */
public class BaseController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    UserMessageService messageService;
    @Autowired
    UserCollectionService collectionService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WsService wsService;
//    @Autowired
//    SearchService searchService;
//    @Autowired
//    AmqpTemplate amqpTemplate;
//    @Autowired
//    ChatService chatService;

    // 抽取出的公共分页方法
    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 2);
        return new Page(pn, size);
    }

    protected AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getProfileId() {
        return getProfile().getId();
    }
}
