package com.example.springboot.controller;

import com.example.springboot.bean.Employee;
import com.example.springboot.serivce.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public String getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmpById(id);
        return employee.toString();
    }

    @GetMapping("/emp")
    public String getEmp(Employee employee){
        employeeService.udpateEmp(employee);
        Employee employeeAfter = employeeService.getEmpById(employee.getId());
        return employeeAfter.toString();
    }

    @GetMapping("/delemp/{id}")
    public String delEmp(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

}
