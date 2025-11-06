package com.alysrazor.library.service;

import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.exceptions.AuthorHasBooksException;
import com.alysrazor.library.exceptions.AuthorNotFoundException;
import com.alysrazor.library.mapper.AuthorMapper;
import com.alysrazor.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repo;
    private final AuthorMapper mapper;

    @Override
    public List<AuthorDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public AuthorDTO findById(int id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public List<AuthorDTO> findByNameContainingIgnoreCase(String name) {
        return repo.findByNameContainingIgnoreCase(name)
                .map(mapper::toDTOList)
                .orElse(null);
    }

    @Override
    public Author save(Author save) {
        repo.save(save);
        return save;
    }

    @Override
    public AuthorDTO update(int id, Author update) {
        Author found = repo.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        mapper.update(update, found);

        return mapper.toDTO(repo.save(found));
    }

    @Override
    public void delete(Author delete) {
        Optional<Author> found = repo.findById(delete.getId());

        if (found.isPresent() && !found.get().getBookList().isEmpty())
            throw new AuthorHasBooksException(found.get().getId());

        repo.delete(delete);
    }


}
