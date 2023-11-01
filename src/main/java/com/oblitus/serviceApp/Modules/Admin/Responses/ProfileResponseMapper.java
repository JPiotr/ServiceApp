package com.oblitus.serviceApp.Modules.Admin.Responses;


import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProfileResponseMapper extends BaseResponseMapper<ProfileResponseBuilder> implements Function<User, ProfileResponse> {
    private final FileService fileService;
    private final FileResponseMapper fileMapper;
    @Override
    public ProfileResponse apply(User user) {
        return this.useBuilder(new ProfileResponseBuilder())
                .setUserName(user.getUsername())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setAvatar(
                        fileMapper.apply(fileService.getObjectFiles(user.getUuid()).stream().findFirst().orElse(null))
                )
                .setUUID(user.getUuid())
                .setId(user.getID())
                .build();
    }
}
