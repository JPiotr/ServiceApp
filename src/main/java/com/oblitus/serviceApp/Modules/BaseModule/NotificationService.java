package com.oblitus.serviceApp.Modules.BaseModule;

import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.NotificationDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService implements IService<Notification, NotificationDTO> {
    private final NotificationRepository repository;
    private final UserService service;
    /**
     * @param dto
     * @return
     */
    @Override
    public Notification get(NotificationDTO dto) {
        var opt = repository.findById(dto.id());
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    /**
     * @return
     */
    @Override
    public Collection<Notification> getAll() {
        return repository.findAll();
    }

    public Collection<Notification> getAllUserNotificationOptions(UUID userUuid){
        return repository.findAllByUser(service.get(new UserDTO(userUuid)));
    }

    public Collection<Notification> addAll(Collection<NotificationDTO> dtos){
        Collection<Notification> collection = new ArrayList<>();
        for (var dto:
             dtos) {
            collection.add(add(dto));
        }
        return collection;
    }

    public Collection<Notification> updateAll(Collection<NotificationDTO> dtos){
        Collection<Notification> collection = new ArrayList<>();
        for (var dto:
                dtos) {
            collection.add(update(dto));
        }
        return collection;

    }

    /**
     * @param dto
     * @return
     */
    @Override
    public Notification update(NotificationDTO dto) {
        var notification = get(dto);
        if(dto.app() != null){
            notification.setApp(dto.app());
            notification.setLastModificationDate();
        }
        if(dto.email() != null){
            notification.setEmail(dto.email());
            notification.setLastModificationDate();

        }
        return repository.save(notification);
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public Notification add(NotificationDTO dto) {
        var user = service.get(new UserDTO(dto.user()));
        return repository.save(new Notification(user,dto.app(),dto.email(),dto.type()));
    }

    /**
     * @param dto
     * @return
     */
    @Override
    @Deprecated
    public boolean delete(NotificationDTO dto) {
        return false;
    }
}
