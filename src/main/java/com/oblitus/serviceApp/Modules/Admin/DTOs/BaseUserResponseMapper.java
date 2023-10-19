package com.oblitus.serviceApp.Modules.Admin.DTOs;


import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BaseUserResponseMapper implements Function<User, BaseUserResponse> {
    @Override
    public BaseUserResponse apply(User user) {
        return new BaseUserResponse(
                user.getID(),
                user.getUsername(),
                user.getName(),
                user.getSurname()
        );
    }
}
