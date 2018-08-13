package com.example.springboot.mapper;

import com.example.springboot.bean.Department;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMapper {

    @Select("select * from mybatis_department where id=#{id}")
    public Department getDeptById(Integer id);
}
