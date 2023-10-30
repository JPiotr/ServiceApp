package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Abstracts.IActivityCreator;
import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleMapper;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Service.ActivityFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService implements UserDetailsService, IService<User, UserDTO>, IActivityCreator {
    private final UserRepository userRepo;
    private final RuleService ruleService;
    private final FileService fileService; //todo: assign photo to user
    private final ActivityFactory activityFabric;

    @Override
    public User get(UserDTO dto) {
        var opt = userRepo.findByUuid(dto.id());
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException("Entity with uuid " + dto.id() + " not found!");
    }

    @Override
    public Collection<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User update(UserDTO dto) {
        User user = get(dto);
        if(dto.email() != null){
            user.setEmail(dto.email());
            user.setLastModificationDate();
        }
        if(dto.name() != null){
            createActivity("name",dto.name(),user.getName(),null,user);
            user.setName(dto.name());
            user.setLastModificationDate();
        }
        if(dto.userName() != null){
            user.setUsername(dto.userName());
            user.setLastModificationDate();
        }
        if(dto.password() != null){
            user.setPassword(dto.password());
            user.setLastModificationDate();
        }
        if(dto.surname() != null){
            createActivity("surname",dto.surname(),user.getSurname(),null,user);
            user.setSurname(dto.surname());
            user.setLastModificationDate();
        }
        if(dto.photoId() != null){
            createActivity("photoId",dto.photoId().toString()," ",null,user);
            var file = fileService.getFileById(dto.photoId());
            file.setObjectId(user.getUuid());
            fileService.updateFile(file);
        }
        if(dto.rules() != null && !dto.rules().isEmpty()){
            createActivity("rules",dto.rules().toString(),user.getRules().toString(),null,user);
            user.setRules(
                    dto.rules().stream()
                            .map(
                                    ruleService::get)
                            .collect(Collectors.toList()
                            )
            );
            user.setLastModificationDate();
        }
        return userRepo.save(user);
    }

    @Override
    public User add(UserDTO dto) {
        var rules = dto.rules().stream().map(ruleService::get).toList();
        User user = new User(dto.userName(), dto.name(), dto.surname(), dto.email(), rules, dto.password());
        if(rules.isEmpty()){
            user.setRules(List.of(ruleService.get(new RuleDTO(ERule.USER.toString()))));
        }
        return userRepo.save(user);
    }

    @Override
    public boolean delete(UserDTO dto) {
        userRepo.delete(this.get(dto));
        return true;
    }

    @Override
    public boolean createActivity(String fieldName, String newValue, String oldValue, User creator, EntityBase objectActivity) {
        activityFabric.prepare(User.class.getName()).create(fieldName,newValue,oldValue,creator,objectActivity);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var opt = userRepo.findByUsername(username);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UsernameNotFoundException("User " + username + " not found!");
    }
    public User changeUserEnabled(UserDTO dto) {
         var user = get(dto);
         user.setEnabled(!user.isEnabled());
         return userRepo.save(user);
    }
}
