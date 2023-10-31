package com.oblitus.serviceApp.Abstracts;

import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for every Entity in application.
 */
@MappedSuperclass
@AllArgsConstructor
public abstract class EntityBase{ //implements Serializable {
    /**
     * Autogenerated UUID = Entity ID
     */
    @Getter
    @Id
    protected UUID uuid;
    /**
     * Autogenerated Id = Database ID
     */
    @Getter
//    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long ID = 1L;

    @Getter
    @CreatedDate
    protected LocalDateTime creationDate;

    @Getter
    @LastModifiedDate
    protected LocalDateTime lastModificationDate;

    public Boolean readOnly;
    protected Boolean locked;
    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class)
    protected User creator;

    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class)
    protected User lastEditedBy;



    /**
     * Constructor witch set properties:
     * @ID = Random UUID
     * @creationDate = Now
     * @lastModyficationDate = Now
     * @readOnly = false
     * @locked = false
     */
    public EntityBase() {
        uuid = UUID.randomUUID();
        this.creationDate = LocalDateTime.now();
        this.lastModificationDate = LocalDateTime.now();
//        this.creator = null;
        this.readOnly = false;
        this.locked = false;
    }
    /**
     * Constructor witch set properties:
     * @ID = Specified UUID
     * @creationDate = Now
     * @lastModyficationDate = Now
     * @readOnly = false
     * @locked = false
     */
    public EntityBase(String uuid){
        this.uuid = UUID.fromString(uuid);
        this.creationDate = LocalDateTime.now();
        this.lastModificationDate = LocalDateTime.now();
//        this.creator = null;
        this.readOnly = false;
        this.locked = false;
    }

    /**
     * Method make Entity locked
     */
    public void lockData(){
        setLastModificationDate();
        locked = true;
    }

    public boolean isLocked(){
        return locked;
    }

    public void setLastModificationDate() {
        lastModificationDate = LocalDateTime.now();
    }

    public EntityBase setReadOnly(Boolean readOnly) {
        setLastModificationDate();
        this.readOnly = readOnly;
        return this;
    }

}
