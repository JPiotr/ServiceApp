package com.oblitus.serviceApp.Common.File.DTOs;

import com.oblitus.serviceApp.Common.File.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Function;

@Service
public class FileResponseMapper implements Function<File, FileResponse> {
    @Override
    public FileResponse apply(File file) {
        if(file != null){
            return new FileResponse(
                    file.getUuid(),
                    file.getObjectId(),
                    file.getFileName(),
                    file.getFileExtension(),
                    file.getFileType(),
                    file.getCreationDate(),
                    file.getLastModificationDate(),
                    "http://localhost:8080/files/"+file.getObjectId()+"/"+file.getFileName(),
                    file.getDescription()
            );
        }
        return null;
    }
}
