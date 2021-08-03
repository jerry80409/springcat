package com.example.springcat.config;

import java.util.Optional;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaAuditorConfig {

    private static final String SYSTEM = "sys";

    /**
     * 先把設定開出來, 之後整合 security 可以從這邊改
     *
     * @return
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    /**
     * 先暫時塞一個 sys 當 created user, updated user
     */
    static class AuditorAwareImpl implements AuditorAware<String> {

        @NonNull
        @Override
        public Optional<String> getCurrentAuditor () {
            // Use below commented code when will use Spring Security.
            return Optional.of(SYSTEM);
        }
    }
}
