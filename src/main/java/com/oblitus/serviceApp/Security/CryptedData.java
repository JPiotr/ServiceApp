package com.oblitus.serviceApp.Security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CryptedData {
    UUID cryptoKey = UUID.fromString("MyCryptoKeyForSecurity###145");
}

