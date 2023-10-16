package com.oblitus.serviceApp.Modules.Admin.DTOs;

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
                    user.getBase().getCreationDate()

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
                user.getBase().getCreationDate()

        );
    }
}
