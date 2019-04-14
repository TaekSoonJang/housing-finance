package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.util.SecurityUtil;
import lombok.Getter;
import lombok.Setter;

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

        return userDTO;
    }

    public void generateEncryptedPassword() {
        this.encryptedPassword = SecurityUtil.generatePassword(this.plainPassword);
    }
}
