package com.github.arthurcech.productcatalog.mapper;

import com.github.arthurcech.productcatalog.domain.User;
import com.github.arthurcech.productcatalog.dto.user.UserDTO;
import com.github.arthurcech.productcatalog.dto.user.UserInsertDTO;
import com.github.arthurcech.productcatalog.dto.user.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(UserInsertDTO userInsertDTO);

    @Mapping(target = "roles", ignore = true)
    void updateUserFromDTO(
            UserUpdateDTO userUpdateDTO,
            @MappingTarget User user
    );

}
