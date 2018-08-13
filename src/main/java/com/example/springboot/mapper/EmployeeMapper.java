package com.example.springboot.mapper;

import com.example.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMapper {

    @Select("select * from mybatis_employee where id=#{id}")
    public Employee getEmpById(Integer id);

    @Update("update mybatis_employee set last_name=#{lastName}, email=#{email}, d_id=#{dId}")
    public void updateEmp(Employee emp);

    @Delete("delete from mybatis_employee where id=#{id}")
    public void deleteEmoById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into mybatis_employee(last_name,email,d_id) values(#{lastName}, #{email}, #{dId})")
    public Employee insertEmp(Employee employee);

    @Select("select * from mybatis_employee where last_name=#{lastName}")
    public Employee getEmpByLastName(String lastName);
}
