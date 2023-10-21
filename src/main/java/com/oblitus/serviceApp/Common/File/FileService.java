package com.oblitus.serviceApp.Common.File;

import com.oblitus.serviceApp.Modules.Service.Ticket;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FileService {
    private final FileRespository fileRespository;

    public File addFile(UUID objectID, MultipartFile file) throws IOException {
        return  fileRespository.save(new File(objectID,file.getName(),file.getContentType(), file.getBytes()));
    }

    public boolean addFiles(UUID objectID, Collection<MultipartFile> files) throws IOException {
        if(files.isEmpty()){
            return false;
        }
        for (var file:
             files) {
            fileRespository.save(new File(objectID,file.getName(),file.getContentType(), file.getBytes()));
        }
        return true;
    }
    public Collection<File> getObjectFiles(UUID objectID){
        return fileRespository.findAll().stream().filter(file -> file.ObjectId == objectID).collect(Collectors.toList());
    }

    public File getFile(UUID objectId, String name){
        return fileRespository.findAll().stream().filter(file -> (file.FileName + file.FileExtension).equals(name))
                .findFirst().orElse(null);
    }

    public boolean deleteFile(UUID id){
        Optional<File> opt = fileRespository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        fileRespository.delete(opt.get());
        return true;
    }

//    public
}
