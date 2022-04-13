package com.example.springcat.persisted.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.springcat.persisted.entity.common.Role;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor(access = PROTECTED)
@Table(
    indexes = {@Index(columnList = "user_id", name = "idx_role_user_id")},
    uniqueConstraints = {})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        RoleEntity that = (RoleEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
