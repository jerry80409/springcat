package com.example.springcat.persisted.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;
import lombok.Builder.Default;
import lombok.ToString.Exclude;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

/**
 * User entity
 * entity 設計的時候, 預先把 index, uniqueConstraints 先開出來, 就算沒有也先用空的大括弧放著
 * table name 則去掉 entity, 物件命名尾綴 entity 是為了之後 dto 物件, vo 物件有個區別
 * 藉由 lombok 的 annotations 自動在 compiler 階段幫助產生 setter, getter, builder, hashcode, equals 等方法
 * 藉由 NotBlank annotations (validation) 自動檢查
 */
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor(access = PROTECTED)
@Table(
    indexes = {@Index(columnList = "email", name = "idx_user_email")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserEntity extends AbstractEntity<String> implements Serializable {

    /**
     * id 原本有想要寫在 AbstractEntity
     * 但考慮到有些情境可能會想要用 uuid 規範來做 pk, 故就不寫在 AbstractEntity 裡面了
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * user display name
     */
    @NotBlank
    @Size(max = 128)
    @Column(name = "name", nullable = false,
        columnDefinition = "varchar(128) comment '顯示使用者名稱'")
    private String name;

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
     * avatar uri or code ?
     */
    @Size(max = 255)
    @Column(name = "avatar",
        columnDefinition = "varchar(255) comment '頭像代號, 頭像素材庫的代號'")
    private String avatar;

    /**
     * user 是否啟用
     */
    @Default
    @Column(name = "enabled", nullable = false,
        columnDefinition = "boolean(1) comment 'user 是否啟用(驗證過)'")
    private boolean enabled = false;

    /**
     * 帳號鎖定
     */
    @Default
    @Column(name = "locked", nullable = false,
        columnDefinition = "boolean(1) comment 'user 是否被鎖定(不給登入)'")
    private boolean locked = false;

    /**
     * user roles, join on role table
     */
    @Exclude
    @Singular
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = ALL, fetch = LAZY, orphanRemoval = true)
    private Set<RoleEntity> roles;

    /**
     * user email verification record
     * 不確定 1 對 1 的設計好不好, 單純希望讓欄位簡單一點, 所以區隔
     */
    @Exclude
    @Singular
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = ALL, fetch = LAZY, orphanRemoval = true)
    private Set<EmailVerificationEntity> verifications;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
