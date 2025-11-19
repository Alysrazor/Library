package com.alysrazor.library.dto;


import com.alysrazor.library.entity.Token;

public record TokenDTO(
        Long id,
        Token.TokenType type,
        boolean revoked,
        boolean expired,
        UserDTO user
) {
}
