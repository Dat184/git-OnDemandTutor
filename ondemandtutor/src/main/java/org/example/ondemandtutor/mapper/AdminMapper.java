package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.response.AdminResponse;
import org.example.ondemandtutor.pojo.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminResponse toAdminResponse(Admin admin);


}
