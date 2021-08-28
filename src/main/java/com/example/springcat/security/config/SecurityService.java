package com.example.springcat.security.config;

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
 * 授權 service, 用來從資料庫取得 user 的細節
 *
 * ref: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-jdbc-bean
 */
@Slf4j
@Service
@RequiredArgsConstructor(access = PACKAGE)
class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        log.info("user ({}) login", userAccount);
        return userRepository.findByEmail(userAccount)
            .map(user -> User.builder()
                .username(user.getName())
                .password(user.getPaswrd())
                .disabled(!user.isEnabled())
                .accountLocked(user.isLocked())
                .build())
            .orElseThrow(() -> new NotFoundException("user 不存在"));
    }

}
