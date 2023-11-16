package com.oblitus.serviceApp.Modules.Admin.Responses;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.BaseModule.Responses.FileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MyProfileResponseMapper
        extends BaseResponseMapper<MyProfileResponseBuilder>
        implements Function<User, MyProfileResponse> {
    private final FileService fileService;
    private final FileResponseMapper fileMapper;
    @Override
    public MyProfileResponse apply(User user) {
        return this.useBuilder(new MyProfileResponseBuilder())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setEmail(user.getEmail())
                .setLastLoginDate(user.getLastLoginDate())
                .setAccountExpirationDate(user.getAccountExpirationDate())
                .setCredentialExpirationDate(user.getCredentialExpirationDate())
                .setAvatar(
                        fileMapper.apply(fileService.getObjectFiles(user.getUuid()).stream().findFirst().orElse(null))
                )
                .setLastModificationDate(user.getLastModificationDate())
                .setUUID(user.getUuid())
                .setId(user.getID())
                .build();
    }
}
