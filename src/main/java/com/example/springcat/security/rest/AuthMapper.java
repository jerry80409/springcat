package com.example.springcat.security.rest;

import com.example.springcat.persisted.entity.UserEntity;
import com.example.springcat.security.dto.UserInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface AuthMapper {


    UserInfo toUserInfo(UserEntity source);
}
