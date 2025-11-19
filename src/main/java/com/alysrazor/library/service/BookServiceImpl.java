package com.alysrazor.library.service;

import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.entity.Book;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.exception.AuthorNotFoundException;
import com.alysrazor.library.exception.BookIsbnAlreadyExists;
import com.alysrazor.library.exception.BookNotFoundException;
import com.alysrazor.library.exception.PublisherNotFoundException;
import com.alysrazor.library.mapper.BookMapper;
import com.alysrazor.library.repository.AuthorRepository;
import com.alysrazor.library.repository.BookRepository;
import com.alysrazor.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService{
    private final BookRepository repo;
    private final BookMapper mapper;

    private final AuthorRepository authorRepo;
    private final PublisherRepository publisherRepo;


    @Override
    public List<BookDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public BookDTO findById(int id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public BookDTO findByIsbn(String isbn) {
        return repo.findByIsbn(isbn)
                .map(mapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @Override
    public List<BookDTO> findByTitleContainingIgnoreCase(String title) {
        return repo.findByTitleContainingIgnoreCase(title)
                .map(mapper::toDTOList)
                .orElse(null);
    }

    @Override
    public Book save(Book save) {
        if (repo.findByIsbn(save.getIsbn()).isPresent())
            throw new BookIsbnAlreadyExists(save.getIsbn());

        Author author = authorRepo.findById(
                save.getAuthor().getId()
        ).orElseThrow(() -> new AuthorNotFoundException(save.getAuthor().getId()));

        Publisher publisher = publisherRepo.findById(
                save.getPublisher().getId()
        ).orElseThrow(() -> new PublisherNotFoundException(save.getPublisher().getId()));

        save.setAuthor(author);
        save.setPublisher(publisher);

        repo.save(save);
        return save;
    }

    @Override
    public BookDTO update(int id, Book update) {
        Book found = repo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        Author author = authorRepo.findById(
                update.getAuthor().getId()
        ).orElseThrow(() -> new AuthorNotFoundException(
                update.getAuthor().getId()));

        Publisher publisher = publisherRepo.findById(
                update.getPublisher().getId()
        ).orElseThrow(() -> new PublisherNotFoundException(
                update.getPublisher().getId()));

        update.setAuthor(author);
        update.setPublisher(publisher);

        mapper.update(update, found);

        return mapper.toDTO(repo.save(found));
    }

    @Override
    public void delete(Book delete) {
        repo.delete(delete);
    }
}
