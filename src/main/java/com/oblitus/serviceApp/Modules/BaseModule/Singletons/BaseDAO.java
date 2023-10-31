package com.oblitus.serviceApp.Modules.BaseModule.Singletons;

import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.Service.Singletons.ServiceDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseDAO {
    private final FileService FILE_SERVICE;
    public BaseDAO getInstance(){return this;}

    public FileService getFileService(){
        return FILE_SERVICE;
    }
}
