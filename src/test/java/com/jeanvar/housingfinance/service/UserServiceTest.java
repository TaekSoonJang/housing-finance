package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.User;
import com.jeanvar.housingfinance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void saveUser_notExists() {
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserDTO userDTO = UserDTO.create("uid", "pass");
        User user = userService.saveUser(userDTO);

        verify(userRepository, times(1)).save(user);

        assertThat(user.getUserId()).isEqualTo("uid");
        assertThat(user.getPassword()).isEqualTo(userDTO.getEncryptedPassword());
    }

    @Test
    void saveUser_alreadyExists() {
        when(userRepository.existsByUserId(any())).thenReturn(true);

        assertThatIllegalArgumentException().isThrownBy(() ->
            userService.saveUser(UserDTO.create("u","p"))
        );
    }
}