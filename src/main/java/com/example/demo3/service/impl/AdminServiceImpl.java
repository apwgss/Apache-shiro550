package com.example.demo3.service.impl;

import com.example.demo3.mapper.AdminMapper;
import com.example.demo3.pojo.Admin;
import com.example.demo3.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByName(String name) {
        return adminMapper.findByName(name);
    }
}
