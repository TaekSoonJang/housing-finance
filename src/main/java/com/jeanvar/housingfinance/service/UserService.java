package com.jeanvar.housingfinance.service;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    String checkUserAndReturnJWS(UserDTO userDTO);
}
