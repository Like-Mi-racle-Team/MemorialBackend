package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;

    @PostMapping("/join")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "아이디 이미 존재")
    })
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest request){
        return userService.join(request);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "아이디 혹은 패스워드 불일치")
    })
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request){
        return userService.login(request);
    }
}
