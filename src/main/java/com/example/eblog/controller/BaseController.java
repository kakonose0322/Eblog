package com.example.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eblog.service.CommentService;
import com.example.eblog.service.PostService;
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

    // 抽取出的公共分页方法
    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 2);
        return new Page(pn, size);
    }
}
