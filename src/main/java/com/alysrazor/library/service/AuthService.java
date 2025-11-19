package com.alysrazor.library.service;

import com.alysrazor.library.dto.request.LoginRequest;
import com.alysrazor.library.dto.request.RegisterRequest;
import com.alysrazor.library.dto.response.TokenResponse;
import com.alysrazor.library.entity.Token;
import com.alysrazor.library.entity.User;
import com.alysrazor.library.repository.TokenRepository;
import com.alysrazor.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public TokenResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .build();

        User save = userRepo.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(save, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Optional<User> user = userRepo.findByEmail(request.email());
        String jwtToken = user.map(jwtService::generateToken).orElse(null);
        String refreshToken = user.map(jwtService::generateRefreshToken).orElse(null);

        if (jwtToken == null || refreshToken == null)
            throw new UsernameNotFoundException(request.email());

        revokeAllUserTokens(user.get());
        saveUserToken(user.get(), jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new IllegalArgumentException("Invalid Bearer Token");

        final String refreshToken = authHeader.substring(7);
        final String email = jwtService.extractUsername(refreshToken);

        if (email == null)
            throw new IllegalArgumentException("Invalid Refresh Token");

        final User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if (!jwtService.isValidToken(refreshToken, user))
            throw new IllegalArgumentException("Invalid Refresh Token");

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validTokens = tokenRepo.findAllValidIsFalseOrRevokedIsFalseByUserId(
                user.getId()
        );

        if (!validTokens.isEmpty()) {
            for (final Token token: validTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }

            tokenRepo.saveAll(validTokens);
        }
    }
}
