package com.example.springcat.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider provider;

    @Test
    void verify_jwt_is_success() {
        val principal = "Springcat";
        val authorities = Arrays.asList(
            new SimpleGrantedAuthority("USER"),
            new SimpleGrantedAuthority("ADMIN")
        );

        val beforeAuth = new  UsernamePasswordAuthenticationToken(principal, null, authorities);
        val jwt = provider.createToken(beforeAuth);
        val afterAuth = provider.verify(jwt);

        assertEquals(jwt, afterAuth.getCredentials());
        assertEquals(principal, afterAuth.getName());
        assertTrue(afterAuth.getAuthorities().containsAll(authorities));
    }
}