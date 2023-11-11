package com.academy.onlinestore.api.auth;

import com.academy.onlinestore.api.auth.web.RegisterDto;
import com.academy.onlinestore.api.user.web.NewUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    NewUserDto mapRegisterDtoToNewUserDto(RegisterDto registerDto);
}
