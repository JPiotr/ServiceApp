package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Common.File.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Common.File.File;
import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserResponseMapper extends BaseResponseMapper<UserResponseBuilder> implements Function<User, UserResponse> {
    private final RuleMapper ruleMapper;
    private final FileResponseMapper fileMapper;
    private final FileService fileService;
    @Override
    public UserResponse apply(User user) {
        if(user == null)
            return null;

        this.useBuilder(new UserResponseBuilder())
                .setUserName(user.getUsername())
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setLastLoginDate(user.getLastLoginDate())
                .setCredentialExpirationDate(user.getCredentialExpirationDate())
                .setAccountExpirationDate(user.getAccountExpirationDate())
                .setIsEnabled(user.isEnabled())
                .setIsExpired(user.isExpired())
                .setIsCredentialExpired(user.isCredentialsExpired())
                .setFile(
                    fileMapper.apply(fileService.getObjectFiles(user.getUuid()).stream().findFirst().orElse(null))
                )
                .setId(user.getID())
                .setUUID(user.getUuid())
                .setCreationDate(user.getCreationDate())
                .setLastModificationDate(user.getLastModificationDate());

        if(user.getRules() == null || user.getRules().isEmpty()){
            builder.setRules(List.of());
            return builder.build();
        }
        return builder.setRules(
                user.getRules()
                        .stream()
                        .map(ruleMapper)
                        .collect(Collectors.toList())
                ).build();
    }
}
