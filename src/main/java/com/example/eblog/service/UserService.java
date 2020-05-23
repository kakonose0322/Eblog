package com.example.eblog.service;

import com.example.eblog.common.lang.Result;
import com.example.eblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eblog.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-05-21
 */
public interface UserService extends IService<User> {
    Result register(User user);
    AccountProfile login(String username, String password);
}
