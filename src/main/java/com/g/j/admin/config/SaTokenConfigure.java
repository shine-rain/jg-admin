package com.g.j.admin.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器；注解鉴权
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 未使用文件存储服务器 临时使用本地存储 静态资源请求不拦截
            SaRouter.match("/static/img/**").stop();
            // 超级管理员不做拦截处理
            if (!StpUtil.hasRole("super-admin")) {
                // 除了登录 拦截所有请求验证是否登录
                SaRouter.match("/**", r -> StpUtil.checkLogin());
                // 系统权限 拦截系统路由，需要具备 admin 角色 可以访问
                SaRouter.match("/system/**", r -> StpUtil.checkRole("admin"));
                // TODO
            }
        }).isAnnotation(true)).addPathPatterns("/**").excludePathPatterns("/system/login");
    }
}
