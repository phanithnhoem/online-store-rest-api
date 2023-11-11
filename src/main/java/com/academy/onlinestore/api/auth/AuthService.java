package com.academy.onlinestore.api.auth;

import com.academy.onlinestore.api.auth.web.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    public AuthDto refreshToken(RefreshTokenDto refreshTokenDto);
    void register(RegisterDto registerDto) throws MessagingException;

    void verify(VerifyDto verifyDto);

    AuthDto login(LoginDto loginDto);
}
