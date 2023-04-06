package com.oblitus.serviceApp.Modules.Admin.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserMapper;
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
public final class UserDAO implements DAO<UserDTO> {
    private final UserService userService;
    private final UserMapper userMapper;
    private final RuleService ruleService;

    @Override
    public Optional<UserDTO> get(UUID id) {
        var opt = userService.getUser(id);
        if(opt.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(
                userMapper.apply(opt.get())
        );
    }

    @Override
    public List<UserDTO> getAll() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return userMapper.apply(userService.addUser(
                    userDTO.username(),
                    userDTO.email(),
                    userDTO.roles()
                            .stream()
                            .map(
                                    ruleDTO -> ruleService.getRule(ruleDTO.id()))
                            .collect(Collectors.toList()
                            ),
                    userDTO.password()
                )
        );
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws AccountLockedException {
        return userMapper.apply(
                userService.updateUser(
                        userDTO.id(),
                        userDTO.username(),
                        userDTO.email(),
                        userDTO.password()
                )
        );
    }

    @Override
    public boolean delete(UserDTO userDTO) {
        return userService.deleteUser(userDTO.id());
    }
}
