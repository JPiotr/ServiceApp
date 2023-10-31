package com.oblitus.serviceApp.Modules.BaseModule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FileService {
    private final FileRespository fileRespository;

    public File addFile(UUID objectID, MultipartFile file, String description){
        try {
            return fileRespository.save(
                    new File(objectID, file.getOriginalFilename(), file.getContentType(), file.getBytes(), description)
            );
        }
        catch (IOException ex){
            return new File();
        }
    }

    public File addFileWithoutObj(MultipartFile file, String description){
        try {
            return fileRespository.save(
                    new File(null, file.getOriginalFilename(), file.getContentType(), file.getBytes(), description)
            );
        }
        catch (IOException ex){
            return new File();
        }
    }

    public boolean addFiles(UUID objectID, Map<String,MultipartFile> files) {
        if(files.isEmpty()){
            return false;
        }
        for (var file:files.keySet()) {
            var f = files.get(file);
            try{
                fileRespository.save(
                        new File(objectID, f.getOriginalFilename(),f.getContentType(),f.getBytes(),file));
            }
            catch (IOException ex){
                fileRespository.save(new File());
            }
        }
        return true;
    }
    public Collection<File> getObjectFiles(UUID objectID){
        var list = fileRespository.findAll().stream()
                .filter(file -> file.ObjectId != null)
                .filter(
                        file -> file.ObjectId.equals(objectID))
                .collect(Collectors.toList());
        return list;
    }

    public File getFile(UUID objectId, String name){
        return fileRespository.findAll().stream()
                .filter(file -> (file.getFileName()).equals(name))
                .filter(file -> (file.getObjectId()).equals(objectId))
                .toList().get(0);
    }

    public File getFileById(UUID fileId){
        return fileRespository.findById(fileId).orElse(null);
    }

    public void updateFile(File file){
        fileRespository.save(file);
    }
    public boolean deleteFile(UUID id){
        Optional<File> opt = fileRespository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        fileRespository.delete(opt.get());
        return true;
    }

}
