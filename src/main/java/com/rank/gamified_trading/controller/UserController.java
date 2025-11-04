package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.request.CreateUserRequest;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request.username().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity< UserResponse> getUser(@PathVariable String id) {
            UserResponse response = userService.getUser(id);
            return ResponseEntity.ok(response);
    }
}
