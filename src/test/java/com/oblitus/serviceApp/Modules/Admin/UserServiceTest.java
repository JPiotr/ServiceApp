//package com.oblitus.serviceApp.Modules.Admin;
//
//import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleMapper;
//import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
//import com.oblitus.serviceApp.Modules.Admin.DTOs.UserMapper;
//import com.oblitus.serviceApp.Modules.EModule;
//import com.oblitus.serviceApp.Modules.Module;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import javax.security.auth.login.AccountLockedException;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//    @Mock UserRepository userRepository;
//    private static UserService userService;
//
//    @Mock private RuleRepository ruleRepository;
//    private static RuleService ruleService;
//    private static User user;
//    private static UserDTO userDTO;
//    private static List<Rule> rules;
//    private static final RuleMapper ruleMapper = new RuleMapper();
//    private static final UserMapper userMapper = new UserMapper(ruleMapper);
//
//    @BeforeEach
//    void setUp() {
//        List<Module> modules = new ArrayList<>();
//        modules.add(new Module(EModule.ADMIN_MODULE.name(), true,EModule.ADMIN_MODULE));
//        ruleService = new RuleService(ruleRepository);
//        userService = new UserService(userRepository);
//        var rule = new Rule(ERule.USER,modules);
//        var ruleDTO = ruleMapper.apply(rule);
//
//        rules = List.of(rule);
//
//        user = new User("user","user@usr.usr",rules,"password");
//        userDTO = userMapper.apply(user);
//    }
//    @TestFactory
//    @Disabled
//    Stream<DynamicTest> dynamicTryUpdateUserTest(){
//        //given
//        List<Module> moduleList = new ArrayList<>();
//        moduleList.add(new Module(EModule.ADMIN_MODULE.name(), true,EModule.ADMIN_MODULE));
//        List<Rule> rules1 = List.of(new Rule(ERule.ADMIN,moduleList));
//
//        List<User> userList = Arrays.asList(
//                new User("User","user@usr.usr",rules1,""),
//                new User("User1","user1@usr.usr",rules1,"123"),
//                new User("User2","user2@usr.usr",rules1,"password")
//        );
//
//        Stream<DynamicTest> tests = userList.stream().map(
//                usr -> DynamicTest.dynamicTest(
//                        "Can update: " + usr.getUsername(),
//                        ()->{
//                            userService.updateUser(
//                                    userMapper.apply(usr).id(),
//                                    null,
//                                    null,
//                                    null
//                            );
//                            verify(userRepository).findById(usr.getID());
//                        }
//                )
//        );
//        return tests;
//    }
//
//    @Test
//    void canTryGetUser() {
//        //when
//        userService.getUser(userDTO.id());
//
//        //then
//        verify(userRepository).findById(any(UUID.class));
//    }
//
//    @Test
//    void canGetUser() {
//        //when
//        userService.getUser(userDTO.id());
//
//        //then
//        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
//        verify(userRepository).findById(uuidArgumentCaptor.capture());
//        UUID capturedUUID = uuidArgumentCaptor.getValue();
//        assertThat(capturedUUID).isEqualTo(userDTO.id());
//    }
//
//    @Test
//    void canTryAddUser() {
//        //when
//        userService.addUser(
//                userDTO.username(),
//                userDTO.email(),
//                userDTO.roles()
//                        .stream()
//                        .map(
//                                ruleDTO -> ruleService.getRule(ruleDTO.id()))
//                        .collect(Collectors.toList()
//                        ),
//                userDTO.password());
//
//        //then
//        verify(userRepository).save(any());
//    }
//
//    @Test
//    void canAddUser() {
//        //when
//        userService.addUser(
//                userDTO.username(),
//                userDTO.email(),
//                rules,
//                userDTO.password());
//
//        //then
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User capturedUser = userArgumentCaptor.getValue();
//        assertThat(capturedUser).isEqualTo(user);
//
//    }
//
//    @Test
//    void canGetAllUsers() {
//        //when
//        userService.getAllUsers();
//
//        //then
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    void canTryUpdateUserEmail() throws AccountLockedException {
//        //given
//        String email = "newemail@usr.usr";
//
//        given(userRepository.findById(userDTO.id()))
//                .willReturn(Optional.of(user));
//        given(userRepository.save(any(User.class)))
//                .willReturn(user);
//
//        user.setEmail(email);
//        //when
//        User updated = userService.updateUser(
//                userDTO.id(),
//                null,
//                email,
//                null
//        );
//
//        //then
//        assertThat(updated).isEqualTo(user);
//    }
//
//    @Test
//    void canTryUpdateUsername() throws AccountLockedException {
//        //given
//        String newUsername = "NewUsername";
//
//        given(userRepository.findById(userDTO.id()))
//                .willReturn(Optional.of(user));
//        given(userRepository.save(any(User.class)))
//                .willReturn(user);
//
//        user.setUsername(newUsername);
//        //when
//        User updated = userService.updateUser(
//                userDTO.id(),
//                newUsername,
//                null,
//                null
//        );
//
//        //then
//        assertThat(updated).isEqualTo(user);
//    }
//
//    @Test
//    void canTryUpdateUserPassword() throws AccountLockedException {
//        //given
//        String newPassword = "newPass";
//
//        given(userRepository.findById(userDTO.id()))
//                .willReturn(Optional.of(user));
//        given(userRepository.save(any(User.class)))
//                .willReturn(user);
//
//        user.setPassword(newPassword);
//        //when
//        User updated = userService.updateUser(
//                userDTO.id(),
//                null,
//                null,
//                newPassword
//        );
//
//        //then
//        assertThat(updated).isEqualTo(user);
//    }
//
//    @Test
//    @Disabled
//    void canTryUpdateUserButNotFound() throws AccountLockedException {
//        //given
//        String username = "User2";
//        //when
//         User setted = userService.updateUser(
//                UUID.randomUUID(),
//                username,
//                null,
//                null);
//        //then
//
//        assertNull(setted);
//    }
//
//    @Test
//    void canTryUpdateLockedAccountButExceptionThrows() throws AccountLockedException {
//        //given
//        String newPassword = "newPass";
//        user.lockData();
//        userDTO = userMapper.apply(user);
//
//        given(userRepository.findById(userDTO.id()))
//                .willReturn(Optional.of(user));
//
//        user.setPassword(newPassword);
//        //then
//        assertThatThrownBy(()-> userService.updateUser(
//                userDTO.id(),
//                null,
//                null,
//                newPassword
//        )).isInstanceOf(AccountLockedException.class)
//                .hasMessageContaining("Account " + user.getUsername() + " is locked");
//
//    }
//
//    @Test
//    void deleteUser() {
//
//    }
//}