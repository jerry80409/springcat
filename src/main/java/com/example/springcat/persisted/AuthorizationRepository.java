package com.example.springcat.persisted;

import com.example.springcat.persisted.entity.AuthorizationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationEntity, Long> {

    /**
     * 取得單一資料, 盡量使用 lambda 的 optional, 方便跟 lambda 整合
     *
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    Optional<AuthorizationEntity> findByEmail (String email);
}
