package com.oblitus.serviceApp.Abstracts;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class EntityBase{
    @Id
    protected UUID ID;
    @CreatedDate
    protected LocalDateTime CreationDate;
    @LastModifiedDate
    protected LocalDateTime LastModificationDate;
    public String Name;
    public Boolean ReadOnly;
    //Once Locked it can't be edited anyway
    protected Boolean Locked;

    public EntityBase(String name) {
        ID = UUID.randomUUID();
        CreationDate = LocalDateTime.now();
        LastModificationDate = LocalDateTime.now();
        Name = name;
        ReadOnly = false;
        Locked = false;
    }

    public void lockData(){
        setLastModificationDate();
        Locked = true;
    }

    public String getName(){
        return Name;
    }

    public LocalDateTime getLastModificationDate() {
        return LastModificationDate;
    }

    public void setLastModificationDate() {
        LastModificationDate = LocalDateTime.now();
    }

    public Boolean getReadOnly() {
        return ReadOnly;
    }

    public EntityBase setReadOnly(Boolean readOnly) {
        setLastModificationDate();
        ReadOnly = readOnly;
        return this;
    }


}
