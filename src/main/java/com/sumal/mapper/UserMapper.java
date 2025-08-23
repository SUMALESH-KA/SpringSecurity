package com.sumal.mapper;


import com.sumal.dto.user.UserResponse;
import com.sumal.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse toResponse(UserEntity userEntity);
}
