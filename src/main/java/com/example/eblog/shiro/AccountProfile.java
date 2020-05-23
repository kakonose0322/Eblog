package com.example.eblog.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zwp
 * @create 2020-05-23 9:30
 * @deprecated 统一登陆返回信息
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private String gender;
    private Date create;
    private String sign;
    // 处理性别
    public String getSex() {
        return "0".equals(gender) ? "女" : "男";
    }
}
