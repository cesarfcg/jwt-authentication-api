package com.fernando.spring_security_jwt.Auth;

import com.fernando.spring_security_jwt.User.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto user) {
        User savedUser = authService.register(user);
        UserResponseDto userResponseDto = new UserResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
    @PostMapping("/auth/login")
    public String authenticate(Authentication authentication) {
        return authService.authenticate(authentication);
    }
    @GetMapping("/users/me")
    public UserResponseDto getCurrentUser(Authentication authentication) {
        return userService.findUser(authentication);
    }
    @GetMapping("/users")
    public List<User> list() {
        return userService.list();
    }
}
