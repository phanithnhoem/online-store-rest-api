package com.academy.onlinestore.api.user;

import com.academy.onlinestore.api.user.web.IsDeletedDto;
import com.academy.onlinestore.api.user.web.NewUserDto;
import com.academy.onlinestore.api.user.web.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{uuid}")
    public UserDto findByUUID(@PathVariable String uuid){
        return userService.findByUUID(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewUser(@RequestBody @Valid NewUserDto newUserDto){
        userService.createNewUser(newUserDto);
    }

    @PutMapping("/{uuid}")
    public void updateIsDeletedByUUID(@PathVariable String uuid, @RequestBody IsDeletedDto isDeletedDto){
        userService.updateIsDeletedByUUID(uuid, isDeletedDto.isDeleted());
    }

    @DeleteMapping("/{uuid}")
    public void deleteByUUID(@PathVariable String uuid){
        userService.deleteByUUID(uuid);
    }
}
