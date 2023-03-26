package com.oblitus.serviceApp.Abstracts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public abstract class EntityBase{
    @Id
    protected UUID ID;
    protected LocalDateTime CreationDate;
    protected LocalDateTime LastModyficationDate;
    public String Name;

    public EntityBase(String name) {
        ID = UUID.randomUUID();
        CreationDate = LocalDateTime.now();
        LastModyficationDate = LocalDateTime.now();
        Name = name;
    }
}
