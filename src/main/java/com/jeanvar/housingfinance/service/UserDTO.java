package com.jeanvar.housingfinance.service;

import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Getter
public class UserDTO {
    private String userId;
    private String plainPassword;
    private String encryptedPassword;
    @Setter
    private String jws;

    private UserDTO() {}

    public static UserDTO create(String userId, String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.userId = userId;
        userDTO.plainPassword = password;
        userDTO.encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        return userDTO;
    }
}
