package com.example.eblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zwp
 * @create 2020-05-20 21:59
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping({"","/","index"})
    public String index() {
        // 1.分页信息 2.分类 3.置顶 4.用户 5.精选 6.排序
        IPage results = postService.paging(getPage(),null,null,null,null,"created");
        request.setAttribute("pageData", results);
        request.setAttribute("currentCategoryId", 0);
        return "index";
    }

    @RequestMapping("/search")
    public String search(String q) {
        IPage pageData = searchService.search(getPage(), q);
        request.setAttribute("q", q);
//        request.setAttribute("pageData", "");// 假数据
        request.setAttribute("pageData", pageData);
        return "search";
    }
}
