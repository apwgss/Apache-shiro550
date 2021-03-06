package com.example.demo3;

import com.example.demo3.pojo.Admin;
import com.example.demo3.service.impl.AdminServiceImpl;
import org.apache.shiro.util.AntPathMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo3ApplicationTests {

    @Autowired
    private AdminServiceImpl adminService;

    @Test
    void contextLoads() {
        Admin kevin = adminService.findByName("kevin");
        System.out.println(kevin);
    }

    @Test
    void testShiro() {
        AntPathMatcher matcher = new AntPathMatcher();
        boolean matches = matcher.matches("/admin/*", "/admin /");
        System.out.println(matches);
        String test = ";";
        int num = test.indexOf(59);
        System.out.println(num);
    }

}
