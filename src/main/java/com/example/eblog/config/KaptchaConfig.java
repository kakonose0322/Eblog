package com.example.eblog.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

/**
 * @author zwp
 * @create 2020-05-23 7:43
 * @deprecated 图片验证码的规则
 */
@Configuration
public class KaptchaConfig {
    // 规则网上可以官网下载示例
    @Bean
    public DefaultKaptcha producer () {
        Properties propertis = new Properties();
        propertis.put("kaptcha.border", "no");// 不要边框
        propertis.put("kaptcha.image.height", "38");// 图片高度 38px
        propertis.put("kaptcha.image.width", "150");//  长度 150
        propertis.put("kaptcha.textproducer.font.color", "black");// 颜色
        propertis.put("kaptcha.textproducer.font.size", "32");// size 32位
        Config config = new Config(propertis);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
