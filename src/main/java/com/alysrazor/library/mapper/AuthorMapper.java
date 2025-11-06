package com.alysrazor.library.mapper;

import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.entity.Author;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);

    List<AuthorDTO> toDTOList(List<Author> authorList);

    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "update_date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author toEntity(AuthorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookList", ignore = true)
    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "update_date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(Author update, @MappingTarget Author target);
}
