package com.example.demo3.service;

import com.example.demo3.pojo.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Admin findByName(String name);
}
