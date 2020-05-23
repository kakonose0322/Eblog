package com.example.eblog.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.common.lang.Result;
import com.example.eblog.entity.Post;
import com.example.eblog.entity.User;
import com.example.eblog.service.UserService;
import com.example.eblog.shiro.AccountProfile;
import com.example.eblog.utils.UploadUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.eblog.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-05-21
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UploadUtil uploadUtil;
    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home() {
        User user = userService.getById(getProfileId());
        List<Post> posts = postService.list(new QueryWrapper<Post>()
                .eq("user_id", getProfileId())
                // 30天内
                //.gt("created", DateUtil.offsetDay(new Date(), -30))
                .orderByDesc("created")
        );
        request.setAttribute("user", user);
        request.setAttribute("posts", posts);
        return "/user/home";
    }

    @GetMapping("/set")
    public String set() {
        User user = userService.getById(getProfileId());
        request.setAttribute("user", user);
        return "/user/set";
    }

    @ResponseBody
    @PostMapping("/set")
    public Result doSet(User user) {
        if(StrUtil.isNotBlank(user.getAvatar())) {
            User temp = userService.getById(getProfileId());
            temp.setAvatar(user.getAvatar());
            userService.updateById(temp);
            AccountProfile profile = getProfile();
            profile.setAvatar(user.getAvatar());
            SecurityUtils.getSubject().getSession().setAttribute("profile", profile);
            return Result.success().action("/user/set#avatar");
        }
        if(StrUtil.isBlank(user.getUsername())) {
            return Result.fail("昵称不能为空");
        }
        int count = userService.count(new QueryWrapper<User>()
                .eq("username", getProfile().getUsername())
                .ne("id", getProfileId()));// 不能为自己
        if(count > 0) {
            return Result.fail("改昵称已被占用");
        }
        User temp = userService.getById(getProfileId());
        temp.setUsername(user.getUsername());
        temp.setGender(user.getGender());
        temp.setSign(user.getSign());
        userService.updateById(temp);
        // 同步shiro缓存与最新信息
        AccountProfile profile = getProfile();
        profile.setUsername(temp.getUsername());
        profile.setSign(temp.getSign());
        SecurityUtils.getSubject().getSession().setAttribute("profile", profile);

        return Result.success().action("/user/set#info");
    }

    @ResponseBody
    @PostMapping("/upload")
    public Result uploadAvatar(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return uploadUtil.upload(UploadUtil.type_avatar, file);
    }

    @ResponseBody
    @PostMapping("/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if(!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }
        User user = userService.getById(getProfileId());
        String nowPassMd5 = SecureUtil.md5(nowpass);
        if(!nowPassMd5.equals(user.getPassword())) {
            return Result.fail("密码不正确");
        }
        user.setPassword(SecureUtil.md5(pass));
        userService.updateById(user);

        return Result.success().action("/user/set#pass");
    }
}
