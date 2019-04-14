package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.User;
import com.jeanvar.housingfinance.exception.WrongUserException;
import com.jeanvar.housingfinance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    AuthService authService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void saveUser_notExists() {
        when(authService.createJWT("uid")).thenReturn("jws");

        UserDTO userDTO = UserDTO.create("uid", "pass");
        userDTO = userService.saveUser(userDTO);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();

        assertThat(user.getUserId()).isEqualTo("uid");
        assertThat(user.getPassword()).isEqualTo(userDTO.getEncryptedPassword());
        assertThat(userDTO.getJws()).isEqualTo("jws");
    }

    @Test
    void saveUser_alreadyExists() {
        when(userRepository.existsByUserId(any())).thenReturn(true);

        assertThatIllegalArgumentException().isThrownBy(() ->
            userService.saveUser(UserDTO.create("u","p"))
        );
    }

    @Test
    void checkUserAndReturnJWS_valid() {
        String userId = "user";

        UserDTO userDTO = UserDTO.create(userId, "password");
        userDTO.generateEncryptedPassword();

        User user = new User();
        user.setUserId(userId);
        user.setPassword(userDTO.getEncryptedPassword());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(authService.createJWT(userId)).thenReturn("token");

        String jws = userService.checkUserAndReturnJWS(userDTO);

        assertThat(jws).isEqualTo("token");
    }

    @Test
    void checkUserAndReturnJWS_invalid() {
        String userId = "user";

        UserDTO userDTO = UserDTO.create(userId, "password");

        User user = new User();
        user.setUserId(userId);
        user.setPassword("wrong password");

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        assertThatExceptionOfType(WrongUserException.class).isThrownBy(() -> userService.checkUserAndReturnJWS(userDTO));
    }
}