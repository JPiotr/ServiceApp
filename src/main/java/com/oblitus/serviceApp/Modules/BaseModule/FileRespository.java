package com.oblitus.serviceApp.Modules.BaseModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRespository extends JpaRepository<File, UUID> {
}
