package com.example.demo3.mapper;

import com.example.demo3.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminMapper {

    Admin findByName(String name);
}
