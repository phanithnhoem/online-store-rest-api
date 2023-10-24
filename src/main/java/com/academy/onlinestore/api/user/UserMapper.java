package com.academy.onlinestore.api.user;

import com.academy.onlinestore.api.user.web.NewUserDto;
import com.academy.onlinestore.api.user.web.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User fromNewUserDto(NewUserDto newUserDto);
}
