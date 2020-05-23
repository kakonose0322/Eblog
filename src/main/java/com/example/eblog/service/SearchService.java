package com.example.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eblog.search.mq.PostMqIndexMessage;
import com.example.eblog.vo.PostVo;

import java.util.List;

public interface SearchService {
    // 从ES中查询数据
    IPage search(Page page, String keyword);
    // 初始化ES数据
    int initEsData(List<PostVo> records);
    // MQ 更新方法
    void createOrUpdateIndex(PostMqIndexMessage message);
    // MQ 删除方法
    void removeIndex(PostMqIndexMessage message);
}
