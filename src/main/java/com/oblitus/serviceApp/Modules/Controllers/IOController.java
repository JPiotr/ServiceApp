package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.File.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Common.File.File;
import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Common.Response;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class IOController {
    private final FileService fileService;
    private final FileResponseMapper fileResponseMapper;
    @GetMapping("/{objectId}/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable @Validated String fileName,
                                          @PathVariable @Validated UUID objectId,
                                          @RequestParam @Validated @Nullable Boolean download) {
        File file = fileService.getFile(objectId,fileName);
        if(download != null && download){
            return ResponseEntity.ok().header(
                            "attachment; filename=\""+file.getFileName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file.getBlob());
        }
        return ResponseEntity.ok().header(
                "attachment; filename=\""+file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getFileExtension()))
                .body(file.getBlob());
    }

    @PostMapping("/{objectId}/upload")
    public ResponseEntity<Response> uploadFileForObject(@PathVariable @Validated UUID objectId,
                                                        @RequestParam @Validated MultipartFile file,
                                                        @RequestParam @Validated @Nullable String description){
        try{
            return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("File uploaded.")
                        .data(Map.of("file", fileResponseMapper.apply(
                                fileService.addFile(objectId,file,description)
                        )))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
            );


        }
        catch (Exception ex){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("File not uploaded.")
                            .data(Map.of("file", " "))
                            .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .reason(ex.getMessage())
                            .build()
            );
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<Response> uploadFileForObject(@RequestParam @Validated MultipartFile file,
                                                        @RequestParam @Validated @Nullable String description){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("File uploaded.")
                            .data(Map.of("file", fileResponseMapper.apply(
                                    fileService.addFileWithoutObj(file,description)
                            )))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );


        }
        catch (Exception ex){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("File not uploaded.")
                            .data(Map.of("file", " "))
                            .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .reason(ex.getMessage())
                            .build()
            );
        }
    }
    @Deprecated
    @PostMapping("/{objectId}/uploadMultiple")
    public ResponseEntity<Response> uploadFilesForObject(@PathVariable UUID objectId,
                                                         @RequestParam @Validated Map<String, MultipartFile> map){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Files uploaded.")
                            .data(Map.of("file",
                                    fileService.addFiles(objectId,map)
                            ))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );


        }
        catch (Exception ex){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Files not uploaded.")
                            .data(Map.of("file", " "))
                            .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .reason(ex.getMessage())
                            .build()
            );
        }
    }
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Response> deleteFile(@PathVariable @Validated UUID fileId){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Try to drop File")
                            .data(Map.of("result", fileService.deleteFile(fileId)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("File " + fileId + " not found.")
                            .data(Map.of("user", null))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }
    }

    @PostMapping("/{fileId}")
    public void updateFile(@PathVariable @Validated String fileId){

    }
}
