package com.example.springcat.persisted.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Table(
    indexes = {@Index(columnList = "user_id", name = "idx_email_verification_user_id")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {})})
public class EmailVerificationEntity extends AbstractEntity<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * Joined by user table
     */
    @Column(name = "user_id", nullable = false,
        columnDefinition = "integer comment '對應 user table'")
    private Long userId;

    /**
     * Verification code
     */
    @Column(name = "code", nullable = false,
        columnDefinition = "varchar(255) comment '驗證碼'")
    private String code;

    /**
     * Verification code expired time
     */
    @Column(name = "expired_at",
        columnDefinition = "timestamp comment '驗證碼失效時間'")
    private LocalDateTime expiredAt;

    /**
     * verified time
     */
    @Column(name = "email_verified_at",
        columnDefinition = "timestamp comment 'Email 被驗證的時間點'")
    private LocalDateTime emailVerifiedAt;

}
