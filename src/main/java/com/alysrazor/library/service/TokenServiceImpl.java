package com.alysrazor.library.service;

import com.alysrazor.library.dto.TokenDTO;
import com.alysrazor.library.mapper.TokenMapper;
import com.alysrazor.library.entity.Token;
import com.alysrazor.library.entity.User;
import com.alysrazor.library.exception.TokenNotFoundException;
import com.alysrazor.library.repository.TokenRepository;
import com.alysrazor.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository repo;
    private final TokenMapper mapper;

    private final UserRepository userRepo;

    @Override
    public List<TokenDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public TokenDTO findById(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new TokenNotFoundException(id));
    }

    @Override
    public List<TokenDTO> findByUser(User user) {
        return repo.findByUser(user)
                .map(mapper::toDTOList)
                .orElse(null);
    }

    @Override
    public List<TokenDTO> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id) {
        return repo.findAllValidIsFalseOrRevokedIsFalseByUserId(id)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public Token save(Token save) {
        repo.save(save);

        return save;
    }

    @Override
    public TokenDTO update(Long id, Token update) {
        Token found = repo.findById(id)
                .orElseThrow(() -> new TokenNotFoundException(id));

        mapper.update(update, found);

        return mapper.toDTO(repo.save(found));
    }

    @Override
    public void delete(Token delete) {
        Token found = repo.findById(delete.getId())
                .orElseThrow(() -> new TokenNotFoundException(delete.getId()));

        repo.delete(found);
    }
}
