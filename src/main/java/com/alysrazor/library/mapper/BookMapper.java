package com.alysrazor.library.mapper;

import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {AuthorMapper.class, PublisherMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookMapper {
    BookDTO toDTO(Book book);

    List<BookDTO> toDTOList(List<Book> bookList);

    @Mapping(target = "summary", ignore = true)
    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "update_date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book toEntity(BookDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "update_date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(Book update, @MappingTarget Book target);
}
