package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.User;
import com.jeanvar.housingfinance.exception.WrongUserException;
import com.jeanvar.housingfinance.repository.UserRepository;
import com.jeanvar.housingfinance.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AuthService authService;

    @Override
    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            throw new WrongUserException(userDTO.getUserId() + " already exists.");
        }

        userDTO.generateEncryptedPassword();

        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getEncryptedPassword());

        userRepository.save(user);

        String jws = authService.createToken(userDTO.getUserId());
        userDTO.setJws(jws);

        return userDTO;
    }

    @Override
    public String checkUserAndReturnJWS(UserDTO userDTO) {
        User user = userRepository.findByUserId(userDTO.getUserId())
            .filter(u -> SecurityUtil.checkPassword(userDTO.getPlainPassword(), u.getPassword()))
            .orElseThrow(() -> new WrongUserException("Wrong user info."));

        return authService.createToken(user.getUserId());
    }
}
