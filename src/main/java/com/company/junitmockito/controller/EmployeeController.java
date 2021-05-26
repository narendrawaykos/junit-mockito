package com.company.junitmockito.controller;

import com.company.junitmockito.dto.EmployeeList;
import com.company.junitmockito.entity.Employee;
import com.company.junitmockito.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(path = "/employee/{id}", produces = "application/json")
    public Employee getEmpById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping(path = "/employees", produces = "application/json")
    public EmployeeList getEmployees() {
        return  employeeService.getEmployees();
    }

    @PostMapping(path = "/employee/save", consumes = "application/json", produces = "application/json")
    public Employee addEmployee(@Valid  @RequestBody  Employee employee) {
        return employeeService.addEmployee(employee);
    }


}

