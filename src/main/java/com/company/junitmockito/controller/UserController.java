package com.company.junitmockito.controller;

import com.company.junitmockito.entity.User;
import com.company.junitmockito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/junit")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
//        return new User(id, "Narendra", "1000", new Date());
    }
}
