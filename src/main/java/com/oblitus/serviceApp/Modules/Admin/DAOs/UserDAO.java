package com.oblitus.serviceApp.Modules.Admin.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponse;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public final class UserDAO implements DAO<UserResponse,UserDTO> {
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    @Override
    public UserResponse get(UUID id) {
        return userResponseMapper.apply(userService.getUser(id));
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
                        null,
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
                        userDTO.password(),
                        userDTO.name(),
                        userDTO.surname()
                )
        );
    }
    @Override
    public boolean delete(UUID id) {
        return userService.deleteUser(id);
    }
    public UserResponse addRuleForUser(UUID userID, String name) {
        return userResponseMapper.apply(userService.addRuleToUser(userID, name));
    }
    public UserResponse disconnectRuleFromUser(UUID userID, String name) {
        return userResponseMapper.apply(userService.disconnectRuleFromUser(userID, name));
    }
    public UserResponse changeEnabled(UUID userId) {
        return userResponseMapper.apply(userService.changeUserEnabled(userId));
    }
}
