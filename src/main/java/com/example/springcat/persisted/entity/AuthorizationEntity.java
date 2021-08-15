package com.example.springcat.persisted.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 配合 {@link org.springframework.security.core.userdetails.User} 建立 spring boot 認證所需的 table.
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@Table(
    indexes = {@Index(columnList = "email", name = "idx_user_email")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class AuthorizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * email
     */
    @NotBlank
    @Size(max = 255)
    @Column(name = "email", nullable = false,
        columnDefinition = "varchar(255) comment '使用者信箱, 重新設定, 忘記密碼使用'")
    private String email;

    /**
     * password
     */
    @NotBlank
    @Column(name = "paswrd", nullable = false,
        columnDefinition = "varchar(255) comment '使用者密碼, 認證使用'")
    private String paswrd;

    /**
     * user 是否啟用
     */
    @Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    /**
     * email 認證時間
     */
    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    /**
     * 帳號鎖定
     */
    @Default
    @Column(name = "locked", nullable = false)
    private boolean locked = false;

}
