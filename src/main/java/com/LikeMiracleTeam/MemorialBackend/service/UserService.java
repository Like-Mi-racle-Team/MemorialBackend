package com.LikeMiracleTeam.MemorialBackend.service;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserResponse> join(UserRequest request);
    ResponseEntity<UserResponse> login(UserRequest request);
    ResponseEntity<UserResponse> updateUser(String tokenString, UserRequest request);
    ResponseEntity<UserResponse> deleteUser(String tokenString);
    ResponseEntity<Page<UserResponse>> findByName(String userName, final Pageable pageable);
    ResponseEntity<UserResponse> findById(String userId);
}
