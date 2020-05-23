package com.example.eblog.vo;

import com.example.eblog.entity.UserMessage;
import lombok.Data;

/**
 * @author zwp
 * @create 2020-05-23 13:21
 */
@Data
public class UserMessageVo extends UserMessage {
    private String toUserName;
    private String fromUserName;
    private String postTitle;
    private String commentContent;
}
