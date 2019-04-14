package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.User;
import com.jeanvar.housingfinance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User saveUser(UserDTO userDTO) {
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            throw new IllegalArgumentException(userDTO.getUserId() + " already exists.");
        }

        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getEncryptedPassword());

        return userRepository.save(user);
    }
}
