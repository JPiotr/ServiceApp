package com.oblitus.serviceApp.Modules.Admin.DTOs;

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
public class UserResponseMapper implements Function<User, UserResponse> {
    private final RuleMapper ruleMapper;
    private final FileResponseMapper fileMapper;
    private final FileService fileService;

    @Override
    public UserResponse apply(User user) {
        if(user == null)
            return null;
        if(user.getRules() == null || user.getRules().isEmpty()){
            return new UserResponse(
                    user.getID(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getName(),
                    user.getSurname(),
                    List.of(),
                    user.getLastLoginDate(),
                    user.getCredentialExpirationDate(),
                    user.getAccountExpirationDate(),
                    user.isEnabled(),
                    user.isExpired(),
                    user.isCredentialsExpired(),
                    user.getBase().getLastModificationDate(),
                    user.getBase().getCreationDate(),
                    fileMapper.apply(fileService.getObjectFiles(user.getID()).stream().findFirst().orElse(null))

            );
        }
        return new UserResponse(
                user.getID(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getRules()
                        .stream()
                        .map(ruleMapper)
                        .collect(Collectors.toList()),
                user.getLastLoginDate(),
                user.getCredentialExpirationDate(),
                user.getAccountExpirationDate(),
                user.isEnabled(),
                user.isExpired(),
                user.isCredentialsExpired(),
                user.getBase().getLastModificationDate(),
                user.getBase().getCreationDate(),
                fileMapper.apply(fileService.getObjectFiles(user.getID()).stream().findFirst().orElse(null))

        );
    }
}
