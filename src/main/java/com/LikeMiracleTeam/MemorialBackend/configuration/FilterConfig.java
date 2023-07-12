package com.LikeMiracleTeam.MemorialBackend.configuration;

import com.LikeMiracleTeam.MemorialBackend.filter.JwtAuthenticationFilter;
import com.LikeMiracleTeam.MemorialBackend.filter.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class FilterConfig extends AbstractHttpConfigurer<FilterConfig, HttpSecurity> {
    private final JwtProvider jwtProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
