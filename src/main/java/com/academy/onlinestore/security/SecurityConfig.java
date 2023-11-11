package com.academy.onlinestore.security;

import com.academy.onlinestore.util.KeyUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final KeyUtil keyUtil;

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider() throws JOSEException {
        return new JwtAuthenticationProvider(
                jwtRefreshTokenDecoder());
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // TODO: What you want to customize
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/v1/auth/**", "/api/v1/files/**", "/auth/**", "/file/**").permitAll()
                    .requestMatchers(
                            HttpMethod.GET,
                            "/api/v1/categories/**",
                            "/api/v1/products/**").hasAuthority("SCOPE_product:read")
                    .requestMatchers(
                            HttpMethod.POST,
                            "/api/v1/categories/**",
                            "/api/v1/products/**").hasAuthority("SCOPE_product:write")
                    .requestMatchers(
                            HttpMethod.PUT,
                            "/api/v1/categories/**",
                            "/api/v1/products/**").hasAuthority("SCOPE_product:update")
                    .requestMatchers(
                            HttpMethod.DELETE,
                            "/api/v1/categories/**",
                            "/api/v1/products/**").hasAuthority("SCOPE_product:delete")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAuthority("SCOPE_user:profile")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("SCOPE_user:read")
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("SCOPE_user:write")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("SCOPE_user:update")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("SCOPE_user:delete")
                    .anyRequest().authenticated());

        // Configure OAuth2 Resource Server
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.csrf(AbstractHttpConfigurer::disable);

        //  TODO: Update api policy to stateless
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    // Start implement access token
    @Bean
    @Primary
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Primary
    public JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessTokenPublicKey()).build();
    }

    @Bean
    @Primary
    public JWKSource<SecurityContext> jwkSource() {
        JWK jwk = new RSAKey.Builder(keyUtil.getAccessTokenPublicKey())
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        var jwkSet = new JWKSet(jwk);
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }

    // Start implement refresh token block
    @Bean("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder(@Qualifier("refreshTokenJwkSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean("jwtRefreshTokenDecoder")
    JwtDecoder jwtRefreshTokenDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getRefreshTokenPublicKey()).build();
    }

    @Bean("refreshTokenJwkSource")
    public JWKSource<SecurityContext> refreshTokenJwkSource() {
        JWK jwk = new RSAKey.Builder(keyUtil.getRefreshTokenPublicKey())
                .privateKey(keyUtil.getRefreshTokenPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        var jwkSet = new JWKSet(jwk);
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }


}

