package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Abstracts.IActivityCreator;
import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.*;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.NewPasswordMismatchException;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.PasswordNotMatchException;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.PasswordSettingSessionExpiredException;
import com.oblitus.serviceApp.Modules.Analytics.AnaliseActivityService;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.BaseModule.Notification;
import com.oblitus.serviceApp.Modules.BaseModule.NotificationRepository;
import com.oblitus.serviceApp.Modules.Service.ActivityFactory;
import com.oblitus.serviceApp.Utils.StaticInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService implements IService<User, UserDTO>, IActivityCreator {
    private final UserRepository userRepo;
    private final UserSetPasswordIdRepository setPasswordIdRepository;
    private final RuleService ruleService;
    private final FileService fileService;
    private final NotificationRepository notificationRepository;
    private final ActivityFactory activityFabric;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //todo: Zmiana z SuperUser na faktycznego kreatora
    @Override
    public User get(UserDTO dto) {
        var opt = userRepo.findById(dto.id());
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException("Entity with uuid " + dto.id() + " not found!");
    }

    @Override
    public Collection<User> getAll() {
        return userRepo.findAllByUuidNot(StaticInfo.SuperUser.getUuid());
    }

    public Collection<User> getAll(Sort sort) {
        return userRepo.findAllByUuidNot(StaticInfo.SuperUser.getUuid(),sort);
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAllByUuidNot(StaticInfo.SuperUser.getUuid(),pageable);
    }
    @Override
    public User update(UserDTO dto) {
        User user = get(dto);
        if(dto.email() != null){
            user.setEmail(dto.email());
            user.setLastModificationDate();
        }
        if(dto.name() != null){
            createActivity("Name",dto.name(),user.getName(),StaticInfo.SuperUser,user);
            user.setName(dto.name());
            user.setLastModificationDate();
        }
        if(dto.password() != null){
            user.setPassword(passwordEncoder.encode(dto.password()));
            user.setLastModificationDate();
        }
        if(dto.surname() != null){
            createActivity("Surname",dto.surname(),user.getSurname(),StaticInfo.SuperUser,user);
            user.setSurname(dto.surname());
            user.setLastModificationDate();
        }
        if(dto.photoId() != null){
            createActivity("Avatar",dto.photoId().toString()," ",StaticInfo.SuperUser,user);
            var file = fileService.getFileById(dto.photoId());
            file.setObjectId(user.getUuid());
            fileService.updateFile(file);
            file.setLastModificationDate();
            user.setLastModificationDate();
        }
        if(dto.rules() != null && !dto.rules().isEmpty()){
            createActivity("Rules",dto.rules().toString(),user.getRules().toString(),StaticInfo.SuperUser,user);
            user.setRules(
                    dto.rules().stream()
                            .map(
                                    ruleService::get)
                            .collect(Collectors.toList()
                            )
            );
            user.setLastModificationDate();
        }
        if(dto.changeVisibility()){
            user.setPublicProfile(!user.isPublicProfile());
            user.setLastModificationDate();
        }
        return userRepo.save(user);
    }

    @Override
    public User add(UserDTO dto) {
        var rules = dto.rules().stream().map(ruleService::get).toList();
        User user = new User(dto.name(), dto.surname(), dto.email(), rules,
                passwordEncoder.encode(dto.password()));

        if(dto.photoId()!=null){
            var file = fileService.getFileById(dto.photoId());
            file.setObjectId(user.getUuid());
            fileService.updateFile(file);
            file.setLastModificationDate();
        }

        if(rules.isEmpty()){
            user.setRules(List.of(ruleService.get(new RuleDTO(ERule.USER.toString()))));
        }
        createActivity("New User","","", StaticInfo.SuperUser,user);
        var usr = userRepo.save(user);
        StaticInfo.NotificationsOptions.forEach(x-> notificationRepository.save(new Notification(usr,false,false,x)));

        return usr;
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

    public User changeUserEnabled(UserDTO dto) {
         var user = get(dto);
         user.setEnabled(!user.isEnabled());
         return userRepo.save(user);
    }

    public User setLastLoginDate(User user){
        return userRepo.save(user);
    }

    public User getUserByUserName(String username){
        var opt = userRepo.findUserByEmail(username);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    public User changeUserPassword(String username, PasswordChangeDTO dto) throws PasswordNotMatchException, NewPasswordMismatchException {
        var user = getUserByUserName(username);
        if(passwordEncoder.matches(dto.password(), user.getPassword())){
            if(Objects.equals(dto.newPassword(), dto.newPasswordConfirmation())){
                user.setPassword(passwordEncoder.encode(dto.newPassword()));
                user.setLastModificationDate();
                createActivity("Change Password","","",user,user);
                return userRepo.save(user);
            }
            throw new NewPasswordMismatchException("Passwords not match!");
        }
        throw new PasswordNotMatchException("Password not match with current password!");
    }

    public User changeProfileDetails(String username, ChangeProfileDetailsDTO dto){
        var user = getUserByUserName(username);
        if(dto.email() != null){
            createActivity("Email",dto.email(),user.getEmail(), user,user);
            user.setEmail(dto.email());
            user.setLastModificationDate();
        }
        if(dto.name() != null){
            createActivity("Name",dto.name(),user.getName(), StaticInfo.SuperUser,user);
            user.setName(dto.name());
            user.setLastModificationDate();
        }
        if(dto.surname() != null){
            createActivity("Surname",dto.surname(),user.getSurname(),StaticInfo.SuperUser,user);
            user.setSurname(dto.surname());
            user.setLastModificationDate();
        }
        if(dto.avatar() != null){
            createActivity("Avatar",dto.avatar().toString()," ",StaticInfo.SuperUser,user);
            var file = fileService.getFileById(dto.avatar());
            file.setObjectId(user.getUuid());
            fileService.updateFile(file);
            file.setLastModificationDate();
            user.setLastModificationDate();
        }
        if(dto.changeVisibility()){
            user.setPublicProfile(!user.isPublicProfile());
            user.setLastModificationDate();
        }
        return userRepo.save(user);
    }

    public void saveUserSetPasswordSession(UUID uuid, User user){
        UserSetPasswordId usr = new UserSetPasswordId(uuid);
        usr.setCreator(user);

        setPasswordIdRepository.save(usr);
    }

    public boolean checkLinkExpired(UUID setPasswordID){
        var usrPass = setPasswordIdRepository.findByPasswordIDChange(setPasswordID);
        return usrPass.isPresent();
    }

    public void deleteUserSetPasswordId(User user){
        var pass = setPasswordIdRepository.findByCreator(user);
        pass.ifPresent(setPasswordIdRepository::delete);
    }

    public String setUserPassword(UUID randomUuid, SetPasswordDTO dto) throws NewPasswordMismatchException, PasswordSettingSessionExpiredException {
        var user = setPasswordIdRepository.findByPasswordIDChange(randomUuid);
        if(user.isPresent()){
            var usr = get(new UserDTO(user.get().getCreator().getUuid()));
            if(Objects.equals(dto.newPassword(), dto.newPasswordConfirmation())){
                usr.setPassword(passwordEncoder.encode(dto.newPassword()));
                usr.setLastModificationDate();
                userRepo.save(usr);
                setPasswordIdRepository.delete(user.get());
                createActivity("Set Password","","", usr,usr);
                return "Your password has been created!";
            }
            throw new NewPasswordMismatchException("Passwords not match!");
        }
        throw new PasswordSettingSessionExpiredException("Password setting session expired!");
    }

    public Collection<User> getUserWithRule(String ruleName, Sort sort){
        var rule = ruleService.get(new RuleDTO(ruleName));
        return sort != null ? userRepo.findAllByRulesContainsAndUuidNot(rule,StaticInfo.Internal.getUuid(),sort) :
                userRepo.findAllByRulesContainsAndUuidNot(rule, StaticInfo.SuperUser.getUuid());
    }

    public Page<User> getUserWithRule(String ruleName, Pageable pageable){
        var rule = ruleService.get(new RuleDTO(ruleName));
        return userRepo.findAllByRulesContainsAndUuidNot(rule,StaticInfo.SuperUser.getUuid(),pageable);
    }
}

