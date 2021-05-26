package com.company.junitmockito.repository;

import com.company.junitmockito.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

}
