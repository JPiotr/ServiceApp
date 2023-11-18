package com.oblitus.serviceApp.Modules.Analytics;

import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnaliseActivityService {
    private final ActivityRepository activityRepository;

    //todo: całkowita aktywność, na tiketach, comentarzach, utworzenie tiketów, time period
    //  user - > aktywnośc - w danym okresie czasu

    @Getter
    private enum AnaliseType{
        ALL_TIME("All time"),
        PERIOD_TIME("Period time"),
        PERIOD_DATES("Period dates")
        ;
        private final String value;

        AnaliseType(String value) {
            this.value = value;
        }
    }
    @Getter
    public enum RateType{
        RATE("Rate restricted", "divided by restricted scope of activities recorded in system"),
        RATE_ALL("Rate all", "divided by all activities recorded in system"),
        COUNT("Count restricted", ""),
        COUNT_ALL("Count all", "")
        ;
        private final String value;
        private final String desc;

        RateType(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    //todo: Move this enums and use them to create Activities
    @Getter
    public enum UserCUEventsFieldsNames{
        NAME("Name"),
        PASSWORD("Password"),
        SURNAME("Surname"),
        AVATAR("Avatar"),
        RULES("Rules"),
        EMAIL("Email"),
        NEW_USER("New User"),
        CHANGE_PASSWORD("Change Password"),
        SET_PASSWORD("Set Password");
        private final String value;

        UserCUEventsFieldsNames(String value) {
            this.value = value;
        }
    }
    @Getter
    public enum CommentCUEventsFieldsNames{
        CONTENT("Content"),
        NEW_COMMENT("New Comment");
        private final String value;

        CommentCUEventsFieldsNames(String value) {
            this.value = value;
        }
    }
    @Getter
    public enum TicketCUEventsFieldsNames{
        PRIORITY("Priority"),
        ASSIGNED("Assigned"),
        STATE("State"),
        NOTE("Note"),
        ATTACHMENTS("Attachments"),
        DESCRIPTION("Description"),
        TITLE("Title"),
        NEW_TICKET("New Ticket"),
        ADD_COMMENT("New Comment");
        private final String value;

        TicketCUEventsFieldsNames(String value) {
            this.value = value;
        }
    }

    //todo: fix
    private RateResponse rateUserActivity(User user, RateType rateType){
        return switch (rateType){
            case RATE, COUNT -> null;
            case RATE_ALL -> {
                double act = activityRepository.countAll();
                double cnt = activityRepository.countActivitiesByCreator(user);
                yield new RateResponse("User activity rate",
                        RateType.RATE_ALL.getValue(),
                        act == 0D && cnt == 0D ? 0D : cnt/act,
                        "User activity rate witch shows user activity " + RateType.RATE_ALL.getDesc(),
                        AnaliseType.ALL_TIME.getValue());
            }
            case COUNT_ALL -> new RateResponse("User activity rate",
                    RateType.COUNT_ALL.getValue(),
                    activityRepository.countActivitiesByCreator(user),
                    "User activity count witch shows user activity recorded in system",
                    AnaliseType.ALL_TIME.getValue());
        };
    }
    private RateResponse rateUserActivity(User user, RateType rateType, LocalDate start, LocalDate  end){
        return switch (rateType){
            case RATE_ALL, COUNT_ALL -> rateUserActivity(user,rateType);
            case RATE -> {
                double act = activityRepository.countActivitiesByDateBetween(start,end);
                double cnt = activityRepository.countActivitiesByCreatorAndDateBetween(user,start,end);
                yield new RateResponse("User activity rate",
                        RateType.RATE.getValue(),
                        act == 0D && cnt == 0D ? 0D : cnt/act,
                        "User activity rate witch shows user activity " + RateType.RATE.getDesc(),
                        AnaliseType.PERIOD_DATES.getValue());
            }
            case COUNT -> new RateResponse("User activity rate",
                    RateType.COUNT.getValue(),
                    activityRepository.countActivitiesByCreatorAndDateBetween(user,start,end),
                    "User activity rate witch shows user activity " + RateType.COUNT.getDesc(),
                    AnaliseType.PERIOD_DATES.getValue());
        };
    }
    private RateResponse rateUserActivity(User user, RateType rateType, LocalTime start, LocalTime end){
        return switch (rateType){
            case RATE_ALL, COUNT_ALL -> rateUserActivity(user,rateType);
            case RATE -> {
                double act = activityRepository.countActivitiesByTimeBetween(start,end);
                double cnt = activityRepository.countActivitiesByCreatorAndTimeBetween(user,start,end);
                yield new RateResponse("User activity rate",
                        RateType.RATE.getValue(),
                        act == 0D && cnt == 0D ? 0D : cnt/act,
                        "User activity rate witch shows user activity " + RateType.RATE.getDesc(),
                        AnaliseType.PERIOD_DATES.getValue());
            }
            case COUNT -> new RateResponse("User activity rate",
                    RateType.COUNT.getValue(),
                    activityRepository.countActivitiesByCreatorAndTimeBetween(user,start,end),
                    "User activity rate witch shows user activity " + RateType.COUNT.getDesc(),
                    AnaliseType.PERIOD_DATES.getValue());
        };
    }


    public RateResponse rateAllUserActivity(User user){
        double allActivities = activityRepository.countAll();
        double count = activityRepository.countActivitiesByCreator(user);
        return new RateResponse("User activity rate",
                RateType.RATE.getValue(),
                allActivities == 0D && count == 0D ? 0D : count/allActivities * 100,
                "User activity rate witch shows user activity divided by all activities recorded in system",
                AnaliseType.ALL_TIME.getValue());
    }
    public RateResponse rateObjectActivity(UUID object){
        double allActivities = activityRepository.countAll();
        double count = activityRepository.countActivitiesByObjectActivity(object);
        return new RateResponse("Object activity rate",
                RateType.RATE.getValue(),
                allActivities == 0D && count == 0D ? 0D : count/allActivities * 100,
                "Object activity rate shows activities on object by Type divided by all activities recorded in system",
                AnaliseType.ALL_TIME.getValue());
    }
    public RateResponse rateUserActivitiesOnObjectType(User creator, ActivityTypes type){
        double allActivities = activityRepository.countAll();
        double count = getSpecificTypeCount(type,creator);
        return new RateResponse("User activity rate on object Type",
                RateType.RATE.getValue(),
                allActivities == 0D && count == 0D ? 0D : count/allActivities * 100,
                "User activity rate that shows user activity on specific object type divided by all activities recorded in system",
                AnaliseType.ALL_TIME.getValue());
    }
    public ChartResponse multiSetChartOfRatesByCreatorInPeriod(User creator, LocalDate startDate, LocalDate endDate){
        List<ActivityTypes> list = Arrays.stream(ActivityTypes.values()).toList();
        List<ChartResponse> chartResponses = new ArrayList<>(List.of());
        list.forEach(a -> {
            List<Double> collection = new ArrayList<>();
            var dates = startDate.datesUntil(endDate);
            double allActivities = activityRepository.countAll();
            dates.forEach(date -> {
                double count = getSpecificTypeCount(a, creator, date);
                collection.add(allActivities == 0D && count == 0D ? 0D : count/allActivities * 100);
            });
            chartResponses.add(new ChartResponse("Chart of User activities rates in period (" + a.getValue() + ")",
                    getHeaders(startDate,endDate),
                    collection,
                    "Chart of User activities rates day by day in specific period and on specific object Type",
                    AnaliseType.PERIOD_DATES.getValue(),
                    RateType.RATE.getValue()));
        });
        return new ChartResponse("Chart of User activities rates in period (all types)",
                getHeaders(startDate,endDate),
                "Chart of User activities rates day by day in specific period and on all object Types",
                AnaliseType.PERIOD_DATES.getValue(),
                RateType.RATE.getValue(),
                chartResponses
                );

    }

    public ChartResponse chartOfCountsByCreatorInTimePeriod(User user, int minutes){
        List<Double> collection = new ArrayList<>();
        List<LocalTime> times = getHeaders(minutes).stream().toList();
        for(int i = 0; i < times.size()-1; i++){
            collection.add(activityRepository.countAllByCreatorAndTimeBetween(user,times.get(i), times.get(i+1)));
        }
        return new ChartResponse("Chart of User activities in period",
                getHeaders(LocalDate.now(),minutes),
                collection,
                "Chart of User activities in period (time)",
                RateType.COUNT.getValue(),
                AnaliseType.PERIOD_TIME.getValue()
        );
    }
    public ChartResponse chartOfCountsByCreatorInPeriod(User user, LocalDate startDate, LocalDate endDate){
        List<Double> collection = new ArrayList<>();
        var dates = startDate.datesUntil(endDate);
        dates.forEach(date -> collection.add(activityRepository.countAllByCreatorAndDate(user, date)));
        return new ChartResponse("Chart of User activities in period",
                getHeaders(startDate,endDate),
                collection,
                "Chart of User activities in period (dates)",
                AnaliseType.PERIOD_DATES.getValue(),
                RateType.COUNT.getValue() );
    }
    public ChartResponse chartOfRatesByClassNameAndCreatorInPeriod(ActivityTypes type, User creator, LocalDate startDate, LocalDate endDate){
        List<Double> collection = new ArrayList<>();
        var dates = startDate.datesUntil(endDate);
        double allActivities = activityRepository.countAll();
        dates.forEach(date -> {
            double count = getSpecificTypeCount(type, creator, date);
            collection.add(allActivities == 0D && count == 0D ? 0D : count/allActivities * 100);
        });
        return new ChartResponse("Chart of User activities rates in period",
                getHeaders(startDate,endDate),
                collection,
                "Chart of User activities rates day by day in specific period and on specific object Type",
                AnaliseType.PERIOD_DATES.getValue(),
                RateType.RATE.getValue());
    }
    public ChartResponse chartOfCountsByCreatorAndClassNameInTimePeriod(ActivityTypes type, User creator, int minutes){
        List<Double> collection = new ArrayList<>();
        List<LocalTime> times = getHeaders(minutes).stream().toList();
        for(int i = 0; i < times.size()-1; i++){
            collection.add(getSpecificTypeCount(type,creator ,times.get(i), times.get(i+1)));
        }
        return new ChartResponse("Chart of User activities on object type in period",
                getHeaders(LocalDate.now(),minutes),
                collection,
                "Chart of User activities on object type in period (time)",
                RateType.COUNT.getValue(),
                AnaliseType.PERIOD_DATES.getValue()
        );
    }
    public ChartResponse multiSetChartOfRatesByClassNameAndCreatorInTimePeriod(User creator, int minutes){
        List<ActivityTypes> list = Arrays.stream(ActivityTypes.values()).toList();
        List<ChartResponse> chartResponses = new ArrayList<>(List.of());
        list.forEach(a -> {
            List<Double> collection = new ArrayList<>();
            List<LocalTime> times = getHeaders(minutes).stream().toList();
            for(int i = 0; i < times.size()-1; i++){
                collection.add(getSpecificTypeCount(a,creator ,times.get(i), times.get(i+1)));
            }
            chartResponses.add(new ChartResponse("Chart of User activities on object in period ("+ a.getValue() + ")",
                    getHeaders(LocalDate.now(),minutes),
                    collection,
                    "Chart of User activities on object type in period (time)",
                    RateType.COUNT.getValue(),
                    AnaliseType.PERIOD_DATES.getValue()
            ));
        });
        return new ChartResponse("Chart of User activities rates in period (all types)",
                getHeaders(LocalDate.now(),minutes),
                "Chart of User activities rates day by day in specific period (time) and on all object Types",
                AnaliseType.PERIOD_DATES.getValue(),
                RateType.RATE.getValue(),
                chartResponses
                );

    }
    public ChartResponse chartOfRatesByClassNameAndCreator(User creator){
        List<Double> collection = new ArrayList<>();
        double allActivities = activityRepository.countAll();
        Arrays.stream(ActivityTypes.values()).toList().forEach(x-> {
            double count = getSpecificTypeCount(x, creator);
            collection.add(allActivities == 0D && count == 0D ? 0D : count/allActivities * 100);
        });

        return new ChartResponse("Chart of User activities rates on object type",
                getHeaders(),
                collection,
                "Chart of User activities rates on object types in all time",
                RateType.RATE.getValue(),
                AnaliseType.ALL_TIME.getValue());
    }
    public ChartResponse chartOfCountsByClassNameAndCreator(User creator){
        List<Double> collection = new ArrayList<>();
        Arrays.stream(ActivityTypes.values()).toList().forEach(x-> {
            double count = getSpecificTypeCount(x, creator);
            collection.add(count);
        });

        return new ChartResponse("Chart of User activities on object type",
                getHeaders(),
                collection,
                "Chart of User activities on object types in all time",
                RateType.COUNT.getValue(),
                AnaliseType.ALL_TIME.getValue());
    }

    public ChartResponse chartOfCountsByCreatorAndClassNameDetailed(User user, ActivityTypes type){
        List<Double> collection = new ArrayList<>();
        switch (type){
            case TICKET -> Arrays.stream(TicketCUEventsFieldsNames.values()).toList().forEach(
                    x->{
                        collection.add(getSpecificTypeAndFieldCount(type, x, user));
                    });
            case USER -> Arrays.stream(UserCUEventsFieldsNames.values()).toList().forEach(
                    x->{
                        collection.add(getSpecificTypeAndFieldCount(type, x, user));
                    });
            case COMMENT -> Arrays.stream(CommentCUEventsFieldsNames.values()).toList().forEach(
                    x->{
                        collection.add(getSpecificTypeAndFieldCount(type, x, user));
                    });
        }
        return new ChartResponse("Chart of User activities on object type",
                getSpecificHeaders(type),
                collection,
                "Chart of User activities on object types in all time (detailed)",
                RateType.COUNT.getValue(),
                AnaliseType.ALL_TIME.getValue());
    }
    public ChartResponse chartOfCountsByCreatorAndClassNameAndEventInPeriod(User user, ActivityTypes type, Enum<?> field, LocalDate start, LocalDate end){
        List<Double> collection = new ArrayList<>();
        Collection<LocalDate> dates = getHeaders(start,end);
        dates.forEach(d->collection.add(getSpecificTypeAndFieldCount(type, field, user,d)));


        return new ChartResponse("Chart of User activities on object type in period",
                dates,
                collection,
                "Chart of User activities on object types in all time and specific field in period",
                RateType.COUNT.getValue(),
                AnaliseType.PERIOD_DATES.getValue());
    }

    public ChartResponse chartOfCountsByCreatorAndClassNameAndEventInTimePeriod(User user, ActivityTypes type, Enum<?> field, int minutes){
        List<Double> collection = new ArrayList<>();
        List<LocalTime> times = getHeaders(minutes).stream().toList();
        for(int i = 0; i < times.size()-1; i++){
            collection.add(getSpecificTypeAndFieldCount(type, field, user,times.get(i), times.get(i+1)));
        }


        return new ChartResponse("Chart of User activities on object type in period",
                getHeaders(LocalDate.now(),minutes),
                collection,
                "Chart of User activities on object types in all time and specific field in time period",
                RateType.COUNT.getValue(),
                AnaliseType.PERIOD_TIME.getValue());
    }
    /*public ChartResponse chartOfCountsByCreatorAndClassNameAndEventTypeInPeriod(User user, ActivityTypes type, LocalDate start, LocalDate end){
        List<ChartResponse> sets = new ArrayList<>();
        Collection<LocalDate> dates = getHeaders(start,end);
        dates.forEach(date -> {
            List<Double> collection = new ArrayList<>();
            Collection<String> headers = getSpecificHeaders(type);
            switch (type){
                case TICKET -> Arrays.stream(TicketCUEventsFieldsNames.values()).toList().forEach(
                        x->{
                            collection.add(getSpecificTypeAndFieldCount(type, x, user, date));
                        });
                case USER -> Arrays.stream(UserCUEventsFieldsNames.values()).toList().forEach(
                        x->{
                            collection.add(getSpecificTypeAndFieldCount(type, x ,user, date));
                        });
                case COMMENT -> Arrays.stream(CommentCUEventsFieldsNames.values()).toList().forEach(
                        x->{
                            collection.add(getSpecificTypeAndFieldCount(type, x, user, date));
                        });
            }
            sets.add(new ChartResponse("Chart of User activities on object type ("+ x +")",
                    dates,
                    collection,
                    "Chart of User activities on object types in all time (detailed)",
                    RateType.COUNT.getValue(),
                    AnaliseType.ALL_TIME.getValue()));
        });
    }*/

    private Collection<String> getHeaders(){
        List<String> collection = new ArrayList<>();
        Arrays.stream(ActivityTypes.values()).toList().forEach(x-> collection.add(x.getValue()));
        return collection;
    }
    private List<String> getSpecificHeaders(ActivityTypes type){
        List<String> collection = new ArrayList<>();
        switch (type){
            case TICKET -> Arrays.stream(TicketCUEventsFieldsNames.values()).toList().forEach(
                    x-> collection.add(x.getValue()));
            case COMMENT -> Arrays.stream(CommentCUEventsFieldsNames.values()).toList().forEach(
                    x-> collection.add(x.getValue()));
            case USER -> Arrays.stream(UserCUEventsFieldsNames.values()).toList().forEach(
                    x-> collection.add(x.getValue()));
        }
        return collection;

    }
    private Collection<LocalDate> getHeaders(LocalDate startDate, LocalDate endDate){
        return startDate.datesUntil(endDate).toList();
    }
    private Collection<LocalTime> getHeaders(int minutes){
        List<LocalTime> localTimes = new ArrayList<>();
        for (int i = 0; i < 24 * 60; i += minutes) {
            LocalTime localTime = LocalTime.of(i / 60, i % 60);
            localTimes.add(localTime);
        }
        return localTimes;
    }
    private Collection<LocalDateTime> getHeaders(LocalDate date, int minutes){
        List<LocalDateTime> collection = new ArrayList<>();
        getHeaders(minutes).stream().toList().forEach(x -> collection.add(date.atTime(x)));
        return collection;
    }
    private double getSpecificTypeCount(ActivityTypes activityType, User user){
        return switch (activityType) {
            case ALL -> activityRepository.countAllByCreatorAndClassName(user, ActivityTypes.TICKET.getValue());
            case TICKET -> activityRepository.countAllByCreatorAndClassName(user, ActivityTypes.TICKET.getValue())
                    - activityRepository.countAllByCreatorAndClassName(user, ActivityTypes.COMMENT.getValue());
            case COMMENT -> activityRepository.countAllByCreatorAndClassName(user, ActivityTypes.COMMENT.getValue());
            case USER -> activityRepository.countAllByCreatorAndClassName(user, ActivityTypes.USER.getValue());
        };
    }
    private double getSpecificTypeCount(ActivityTypes activityType, User user, LocalDate date){
        return switch (activityType) {
            case ALL -> activityRepository.countAllByCreatorAndClassNameAndDate(user, ActivityTypes.TICKET.getValue(), date);
            case TICKET -> activityRepository.countAllByCreatorAndClassNameAndDate(user, ActivityTypes.TICKET.getValue(), date)
                    - activityRepository.countAllByCreatorAndClassNameAndDate(user, ActivityTypes.COMMENT.getValue(), date);
            case COMMENT -> activityRepository.countAllByCreatorAndClassNameAndDate(user, ActivityTypes.COMMENT.getValue(), date);
            case USER -> activityRepository.countAllByCreatorAndClassNameAndDate(user, ActivityTypes.USER.getValue(), date);
        };
    }
    private double getSpecificTypeCount(ActivityTypes activityType, User user, LocalTime start, LocalTime end){
        return switch (activityType) {
            case ALL -> activityRepository.countAllByCreatorAndClassNameAndTimeBetween(user, ActivityTypes.TICKET.getValue(), start, end);
            case TICKET -> activityRepository.countAllByCreatorAndClassNameAndTimeBetween(user, ActivityTypes.TICKET.getValue(), start, end)
                    - activityRepository.countAllByCreatorAndClassNameAndTimeBetween(user, ActivityTypes.COMMENT.getValue(), start, end);
            case COMMENT -> activityRepository.countAllByCreatorAndClassNameAndTimeBetween(user, ActivityTypes.COMMENT.getValue(), start, end);
            case USER -> activityRepository.countAllByCreatorAndClassNameAndTimeBetween(user, ActivityTypes.USER.getValue(), start, end);
        };
    }

    private double getSpecificTypeAndFieldCount(ActivityTypes activityTypes, Enum<?> fieldName, User user){
        return switch (activityTypes){
            case TICKET -> switch ((TicketCUEventsFieldsNames)fieldName){
                case PRIORITY -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.PRIORITY.getValue());
                case ASSIGNED -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.ASSIGNED.getValue());
                case STATE -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.STATE.getValue());
                case NOTE -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.NOTE.getValue());
                case ATTACHMENTS -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.ATTACHMENTS.getValue());
                case DESCRIPTION -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.DESCRIPTION.getValue());
                case TITLE -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.TITLE.getValue());
                case NEW_TICKET -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.NEW_TICKET.getValue());
                case ADD_COMMENT -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        TicketCUEventsFieldsNames.ADD_COMMENT.getValue());
            };
            case COMMENT -> switch ((CommentCUEventsFieldsNames)fieldName){
                case CONTENT -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.COMMENT.getValue(),
                        CommentCUEventsFieldsNames.CONTENT.getValue());
                case NEW_COMMENT -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.COMMENT.getValue(),
                        CommentCUEventsFieldsNames.NEW_COMMENT.getValue());
            };
            case USER -> switch ((UserCUEventsFieldsNames)fieldName){
                case NAME -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.NAME.getValue());
                case PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.PASSWORD.getValue());
                case SURNAME -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.SURNAME.getValue());
                case AVATAR -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.AVATAR.getValue());
                case RULES -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.RULES.getValue());
                case EMAIL -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.EMAIL.getValue());
                case NEW_USER -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.NEW_USER.getValue());
                case CHANGE_PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.CHANGE_PASSWORD.getValue());
                case SET_PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        UserCUEventsFieldsNames.SET_PASSWORD.getValue());
            };
            case ALL -> getSpecificTypeCount(ActivityTypes.ALL,user);
        };
    }

    private double getSpecificTypeAndFieldCount(ActivityTypes activityTypes, Enum<?> fieldName, User user, LocalDate date){
        return switch (activityTypes){
            case TICKET -> switch ((TicketCUEventsFieldsNames)fieldName){
                case PRIORITY -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.PRIORITY.getValue());
                case ASSIGNED -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.ASSIGNED.getValue());
                case STATE -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.STATE.getValue());
                case NOTE -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.NOTE.getValue());
                case ATTACHMENTS -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.ATTACHMENTS.getValue());
                case DESCRIPTION -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.DESCRIPTION.getValue());
                case TITLE -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.TITLE.getValue());
                case NEW_TICKET -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.NEW_TICKET.getValue());
                case ADD_COMMENT -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        date,
                        TicketCUEventsFieldsNames.ADD_COMMENT.getValue());
            };
            case COMMENT -> switch ((CommentCUEventsFieldsNames)fieldName){
                case CONTENT -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.COMMENT.getValue(),
                        date,
                        CommentCUEventsFieldsNames.CONTENT.getValue());
                case NEW_COMMENT -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.COMMENT.getValue(),
                        date,
                        CommentCUEventsFieldsNames.NEW_COMMENT.getValue());
            };
            case USER -> switch ((UserCUEventsFieldsNames)fieldName){
                case NAME -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.NAME.getValue());
                case PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.PASSWORD.getValue());
                case SURNAME -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.SURNAME.getValue());
                case AVATAR -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.AVATAR.getValue());
                case RULES -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.RULES.getValue());
                case EMAIL -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.EMAIL.getValue());
                case NEW_USER -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.NEW_USER.getValue());
                case CHANGE_PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.CHANGE_PASSWORD.getValue());
                case SET_PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndDateAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        date,
                        UserCUEventsFieldsNames.SET_PASSWORD.getValue());
            };
            case ALL -> getSpecificTypeCount(ActivityTypes.ALL,user, date);
        };
    }

    private double getSpecificTypeAndFieldCount(ActivityTypes activityTypes, Enum<?> fieldName, User user, LocalTime start, LocalTime end){
        return switch (activityTypes){
            case TICKET -> switch ((TicketCUEventsFieldsNames)fieldName){
                case PRIORITY -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.PRIORITY.getValue());
                case ASSIGNED -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.ASSIGNED.getValue());
                case STATE -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.STATE.getValue());
                case NOTE -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.NOTE.getValue());
                case ATTACHMENTS -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.ATTACHMENTS.getValue());
                case DESCRIPTION -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.DESCRIPTION.getValue());
                case TITLE -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.TITLE.getValue());
                case NEW_TICKET -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.NEW_TICKET.getValue());
                case ADD_COMMENT -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.TICKET.getValue(),
                        start,
                        end,
                        TicketCUEventsFieldsNames.ADD_COMMENT.getValue());
            };
            case COMMENT -> switch ((CommentCUEventsFieldsNames)fieldName){
                case CONTENT -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.COMMENT.getValue(),
                        start,
                        end,
                        CommentCUEventsFieldsNames.CONTENT.getValue());
                case NEW_COMMENT -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.COMMENT.getValue(),
                        start,
                        end,
                        CommentCUEventsFieldsNames.NEW_COMMENT.getValue());
            };
            case USER -> switch ((UserCUEventsFieldsNames)fieldName){
                case NAME -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.NAME.getValue());
                case PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.PASSWORD.getValue());
                case SURNAME -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.SURNAME.getValue());
                case AVATAR -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.AVATAR.getValue());
                case RULES -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.RULES.getValue());
                case EMAIL -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.EMAIL.getValue());
                case NEW_USER -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.NEW_USER.getValue());
                case CHANGE_PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.CHANGE_PASSWORD.getValue());
                case SET_PASSWORD -> activityRepository.countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(user,
                        ActivityTypes.USER.getValue(),
                        start,
                        end,
                        UserCUEventsFieldsNames.SET_PASSWORD.getValue());
            };
            case ALL -> getSpecificTypeCount(ActivityTypes.ALL,user, start, end);
        };
    }


}
