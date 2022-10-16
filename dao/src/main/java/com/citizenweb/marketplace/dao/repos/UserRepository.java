package com.citizenweb.marketplace.dao.repos;

import com.citizenweb.marketplace.model.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(long id);

}
