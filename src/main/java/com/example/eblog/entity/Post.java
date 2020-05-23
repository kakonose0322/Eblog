package com.example.eblog.entity;

import com.example.eblog.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 编辑模式：html可视化，markdown ..
     */
    private String editMode;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 支持人数
     */
    private Integer voteUp;

    /**
     * 反对人数
     */
    private Integer voteDown;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 是否为精华
     */
    private Boolean recommend;

    /**
     * 置顶等级
     */
    private Integer level;
}
