package com.academy.onlinestore.api.user;

import com.academy.onlinestore.api.user.web.NewUserDto;
import com.academy.onlinestore.api.user.web.UpdateUserDto;
import com.academy.onlinestore.api.user.web.UserDto;
import com.academy.onlinestore.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        log.info("Jwt Subject: {}", jwt.getSubject());
        log.info("Jwt ID: ", jwt.getId());
        User user = userRepository.findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(jwt.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found..!"));
        return userMapper.toUserDto(user);
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
    public void createNewUser(NewUserDto newUserDto) {
        // Check username if exit
        if (userRepository.existsByUsernameAndIsDeletedFalse(newUserDto.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists...");
        }
        // Check email if exit
        if (userRepository.existsByEmailAndIsDeletedFalse(newUserDto.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists...");
        }

        User newUser = userMapper.fromNewUserDto(newUserDto);
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setIsVerified(false);
        newUser.setIsDeleted(false);
        newUser.setPassword(passwordEncoder.encode(newUserDto.password()));

        boolean isNotFound = newUserDto.roleIds().stream()
                .anyMatch(roleId -> !roleRepository.existsById(roleId));

        if (isNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role ID does not exist..!");
        }

        Set<Role> roles = newUserDto.roleIds().stream()
                .map(roleId -> Role.builder().id(roleId).build())
                .collect(Collectors.toSet());

        newUser.setRoles(roles);
        userRepository.save(newUser);
    }

    @Transactional
    @Override
    public void updateByUUID(String uuid, UpdateUserDto updateUserDto) {
        // Check email if exit
        if (userRepository.existsByEmailAndIsDeletedFalse(updateUserDto.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists...");
        }

        User foundUser = userRepository.selectUserByUUID(uuid).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists..."));

        userMapper.fromUpdateUserDto(foundUser, updateUserDto);
        userRepository.save(foundUser);
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
