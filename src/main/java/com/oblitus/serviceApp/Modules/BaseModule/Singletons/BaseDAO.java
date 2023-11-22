package com.oblitus.serviceApp.Modules.BaseModule.Singletons;

import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.BaseModule.NotificationService;
import com.oblitus.serviceApp.Modules.Service.Singletons.ServiceDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseDAO {
    private final FileService FILE_SERVICE;
    private final NotificationService NOTYFICATION_SERVICE;
    public BaseDAO getInstance(){return this;}

    public FileService getFileService(){
        return FILE_SERVICE;
    }
    public NotificationService getNotificationService(){return NOTYFICATION_SERVICE;}
}
