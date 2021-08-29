package com.example.springcat.persisted.entity.common;

import lombok.Getter;

public enum Role {
    ADMIN(99),
    USER(1),
    ;

    @Getter
    private final int value;

    Role(int value) {
        this.value = value;
    }
}
