package com.alysrazor.library.repository;

import com.alysrazor.library.entity.Token;
import com.alysrazor.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findById(Long id);
    Optional<Token> findByToken(String token);
    Optional<List<Token>> findByUser(User user);

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id);
}
