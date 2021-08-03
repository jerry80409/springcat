package com.example.springcat.persisted.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * User entity
 * entity 設計的時候, 預先把 index, uniqueConstraints 先開出來, 就算沒有也先用空的大括弧放著
 * table name 則去掉 entity, 物件命名尾綴 entity 是為了之後 dto 物件, vo 物件有個區別
 * 藉由 lombok 的 annotations 自動在 compiler 階段幫助產生 setter, getter, builder, hashcode, equals 等方法
 * 藉由 NotBlank annotations (validation) 自動檢查
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "user",
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
    @Column(name = "name", nullable = false,
        columnDefinition = "varchar(128) comment '顯示使用者名稱'")
    private String name;

    /**
     * email
     */
    @NotBlank
    @Column(name = "email", nullable = false,
        columnDefinition = "varchar(255) comment '使用者信箱, 重新設定, 忘記密碼使用'")
    private String email;

    /**
     * avatar uri or code ?
     */
    @Column(name = "avatar",
        columnDefinition = "varchar(255) comment '頭像代號, 頭像素材庫的代號'")
    private String avatar;

    /**
     * remember me
     */
    @Column(name = "remember_token",
        columnDefinition = "varchar(255) comment '用於紀錄 user 登入期間的 token'")
    private String rememberToken;

}
