package com.example.springboot.controller;

import com.example.springboot.bean.Department;
import com.example.springboot.serivce.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/{id}")
    public String getDept(@PathVariable("id") Integer id){
        Department department = deptService.getDeptById(id);
        return department.toString();
    }

}
