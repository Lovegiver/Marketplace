package com.citizenweb.marketplace.dao.test;

import com.citizenweb.marketplace.dao.repos.UserRepository;
import com.citizenweb.marketplace.model.enums.GenderType;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getUserTest() {
        User user = new Buyer("fred@courcier.com", "pericard42", "Lovegiver", "Fred", "Courcier", GenderType.MAN, null);
        entityManager.persist(user);
    }
}
