package com.example.eblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.entity.Category;
import com.example.eblog.mapper.CategoryMapper;
import com.example.eblog.service.CategoryService;
import com.example.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @author zwp
 * @create 2020-05-21 21:21
 * @deprecated ApplicationRunner:项目启动就加载;ServletContextAware:应用级别的注入
 */
@Component
public class ContextConfig implements ApplicationRunner, ServletContextAware {
    @Autowired
    CategoryService categoryService;
    // 全局注入列表信息
    ServletContext servletContext;
    @Autowired
    PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Category> list = categoryService.list(new QueryWrapper<Category>()
                .eq("status", 0)
        );
        postService.initWeekRank();
        servletContext.setAttribute("categorys", list);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
