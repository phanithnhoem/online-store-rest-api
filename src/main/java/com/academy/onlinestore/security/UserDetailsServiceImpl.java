package com.academy.onlinestore.security;

import com.academy.onlinestore.api.user.User;
import com.academy.onlinestore.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userSecurity = userRepository.findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(username)
                .orElseThrow(() -> {
                    log.error("User has not been found");
                    return new UsernameNotFoundException("User has not been found");
                });

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(userSecurity);

        log.info("Security Username = {}", userSecurity.getUsername());
        log.info("Security Email = {}", userSecurity.getEmail());
        log.info("Security Author = {}", customUserDetails.getAuthorities());
        return customUserDetails;
    }
}
