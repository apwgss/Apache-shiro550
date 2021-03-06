package com.example.demo3.config;

import com.example.demo3.pojo.Admin;
import com.example.demo3.service.impl.AdminServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminServiceImpl adminService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add"); // 为用户授权
        Subject currentSubject = SecurityUtils.getSubject();
        Object principal = currentSubject.getPrincipal();

        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        // 用户名密码，数据库中取
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        if(!userToken.getUsername().equals("admin")) {
            return null; // 抛出异常，UnknowAccountException
        }

        // 登陆成功之后将user对象存入session内，以供前端获取
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser", userToken.getUsername());

        // 密码认证，shiro做
        return new SimpleAuthenticationInfo(userToken.getUsername(),userToken.getPassword(), "");
    }
}
