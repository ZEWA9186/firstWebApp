package org.example.testsecurity.jpa.mapper;

import org.example.testsecurity.dto.RequestRegistrationDTO;
import org.example.testsecurity.dto.UserAuthResponse;
import org.example.testsecurity.jpa.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Profile toProfile(RequestRegistrationDTO dto);

    @Mapping(target = "token", ignore = true)
    UserAuthResponse toUserAuthResponse(Profile profile);
}
