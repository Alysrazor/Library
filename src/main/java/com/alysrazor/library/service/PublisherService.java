package com.alysrazor.library.service;

import com.alysrazor.library.dto.PublisherDTO;
import com.alysrazor.library.entity.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublisherService {
    List<PublisherDTO> findAll();

    PublisherDTO findById(int id);

    List<PublisherDTO> findByNameContainingIgnoreCase(String name);

    Publisher save(Publisher save);
    PublisherDTO update(int id, Publisher update);
    void delete(Publisher delete);
}
