package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/findByUserName")
    public ResponseEntity<Page<UserResponse>> findByUserName(@RequestBody UserRequest request, @RequestBody Pageable pageable) {
        return userService.findByName(request, pageable);
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<UserResponse> findByUserId(@RequestBody UserRequest request) {
        return userService.findById(request);
    }

}
