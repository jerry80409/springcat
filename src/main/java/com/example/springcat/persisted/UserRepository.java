package com.example.springcat.persisted;

import com.example.springcat.persisted.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository 繼承 JpaRepository 裡面包含 (PagingAndSortingRepository, QueryByExampleExecutor, CrudRepository)
 * 基本上已經很夠用了
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 取得單一資料, 盡量使用 lambda 的 optional, 方便跟 lambda 整合
     *
     * @param email find by user email
     * @return user entity
     */
    Optional<UserEntity> findByEmail (String email);
}
