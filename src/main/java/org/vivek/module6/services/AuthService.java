package org.vivek.module6.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.vivek.module6.dto.LoginDto;
import org.vivek.module6.dto.LoginResponseDto;
import org.vivek.module6.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String accessToken =  jwtService.generateAccessToken(userEntity);
        String refreshToken =  jwtService.generateRefreshToken(userEntity);

        return new LoginResponseDto(userEntity.getId(),accessToken, refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity userEntity = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(userEntity);
        return new LoginResponseDto(userEntity.getId(), accessToken, refreshToken);
    }
}
