package com.fernando.spring_security_jwt.Auth;

import com.fernando.spring_security_jwt.User.User;
import com.fernando.spring_security_jwt.User.UserRequestDto;
import com.fernando.spring_security_jwt.User.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto user) {
        User savedUser = authService.register(user);
        UserResponseDto userResponseDto = new UserResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        return authService.authenticate(authentication);
    }
}
