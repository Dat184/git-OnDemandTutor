package org.example.ondemandtutor.mapper;


import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.request.UserUpdateRequest;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.pojo.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

}
