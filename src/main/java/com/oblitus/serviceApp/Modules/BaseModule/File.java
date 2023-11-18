package com.oblitus.serviceApp.Modules.BaseModule;

import com.oblitus.serviceApp.Abstracts.EntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "files")
public class File extends EntityBase {
    private static long num = 0;
    protected UUID ObjectId;
    protected String FileName;
    protected String FileType;
    protected String FileExtension;
    protected String Description;
    @Lob
    protected byte[] Blob;

    public File(String uuid, UUID objectId, String fileName, String fileExtension, byte[] blob, String description) {
        super(uuid);
        ObjectId = objectId;
        FileName = fileName;
        FileExtension = fileExtension;
        Blob = blob;
        FileType = fileName.substring(fileName.lastIndexOf('.'));
        Description = description;
        setID(File.num);
        File.num += 1;
    }

    public File(UUID objectId, String fileName, String fileExtension, byte[] blob, String description) {
        ObjectId = objectId;
        FileName = fileName;
        FileExtension = fileExtension;
        Blob = blob;
        FileType = fileName.substring(fileName.lastIndexOf('.'));
        Description = description;
        setID(File.num);
        File.num += 1;
    }
}
