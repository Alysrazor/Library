package com.alysrazor.library.mapper;

import com.alysrazor.library.dto.UserDTO;
import com.alysrazor.library.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserDTO toDTO(User user);
    List<UserDTO> toDTOList(List<User> userList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toEntity(UserDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tokenList", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(User update, @MappingTarget User target);
}
