package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.util.UUID;

/*/public record UserDTO_old (
//      UUID id,
//      String username,
//      String name,
//      String surname,
//      String email,
//      LocalDateTime lastLoginDateTime,
//      LocalDateTime credentialExpireDate,
//      LocalDateTime accountExpireDate,
//      boolean isEnabled,
//      boolean isExpired,
//      boolean isCredentialExpired,
//      String password,
//      Collection<RuleDTO> roles
//) {
//    public UserDTO(String username, String name, String surname, String email, String password, Collection<RuleDTO> roles) {
//        this(null,
//                username,
//                name,
//                surname,
//                email,
//                null,
//                null,
//                null,
//                true,
//                false,
//                false,
//                password,
//                roles);
//    }
//}*/
public record UserDTO(
      UUID id,
      String email,
      String userName,
      String name,
      String surname,
      String password
){}

