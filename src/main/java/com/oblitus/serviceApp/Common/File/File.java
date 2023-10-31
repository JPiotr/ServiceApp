package com.oblitus.serviceApp.Common.File;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "files")
public class File extends EntityBase {
    UUID ObjectId;
    String FileName;
    String FileType;
    String FileExtension;
    String Description;
    @Lob
    byte[] Blob;
    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class)
    protected User creator;

    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class)
    protected User lastEditedBy;

    public File(String uuid, UUID objectId, String fileName, String fileExtension, byte[] blob, String description) {
        super(uuid);
        ObjectId = objectId;
        FileName = fileName;
        FileExtension = fileExtension;
        Blob = blob;
        FileType = fileName.substring(fileName.lastIndexOf('.'));
        Description = description;
    }

    public File(UUID objectId, String fileName, String fileExtension, byte[] blob, String description) {
        ObjectId = objectId;
        FileName = fileName;
        FileExtension = fileExtension;
        Blob = blob;
        FileType = fileName.substring(fileName.lastIndexOf('.'));
        Description = description;
    }
}
