package com.LikeMiracleTeam.MemorialBackend.auth;

import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserId(username);
        return optionalUser.map(PrincipalDetails::new).orElse(null);
    }
}
