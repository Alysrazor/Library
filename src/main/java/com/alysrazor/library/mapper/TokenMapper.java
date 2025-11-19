package com.alysrazor.library.mapper;

import com.alysrazor.library.dto.TokenDTO;
import com.alysrazor.library.entity.Token;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface TokenMapper {
    TokenDTO toDTO(Token token);
    List<TokenDTO> toDTOList(List<Token> tokenList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Token toEntity(TokenDTO dto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(Token update, @MappingTarget Token target);
}
