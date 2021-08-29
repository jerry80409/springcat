package com.example.springcat.security.web;

import com.example.springcat.security.jwt.JwtTokenProvider;
import com.example.springcat.security.config.WebSecurityConfig;
import java.util.Arrays;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(WebSecurityConfig.AUTH_ENDPOINT)
class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    ResponseEntity<String> login(@Valid @RequestBody Login login) {
        // authenticate and set authentication to security context
        val authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPaswrd(), Arrays.asList(
                new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")
            )));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // create token
        return ResponseEntity.ok(jwtTokenProvider.createToken(authentication));
    }
}
