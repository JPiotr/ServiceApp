package com.oblitus.serviceApp.Modules.Admin.DTOs;


import com.oblitus.serviceApp.Common.File.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BaseUserResponseMapper implements Function<User, BaseUserResponse> {
    private final FileService fileService;
    private final FileResponseMapper fileMapper;
    @Override
    public BaseUserResponse apply(User user) {
        return new BaseUserResponse(
                user.getID(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                fileMapper.apply(fileService.getObjectFiles(user.getID()).stream().findFirst().orElse(null))
        );
    }
}
