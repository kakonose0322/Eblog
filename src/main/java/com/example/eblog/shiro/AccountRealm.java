package com.example.eblog.shiro;

import com.example.eblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zwp
 * @create 2020-05-23 9:27
 */
@Component// 注册为组件，不再需要在其它使用的地方使用@Bean
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken =  (UsernamePasswordToken) token;
        AccountProfile profile = userService.login(usernamePasswordToken.getUsername(),String.valueOf(usernamePasswordToken.getPassword()));
        SecurityUtils.getSubject().getSession().setAttribute("profile", profile);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile,token.getCredentials(),getName());
        return info;
    }
}
