package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.service.UserService;
import com.querydsl.core.annotations.QueryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findByUserName(@RequestParam(name = "username")String userName, Pageable pageable) {
        return userService.findByName(userName, pageable);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findByUserId(@PathVariable(name = "userId")String userId ) {
        return userService.findById(userId);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateUser(@RequestHeader(name = "Authorization")String tokenString, @RequestBody UserRequest request) {
        return userService.updateUser(tokenString, request);
    }

    @DeleteMapping
    public ResponseEntity<UserResponse> deleteUser(@RequestHeader(name = "Authorization")String tokenString) {
        return userService.deleteUser(tokenString);
    }

}
