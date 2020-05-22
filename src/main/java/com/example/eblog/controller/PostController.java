package com.example.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.eblog.entity.Post;
import com.example.eblog.vo.CommentVo;
import com.example.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.util.Assert;

/**
 * @author zwp
 * @create 2020-05-21 20:35
 */
@Controller
public class PostController extends BaseController {
    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name = "id") Long id) {
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        request.setAttribute("currentCategoryId", id);
        request.setAttribute("pn", pn);
        return "post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        PostVo vo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", id));
        Assert.notNull(vo, "文章已被删除");
        // 增加文章阅读量
        postService.putViewCount(vo);
        // 1分页，2文章id，3用户id，排序
        IPage<CommentVo> results = commentService.paing(getPage(), vo.getId(), null, "created");
        request.setAttribute("currentCategoryId", vo.getCategoryId());
        request.setAttribute("post", vo);
        request.setAttribute("pageData", results);
        return "post/detail";
    }
}
