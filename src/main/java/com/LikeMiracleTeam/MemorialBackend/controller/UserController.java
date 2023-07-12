package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.service.UserService;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    public ResponseEntity<Page<UserResponse>> findByUserName(
            @ApiParam(value = "찾을 유저의 이름") @RequestParam(name = "username")String userName,
            @ApiParam(value = "페이징 파라미터") Pageable pageable) {
        return userService.findByName(userName, pageable);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "사용자가 존재치 않음")
    })
    public ResponseEntity<UserResponse> findByUserId(
            @ApiParam(value = "찾을 유저의 아이디")@PathVariable(name = "userId")String userId ) {
        return userService.findById(userId);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateUser(
            @ApiParam(value = "엑세스 토큰")@ RequestHeader(name = "Authorization")String tokenString,
            @RequestBody UserRequest request) {
        return userService.updateUser(tokenString, request);
    }

    @DeleteMapping
    public ResponseEntity<UserResponse> deleteUser(
            @ApiParam(value = "엑세스 토큰") @RequestHeader(name = "Authorization")String tokenString) {
        return userService.deleteUser(tokenString);
    }

}
