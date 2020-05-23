package com.example.eblog.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.eblog.common.lang.Result;
import com.example.eblog.entity.User;
import com.example.eblog.service.UserService;
import com.example.eblog.utils.ValidationUtil;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author zwp
 * @create 2020-05-23 7:32
 * @deprecated 登陆控制器
 */
@Controller
public class AuthController extends BaseController {
    // capthca 在session中的名字
    private static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";
    @Autowired
    Producer producer;// 验证码生成工具
    @Autowired
    UserService userService;

    @GetMapping("/capthca.jpg")
    public void kaptcha(HttpServletResponse response) throws IOException {
        // 验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);// 生成图片
        request.getSession().setAttribute(KAPTCHA_SESSION_KEY, text);
        // 设置响应格式
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();// 返回图片文件
        ImageIO.write(image, "jpg", outputStream);
    }

    @RequestMapping("/login")
    public String login() {
        return "/auth/login";
    }
    @RequestMapping("/register")
    public String register() {
        return "/auth/reg";
    }

    /**
     * 注册，跳转登陆
     * @param user 提交的参数
     * @param repass 没有的确认密码
     * @param vercode 缺少的验证码
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public Result doRegister(User user,String repass,String vercode) {
        // 校验输入
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if(validResult.hasErrors()) {
            return Result.fail(validResult.getErrors());
        }
        if(!user.getPassword().equals(repass)) {
            return Result.fail("两次输入密码不相同");
        }
        String capthca = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        System.out.println(capthca);
        if(vercode == null || !vercode.equalsIgnoreCase(capthca)) {
            return Result.fail("验证码输入不正确");
        }
        // 完成注册
        Result result = userService.register(user);
        return result.action("/login");
    }

    @ResponseBody
    @PostMapping("/login")
    public Result doLogin(String email, String password) {
        if(StrUtil.isEmpty(email) || StrUtil.isBlank(password)) {
            return Result.fail("邮箱或密码不能为空");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(email, SecureUtil.md5(password));
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return Result.fail("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.fail("密码错误");
            } else {
                return Result.fail("用户认证失败");
            }
        }
        return Result.success().action("/");
    }

    @RequestMapping("/user/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
