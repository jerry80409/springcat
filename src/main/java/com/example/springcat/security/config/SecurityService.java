package com.example.springcat.security.config;

import com.example.springcat.persisted.UserRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 授權 service, 用來從資料庫取得 user 的細節
 *
 * ref: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-jdbc-bean
 */
@Slf4j
@Component
@RequiredArgsConstructor
class SecurityService implements UserDetailsService {


    private final UserRepository userRepository;

    /**
     * 取得 user 身份準備做認證 Authorization
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("User ({}) login", email);

        return userRepository.findByEmail(email)
            .map(user -> User.builder()
                .username(user.getEmail())  // 用 email 當作帳號
                .password(user.getPaswrd())
                .disabled(!user.isEnabled())
                .accountLocked(user.isLocked())
                .authorities(Arrays.asList(
                    new SimpleGrantedAuthority("USER"),
                    new SimpleGrantedAuthority("ADMIN")))
                .build())
            .orElseThrow(() -> new UsernameNotFoundException(String.format("user(%s) not existed", email)));
    }

}
