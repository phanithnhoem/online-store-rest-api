package com.academy.onlinestore.api.user;

import com.academy.onlinestore.api.user.web.NewUserDto;
import com.academy.onlinestore.api.user.web.UpdateUserDto;
import com.academy.onlinestore.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createNewUser(NewUserDto newUserDto) {
        // Check username if exit
        if (userRepository.existsByUsernameAndIsDeletedFalse(newUserDto.username())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username already exists...");
        }
        // Check email if exit
        if (userRepository.existsByUsernameAndIsDeletedFalse(newUserDto.email())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email already exists...");
        }

        User newUser = userMapper.fromNewUserDto(newUserDto);
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setIsVerified(false);
        newUser.setIsDeleted(false);
        userRepository.save(newUser);
    }

    @Transactional
    @Override
    public void updateByUUID(String uuid, UpdateUserDto updateUserDto) {

    }

    @Override
    public UserDto findByUUID(String uuid) {
        User foundUser = userRepository.selectUserByUuidAndIsDeleted(uuid, false)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User UUID = %s doesn't exist in database.", uuid)));

        return userMapper.toUserDto(foundUser);
    }

    @Transactional
    @Override
    public void deleteByUUID(String uuid) {
        User foundUser = userRepository.selectUserByUUID(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User UUID = %s doesn't exist in database.", uuid)));
        userRepository.delete(foundUser);
    }

    @Transactional
    @Override
    public void updateIsDeletedByUUID(String uuid, Boolean isDeleted) {
        // Check user exits or not
        if (userRepository.checkUserByUUID(uuid)){
            userRepository.updateIsDeletedStatusByUUID(uuid, isDeleted);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User UUID = %s doesn't exist in database.", uuid));
    }
}
