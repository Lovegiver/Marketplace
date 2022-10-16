package com.citizenweb.marketplace.dao;

import com.citizenweb.marketplace.dao.repos.UserRepository;
import com.citizenweb.marketplace.dao.repos.UserRepositoryImpl;
import com.citizenweb.marketplace.model.enums.GenderType;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserRepositoryImpl.class)
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    private final Logger LOG = LoggerFactory.getLogger(UserTest.class);

    @Test
    public void getUserTest() {
        User user = new Buyer("fred@courcier.com", "pericard42", "Lovegiver", "Fred", "Courcier", GenderType.MAN, null);
        User savedUser = userRepository.save(user);
        LOG.info("Saved User : " + savedUser);
    }
}
