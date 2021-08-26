package com.example.springcat.service;

import static lombok.AccessLevel.PACKAGE;

import com.example.springcat.persisted.UserRepository;
import com.example.springcat.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 授權 service
 */
@Slf4j
@Service
@RequiredArgsConstructor(access = PACKAGE)
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        log.info("user ({}) login", userAccount);
        return userRepository.findByEmail(userAccount)
            .map(user -> User.builder()
                .username(user.getName())
                .password("")
                .build())
            .orElseThrow(() -> new NotFoundException("user 不存在"));
    }

}
