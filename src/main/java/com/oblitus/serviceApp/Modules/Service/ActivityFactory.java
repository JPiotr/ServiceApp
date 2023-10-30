package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ActivityFactory {
    private final ActivityRepository activityRepository;

    private String className;

    public ActivityFactory prepare(String className){
        if(Objects.equals(className, Ticket.class.getName()) ||
                Objects.equals(className, User.class.getName()) ||
                Objects.equals(className, Comment.class.getName())){
            this.className = className;
            return this;
        }
        throw new IllegalArgumentException();
    }
    public Activity create(String fieldName, String newValue, String oldValue, User creator, EntityBase objectActivity){
        return switch (className) {
            case "Ticket" -> activityRepository.save(new TicketActivity(fieldName, newValue, oldValue, creator, (Ticket) objectActivity));
            case "User" -> activityRepository.save(new UserActivity(fieldName, newValue, oldValue, creator, (User) objectActivity));
            case "Comment" -> activityRepository.save(new CommentActivity(fieldName, newValue, oldValue, creator, (Comment) objectActivity));
            default -> null;
        };
    }

    private static class TicketActivity extends Activity{
        public TicketActivity(String fieldName, String newValue, String oldValue, User creator, Ticket objectActivity) {
            super(objectActivity.getClass().getName(), fieldName, newValue, oldValue, EActivityTypes.SYSTEM.toString(), creator, objectActivity.getUuid());
        }
    }

    private static class UserActivity extends Activity{
        public UserActivity(String fieldName, String newValue, String oldValue, User creator, User objectActivity) {
            super(objectActivity.getClass().getName(), fieldName, newValue, oldValue, EActivityTypes.SYSTEM.toString(), creator, objectActivity.getUuid());
        }
    }

    private static class CommentActivity extends Activity{
        public CommentActivity(String fieldName, String newValue, String oldValue, User creator, Comment objectActivity) {
            super(objectActivity.getClass().getName(), fieldName, newValue, oldValue, EActivityTypes.USER.toString(), creator, objectActivity.getUuid());
        }
    }

}