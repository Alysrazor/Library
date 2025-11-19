package com.alysrazor.library.service;

import com.alysrazor.library.dto.PublisherDTO;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.exception.PublisherHasBooksException;
import com.alysrazor.library.exception.PublisherNotFoundException;
import com.alysrazor.library.mapper.PublisherMapper;
import com.alysrazor.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService{
    private final PublisherRepository repo;
    private final PublisherMapper mapper;


    @Override
    public List<PublisherDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public PublisherDTO findById(int id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    @Override
    public List<PublisherDTO> findByNameContainingIgnoreCase(String name) {
        return repo.findByNameContainingIgnoreCase(name)
                .map(mapper::toDTOList)
                .orElse(null);
    }

    @Override
    public Publisher save(Publisher save) {
        repo.save(save);
        return save;
    }

    @Override
    public PublisherDTO update(int id, Publisher update) {
        Publisher found = repo.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));

        mapper.update(update, found);

        return mapper.toDTO(repo.save(found));
    }

    @Override
    public void delete(Publisher delete) {
        Optional<Publisher> found = repo.findById(delete.getId());

        if (found.isPresent() && !found.get().getBookList().isEmpty())
            throw new PublisherHasBooksException(found.get().getId());

        repo.delete(delete);
    }
}
