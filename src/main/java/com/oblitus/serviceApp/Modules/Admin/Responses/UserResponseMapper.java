package com.oblitus.serviceApp.Modules.Admin.Responses;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.Responses.FileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
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
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setLastLoginDate(user.getLastLoginDate())
                .setCredentialExpirationDate(user.getCredentialExpirationDate())
                .setAccountExpirationDate(user.getAccountExpirationDate())
                .setIsEnabled(user.isEnabled())
                .setIsExpired(user.isExpired())
                .setIsCredentialExpired(user.isCredentialsExpired())
                .setAvatar(
                    fileMapper.apply(fileService.getObjectFiles(user.getUuid()).stream().findFirst().orElse(null))
                )
                .setId(user.getID())
                .setUUID(user.getUuid())
                .setCreationDate(user.getCreationDate())
                .setLastModificationDate(user.getLastModificationDate())
                .setId(user.getID());

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
