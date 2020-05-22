package com.example.eblog.vo;

import com.example.eblog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zwp
 * @create 2020-05-21 22:02
 */
@Data
@AllArgsConstructor
public class PostVo extends Post {
    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private String categoryName;
}
