package com.alysrazor.library.mapper;

import com.alysrazor.library.dto.PublisherDTO;
import com.alysrazor.library.entity.Publisher;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PublisherMapper {
    PublisherDTO toDTO(Publisher publisher);

    List<PublisherDTO> toDTOList(List<Publisher> publisherList);

    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "update_date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Publisher toEntity(PublisherDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "update_date", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(Publisher update, @MappingTarget Publisher target);
}
