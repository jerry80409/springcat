package com.example.springcat.security.rest.register;

import com.example.springcat.persisted.entity.UserEntity;
import com.example.springcat.security.dto.UserInfo;
import com.example.springcat.security.rest.Register;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface RegisterMapper {

    UserEntity toUserEntity(Register source);

    UserInfo toUserInfo(UserEntity source);
}
