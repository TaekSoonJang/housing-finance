package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.User;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void crud() {
        User user = new User();
        user.setUserId("userId");
        user.setPassword(BCrypt.hashpw("pass", BCrypt.gensalt()));

        assertThat(user.getPassword()).hasSize(60);

        user = userRepository.save(user);

        entityManager.flush();
        entityManager.clear();

        Optional<User> found = userRepository.findByUserId("userId");
        assertThat(found).hasValueSatisfying(u ->
            assertThat(BCrypt.checkpw("pass", u.getPassword())).isTrue()
        );
    }
}