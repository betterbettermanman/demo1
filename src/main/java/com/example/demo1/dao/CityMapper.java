package com.example.demo1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CityMapper {
    List<Map<String,Object>> selectCode(@Param("name") String name);
}
