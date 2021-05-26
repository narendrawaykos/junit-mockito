package com.company.junitmockito.service;

import com.company.junitmockito.entity.User;
import com.company.junitmockito.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public User getUser(long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    public void printMessage(boolean isError) throws Exception {
        if (isError) {
            throw new Exception("Error Occurred!!");
        } else
            System.out.println("Printing Error!!!");
    }
}
