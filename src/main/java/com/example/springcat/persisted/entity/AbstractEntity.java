package com.example.springcat.persisted.entity;

import static com.example.springcat.persisted.entity.common.Status.ACTIVATED;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

import com.example.springcat.persisted.entity.common.Status;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 基本的 entity 物件, 設計給 entity 物件繼承
 * 參考 https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#auditing
 *
 * @param <U> 整合 security user details
 */
@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractEntity<U extends Serializable> implements Serializable {

    /**
     * 建立者
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false,
        columnDefinition = "varchar(128) comment '建立者'")
    protected U createdBy;

    /**
     * 建立時間
     */
    @CreatedDate
    @Column(name = "created_date", updatable = false,
        columnDefinition = "timestamp comment '建立時間'")
    protected LocalDateTime createdDate;

    /**
     * 修改者
     */
    @LastModifiedBy
    @Column(name = "last_modified_by",
        columnDefinition = "varchar(128) comment '修改者'")
    protected U lastModifiedBy;

    /**
     * 修改時間
     */
    @LastModifiedDate
    @Column(name = "last_modified_date",
        columnDefinition = "timestamp comment '修改時間'")
    protected LocalDateTime lastModifiedDate;

    /**
     * 狀態
     */
    @Default
    @Enumerated(STRING)
    @Column(name = "status", nullable = false,
        columnDefinition = "varchar(16) comment '狀態, 預設為 ACTIVATED, 預備作為軟刪除(Soft-Deleted)'")
    protected Status status = ACTIVATED;

    /**
     * 版本, 樂觀鎖
     */
    @Version
    @Column(name = "version",
        columnDefinition = "bigint comment '版本, 預設為 0, 預備作為樂觀鎖(Optimistic-Locking)'")
    protected long version;
}
