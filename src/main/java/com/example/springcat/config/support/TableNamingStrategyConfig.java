package com.example.springcat.config.support;

import lombok.val;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * 資料庫命名策略
 */
public class TableNamingStrategyConfig extends SpringPhysicalNamingStrategy {

    private static final String ENTITY = "Entity";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        val tableName = name.getText();
        if (tableName.contains(ENTITY)) {
            return super
                .toPhysicalTableName(new Identifier(tableName.replace(ENTITY, ""), name.isQuoted()),
                    jdbcEnvironment);
        }
        return super.toPhysicalTableName(name, jdbcEnvironment);
    }
}
