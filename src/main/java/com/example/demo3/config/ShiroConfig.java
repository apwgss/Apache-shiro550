package com.example.demo3.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // ShiroFilterFactoryBean对象,方法名必须为 shiroFilterFactoryBean，否则在启动的时候会报错找不到对应的bean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(securityManager);

        // 添加shiro的内置过滤器
        /*
            anon：  无需认证就可以访问
            authc： 必须认证了才能访问
            user：  必须拥有 记住我 功能才能访问
            perms： 拥有对某个资源的权限才能访问
            role：   拥有某个角色的权限才能访问
        */

        Map<String, String> authMap = new LinkedHashMap<>();

        authMap.put("/user/*", "user");
        authMap.put("/admin/**", "authc");
        authMap.put("/logout", "logout");
        bean.setFilterChainDefinitionMap(authMap);

        // 设置登陆的请求
        bean.setLoginUrl("/toLogin");
        // 设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }

    // DefaultWebSecurityManager对象
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 创建Realm对象，需要自定义
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    // 整合ShiroDialect：用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
