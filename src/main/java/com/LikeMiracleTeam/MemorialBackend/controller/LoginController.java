package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest request){
        return userService.join(request);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request){
        return userService.login(request);
    }
}
