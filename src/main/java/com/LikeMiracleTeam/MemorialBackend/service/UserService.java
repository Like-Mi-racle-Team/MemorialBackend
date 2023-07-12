package com.LikeMiracleTeam.MemorialBackend.service;

import com.LikeMiracleTeam.MemorialBackend.auth.PrincipalDetails;
import com.LikeMiracleTeam.MemorialBackend.auth.PrincipalDetailsService;
import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.UserResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.filter.JwtProvider;
import com.LikeMiracleTeam.MemorialBackend.repository.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final PrincipalDetailsService principalDetailsService;

    public ResponseEntity<UserResponse> join(UserRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getUserPassword()))
                .name(request.getUserName())
                .introduce(request.getUserIntroduce())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(new UserResponse(user));
    }

    public ResponseEntity<UserResponse> login(UserRequest request) {
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(request.getUserId());

        if (principalDetails == null || !passwordEncoder.matches(request.getUserPassword(), principalDetails.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails.getUsername(),principalDetails.getPassword(),principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtProvider.generateToken(request.getUserId()));

        return new ResponseEntity<>(principalDetails.toDto(), headers, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<UserResponse> modified(String tokenString, UserRequest request) {

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        if (userRepository.existsByUserId(jwtProvider.getId(tokenString))) {
            return ResponseEntity.ok(new UserResponse(user));
        }
        user.update(request.getUserId(), request.getUserPassword(), request.getUserName(), request.getUserIntroduce());

        return ResponseEntity.ok(new UserResponse(user));

    }

    public ResponseEntity<UserResponse> delete(String tokenString, UserRequest request) {

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        userRepository.delete(user);

        return ResponseEntity.ok(new UserResponse(user));
    }

    public ResponseEntity<Page<UserResponse>> findByName(String userName, final Pageable pageable) {

        Page<User> users = userRepository.findAllByName(userName, pageable);

        return ResponseEntity.ok(users.map(UserResponse::new));
    }

    public ResponseEntity<UserResponse> findById(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();

        return ResponseEntity.ok(new UserResponse(user));
    }

}
