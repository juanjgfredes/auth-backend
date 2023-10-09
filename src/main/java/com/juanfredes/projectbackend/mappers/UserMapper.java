package com.juanfredes.projectbackend.mappers;

import com.juanfredes.projectbackend.dto.CreateUserDto;
import com.juanfredes.projectbackend.dto.UserDto;
import com.juanfredes.projectbackend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser( CreateUserDto createUserDto );

    //@Mapping(target = "token", ignore = true)
    UserDto toUserDto( User user );
}
