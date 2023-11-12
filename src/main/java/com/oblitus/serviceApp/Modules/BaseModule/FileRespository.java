package com.oblitus.serviceApp.Modules.BaseModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface FileRespository extends JpaRepository<File, UUID> {
}
