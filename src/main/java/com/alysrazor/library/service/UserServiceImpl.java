package com.alysrazor.library.service;

import com.alysrazor.library.dto.UserDTO;
import com.alysrazor.library.mapper.UserMapper;
import com.alysrazor.library.entity.User;
import com.alysrazor.library.exception.UserNotFoundException;
import com.alysrazor.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository repo;
    private final UserMapper mapper;

    @Override
    public List<UserDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public UserDTO findById(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserDTO> findByUsernameContainingIgnoreCase(String username) {
        return repo.findByUsernameContainingIgnoreCase(username)
                .map(mapper::toDTOList)
                .orElse(null);
    }

    @Override
    public User save(User save) {
        repo.save(save);
        return save;
    }

    @Override
    public UserDTO update(Long id, User update) {
        User found = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        mapper.update(update, found);

        return mapper.toDTO(repo.save(found));
    }

    @Override
    public void delete(User delete) {
        User found = repo.findById(delete.getId())
                .orElseThrow(() -> new UserNotFoundException(delete.getId()));

        repo.delete(found);
    }
}
