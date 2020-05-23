package com.example.eblog.config;


import com.example.eblog.common.lang.Consts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zwp
 * @create 2020-05-23 10:44
 * @deprecated 处理上传文件等文件的加载问题
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    Consts consts;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/avatar/**")
                .addResourceLocations("file:///" + consts.getUploadDir() + "/avatar/");
    }
}