package com.example.springcat.persisted.config;

import lombok.val;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * 資料庫命名策略
 */
public class TableNamingStrategyConfig extends SpringPhysicalNamingStrategy {

    private static final String ENTITY = "Entity";

    /**
     * 把資料庫的命名, 自動去掉 Entity
     *
     * @param name  table name
     * @param jdbcEnv jdbc env
     * @return
     */
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnv) {
        val tableName = name.getText();
        if (tableName.contains(ENTITY)) {
            return super
                .toPhysicalTableName(new Identifier(tableName.replace(ENTITY, ""), name.isQuoted()),
                    jdbcEnv);
        }
        return super.toPhysicalTableName(name, jdbcEnv);
    }
}
