package com.oblitus.serviceApp.Modules.BaseModule.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.File;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FileResponseMapper extends BaseResponseMapper<FileResponseBuilder> implements Function<File, FileResponse> {
    @Override
    public FileResponse apply(File file) {
        if(file == null){
            return new FileResponse();
        }
        return this.useBuilder(new FileResponseBuilder())
                .setObjectId(file.getObjectId())
                .setName(file.getFileName())
                .setFileExtension(file.getFileExtension())
                .setDescription(file.getDescription())
                .setUrl(
                        "http://localhost:8080/files/"+file.getObjectId()+"/"+file.getFileName()
                )
                .setUUID(file.getUuid())
                .setCreationDate(file.getCreationDate())
                .setLastModificationDate(file.getLastModificationDate())
                .setId(file.getID())
                .build();
    }
}
