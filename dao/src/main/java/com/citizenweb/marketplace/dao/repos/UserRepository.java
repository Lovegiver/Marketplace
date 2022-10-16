package com.citizenweb.marketplace.dao.repos;

import com.citizenweb.marketplace.model.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(long id);

}
