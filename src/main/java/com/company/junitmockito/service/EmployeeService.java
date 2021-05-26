package com.company.junitmockito.service;

import com.company.junitmockito.dto.EmployeeList;
import com.company.junitmockito.entity.Employee;
import com.company.junitmockito.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    public Employee getEmployeeById(@PathVariable int id) {
        Optional<Employee> empOptional = this.employeeRepository.findById(id);
        return empOptional.isEmpty() ? empOptional.get() : new Employee();
    }

    public EmployeeList getEmployees() {
        EmployeeList response = new EmployeeList();
//        employeeRepository.findAll().forEach(e -> list.add(e));
        ArrayList<Employee> list = new ArrayList<>(employeeRepository.findAll());
        response.setEmployeeList(list);

        return response;
    }

    public Employee addEmployee(@RequestBody Employee employee) {
        //add resource
        employee = employeeRepository.save(employee);
//        //Create resource location
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(employee.getId())
//                .toUri();
//        //Send location in response
        return employee;
    }
}
