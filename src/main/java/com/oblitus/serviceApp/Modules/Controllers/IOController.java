package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class IOController {

    private final FileService fileService;
    @GetMapping("/{objectId}/{fileName}")
    public Resource getFile(@PathVariable @Validated String fileName, @PathVariable @Validated UUID objectId) throws IOException {
        return new ByteArrayResource(fileService.getFile(objectId,fileName).getBlob()).createRelative("/");
    }

    @PostMapping("{objectId}/upload")
    public void uploadFileForObject(@PathVariable @Validated UUID objectId,@RequestParam @Validated MultipartFile file){

        try{
            fileService.addFile(objectId, file);
        }
        catch (Exception ex){
            return;
        }
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Response> deleteFile(@PathVariable @Validated String fileId){

    }

    @PostMapping("/{fileId}")
    public void updateFile(@PathVariable @Validated String fileId){

    }
}
