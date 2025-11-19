package com.alysrazor.library.service;


import com.alysrazor.library.dto.TokenDTO;
import com.alysrazor.library.entity.Token;
import com.alysrazor.library.entity.User;

import java.util.List;

public interface TokenService {
    List<TokenDTO> findAll();
    TokenDTO findById(Long id);
    List<TokenDTO> findByUser(User user);
    List<TokenDTO> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id);

    Token save(Token save);
    TokenDTO update(Long id, Token update);
    void delete(Token delete);
}
