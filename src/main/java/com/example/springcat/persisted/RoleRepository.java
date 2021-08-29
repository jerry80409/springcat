package com.example.springcat.persisted;

import com.example.springcat.persisted.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Transactional(readOnly = true)
    Optional<RoleEntity> findByCode(String code);
}
