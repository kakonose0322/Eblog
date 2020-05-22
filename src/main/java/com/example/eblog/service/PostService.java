package com.example.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eblog.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eblog.vo.PostVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-05-21
 */
public interface PostService extends IService<Post> {
    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String created);
    PostVo selectOnePost(QueryWrapper<Post> wrapper);
    // 初始化每周热评
    void initWeekRank();
    // 增加评论 postId：文章；isIncr：是否增加，否就减1
    void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr);
    // 增加文章的阅读量
    void putViewCount(PostVo vo);
}
