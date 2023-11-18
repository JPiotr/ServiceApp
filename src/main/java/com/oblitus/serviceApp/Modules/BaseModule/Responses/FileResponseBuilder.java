package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;

import java.util.UUID;

public class FileResponseBuilder extends BaseBuilder<FileResponse> {
    @Override
    public void setEntity() {
        entity = new FileResponse();
    }

    public FileResponseBuilder setObjectId(UUID objectId){
        super.entity.setObjectId(objectId);
        return this;
    }
    public FileResponseBuilder setName(String name){
        super.entity.setName(name);
        return this;
    }
    public FileResponseBuilder setFileExtension(String fileExtension){
        super.entity.setFileExtension(fileExtension);
        return this;
    }
    public FileResponseBuilder setFileType(String fileType){
        super.entity.setFileType(fileType);
        return this;
    }
    public FileResponseBuilder setUrl(String url){
        super.entity.setUrl(url);
        return this;
    }
    public FileResponseBuilder setDescription(String description ){
        super.entity.setDescription(description);
        return this;
    }

}
