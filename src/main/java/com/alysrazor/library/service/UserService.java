package com.alysrazor.library.service;

import com.alysrazor.library.dto.UserDTO;
import com.alysrazor.library.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    List<UserDTO> findByUsernameContainingIgnoreCase(String username);

    User save(User save);
    UserDTO update(Long id, User update);
    void delete(User delete);
}
