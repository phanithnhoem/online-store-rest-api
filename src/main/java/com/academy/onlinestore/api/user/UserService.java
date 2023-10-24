package com.academy.onlinestore.api.user;

import com.academy.onlinestore.api.user.web.NewUserDto;
import com.academy.onlinestore.api.user.web.UpdateUserDto;
import com.academy.onlinestore.api.user.web.UserDto;

public interface UserService {
    // Create a new user
    void createNewUser(NewUserDto newUserDto);

    // Update an existing user
    void updateByUUID(String uuid, UpdateUserDto updateUserDto);

    // Find users by pagination and filter

    // Find a user by UUID
    UserDto findByUUID(String uuid);

    // Delete a user by UUID(Permanently Delete)
    void deleteByUUID(String uuid);

    // Update status isDeleted by UUID (Soft Delete)
    void updateIsDeletedByUUID(String uuid, Boolean isDeleted);

}
