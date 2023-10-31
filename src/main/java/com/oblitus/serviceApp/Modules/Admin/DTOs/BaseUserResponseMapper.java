package com.oblitus.serviceApp.Modules.Admin.DTOs;


import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BaseUserResponseMapper extends BaseResponseMapper<BaseUserResponseBuilder> implements Function<User, BaseUserResponse> {
    private final FileService fileService;
    private final FileResponseMapper fileMapper;
    @Override
    public BaseUserResponse apply(User user) {
        return this.useBuilder(new BaseUserResponseBuilder())
                .setUserName(user.getUsername())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setPhoto(
                        fileMapper.apply(fileService.getObjectFiles(user.getUuid()).stream().findFirst().orElse(null))
                )
                .setUUID(user.getUuid())
                .build();
    }
}
