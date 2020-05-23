package com.example.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eblog.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eblog.vo.UserMessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-05-21
 */
public interface UserMessageMapper extends BaseMapper<UserMessage> {
    IPage<UserMessageVo> selectMessages(Page page, @Param(Constants.WRAPPER) QueryWrapper<UserMessage> wrapper);

    @Transactional
    @Update("update user_message set status = 1 ${ew.customSqlSegment}")
    void updateToReaded(QueryWrapper<UserMessage> id);
}
