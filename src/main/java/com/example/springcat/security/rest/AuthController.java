package com.example.springcat.security.rest;

import static com.example.springcat.security.jwt.JwtTokenProvider.BEARER_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.example.springcat.security.config.WebSecurityConfig;
import com.example.springcat.security.dto.UserInfo;
import com.example.springcat.security.jwt.JwtTokenProvider;
import com.example.springcat.security.rest.register.RegisterService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final RegisterService registerService;

    @PostMapping("/register")
    ResponseEntity<UserInfo> register(@Valid @RequestBody Register register) throws Exception {
        return ResponseEntity.ok(registerService.register(register));
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@Valid @RequestBody Login login) {
        // authenticate and set authentication to security context
        val authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPaswrd()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // create token
        val token = BEARER_TOKEN + jwtTokenProvider.createToken(authentication);
        return ResponseEntity.ok().header(AUTHORIZATION, token).body(token);
    }
}
