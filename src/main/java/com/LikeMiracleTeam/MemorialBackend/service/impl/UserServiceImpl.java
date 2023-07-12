package com.LikeMiracleTeam.MemorialBackend.service.impl;

import com.LikeMiracleTeam.MemorialBackend.auth.PrincipalDetails;
import com.LikeMiracleTeam.MemorialBackend.auth.PrincipalDetailsService;
import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.filter.JwtProvider;
import com.LikeMiracleTeam.MemorialBackend.repository.UserRepository;
import com.LikeMiracleTeam.MemorialBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    public ResponseEntity<UserResponse> join(UserRequest request) {
        if (userRepository.existsByUserId(request.getUser_id())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = User.builder()
                .userId(request.getUser_id())
                .password(passwordEncoder.encode(request.getUser_password()))
                .name(request.getUser_name())
                .introduce(request.getUser_introduce())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(new UserResponse(user));
    }

    @Override
    public ResponseEntity<UserResponse> login(UserRequest request) {
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(request.getUser_id());

        if (principalDetails == null || !passwordEncoder.matches(request.getUser_password(), principalDetails.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails.getUsername(),principalDetails.getPassword(),principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtProvider.generateToken(request.getUser_id()));

        return new ResponseEntity<>(principalDetails.toDto(), headers, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<UserResponse> updateUser(String tokenString, UserRequest request) {

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        user.update(request.getUser_id(), request.getUser_password(), request.getUser_name(), request.getUser_introduce());

        return ResponseEntity.ok(new UserResponse(user));

    }

    @Override
    public ResponseEntity<UserResponse> deleteUser(String tokenString) {

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        userRepository.delete(user);

        return ResponseEntity.ok(new UserResponse(user));
    }

    @Override
    public ResponseEntity<Page<UserResponse>> findByName(String userName, final Pageable pageable) {

        Page<User> users = userRepository.findAllByName(userName, pageable);

        return ResponseEntity.ok(users.map(UserResponse::new));
    }

    @Override
    public ResponseEntity<UserResponse> findById(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();

        return ResponseEntity.ok(new UserResponse(user));
    }

}
