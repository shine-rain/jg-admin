package com.g.j.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/img/**")
                .addResourceLocations("file:///" + uploadConfig.getLocal().getUploadPath());
    }
}
