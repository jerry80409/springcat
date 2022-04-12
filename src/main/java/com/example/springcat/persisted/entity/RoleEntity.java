package com.example.springcat.persisted.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import com.example.springcat.persisted.entity.common.Role;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Table(
    indexes = {@Index(columnList = "code", name = "idx_role_code")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class RoleEntity extends AbstractEntity<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * joined by user table
     */
    @Column(name = "user_id",
        columnDefinition = "integer(11) comment '對應 user table'")
    private Long userId;

    /**
     * role code
     */
    @Enumerated(STRING)
    @Column(name = "code", nullable = false,
        columnDefinition = "varchar(8) comment '角色'")
    private Role code;

    /**
     * description
     */
    @Column(name = "desc",
        columnDefinition = "varchar(255) comment '說明'")
    private String desc;

    /**
     * role value
     */
    @NotNull
    @Column(name = "value", nullable = false,
        columnDefinition = "integer(11) comment '權重, 數字越大權限越高'")
    private Integer value;
}
