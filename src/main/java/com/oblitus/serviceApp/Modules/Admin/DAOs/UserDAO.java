package com.oblitus.serviceApp.Modules.Admin.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponse;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.Admin.RuleService;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public final class UserDAO implements DAO<UserResponse,UserDTO>{
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;
    private final RuleService ruleService;

    @Override
    public Optional<UserResponse> get(UUID id) {
        var opt = userService.getUser(id);
        return opt.map(userResponseMapper);
    }

    @Override
    public List<UserResponse> getAll() {
        return userService.getAllUsers()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(UserDTO userDTO) {
        return userService.deleteUser(userDTO.id());
    }

    @Override
    public UserResponse save(UserDTO userDTO) {
        return userResponseMapper.apply(
                userService.addUser(
                        userDTO.name(),
                        userDTO.email(),
                        List.of(ruleService.getRule(ERule.ADMIN.toString())),
                        userDTO.password(),
                        userDTO.userName(),
                        userDTO.surname()
                )
        );
    }


    @Override
    public UserResponse update(UserDTO userDTO) throws AccountLockedException {
        return userResponseMapper.apply(
                userService.updateUser(
                        userDTO.id(),
                        userDTO.userName(),
                        userDTO.email(),
                        userDTO.password()
                )
        );
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    public UserResponse addRuleForUser(UUID userID, String name){
        return userResponseMapper.apply(userService.addRuleToUser(userID, name));
    }

    public UserResponse disconnectRuleFromUser(UUID userID, String name){
        return userResponseMapper.apply(userService.disconnectRuleFromUser(userID, name));
    }
}
