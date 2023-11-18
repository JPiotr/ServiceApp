package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.PageInfo;
import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Analytics.ActivityTypes;
import com.oblitus.serviceApp.Modules.Analytics.AnaliseActivityService;
import com.oblitus.serviceApp.Modules.Analytics.ChartResponse;
import com.oblitus.serviceApp.Modules.Analytics.RateResponse;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/analise")
@RequiredArgsConstructor
public class AnaliseController {
    private final AnaliseActivityService activityService;
    private final ModulesWrapper modulesWrapper;

    @GetMapping("/rate/all/user/{userUuid}")
    public ResponseEntity<Response> getAllTimeUserActivityRate(@NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity Rate")
                        .data(Map.of("rate", activityService.rateAllUserActivity(user)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/rate/all/object/{objectUuid}")
    public ResponseEntity<Response> getAllTimeObjectActivityRate(@NonNull @Validated @PathVariable UUID objectUuid){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time on object activity Rate")
                        .data(Map.of("rate", activityService.rateObjectActivity(objectUuid)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/rate/all/user/{userUuid}/{className}")
    public ResponseEntity<Response> getAllTimeUserActivityRateOnClassName(
            @NonNull @Validated @PathVariable UUID userUuid,
            @NonNull @Validated @PathVariable String className
        ){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity Rate on Object")
                        .data(Map.of("rate", activityService.rateUserActivitiesOnObjectType(user, ActivityTypes.valueOf(className))))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/{startDate}/{endDate}/user/{userUuid}")
    public ResponseEntity<Response> getChartOfRatesInPeriod(
            @NonNull @Validated @PathVariable LocalDate endDate,
            @NonNull @Validated @PathVariable LocalDate startDate,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart in period")
                        .data(Map.of("multiChart", activityService.multiSetChartOfRatesByCreatorInPeriod(user,startDate,endDate)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/all/user/rates/{userUuid}")
    public ResponseEntity<Response> getChartOfRatesAll(
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity chart rates by class")
                        .data(Map.of("chart", activityService.chartOfRatesByClassNameAndCreator(user)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/all/user/{userUuid}")
    public ResponseEntity<Response> getChartOfCountsAll(
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity chart by class")
                        .data(Map.of("chart", activityService.chartOfCountsByClassNameAndCreator(user)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/{startDate}/{endDate}/user/{userUuid}/{className}")
    public ResponseEntity<Response> getChartOfCountsClassNameInPeriod(
            @NonNull @Validated @PathVariable LocalDate endDate,
            @NonNull @Validated @PathVariable LocalDate startDate,
            @NonNull @Validated @PathVariable String className,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in period")
                        .data(Map.of("chart", activityService.chartOfRatesByClassNameAndCreatorInPeriod(ActivityTypes.valueOf(className),user,startDate,endDate)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/{startDate}/{endDate}/user/{userUuid}")
    public ResponseEntity<Response> getChartOfCountsClassNameInPeriod(
            @NonNull @Validated @PathVariable LocalDate endDate,
            @NonNull @Validated @PathVariable LocalDate startDate,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in period")
                        .data(Map.of("chart", activityService.chartOfCountsByCreatorInPeriod(user,startDate,endDate)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/chart/{startDate}/{endDate}/user/{userUuid}/{className}/event/{event}")
    public ResponseEntity<Response> getChartOfCountsClassNameInPeriod(
            @NonNull @Validated @PathVariable LocalDate endDate,
            @NonNull @Validated @PathVariable LocalDate startDate,
            @NonNull @Validated @PathVariable String event,
            @NonNull @Validated @PathVariable String className,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return switch (ActivityTypes.valueOf(className)){
            case COMMENT -> ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User activity chart by class in period")
                            .data(Map.of("chart", activityService
                                    .chartOfCountsByCreatorAndClassNameAndEventInPeriod(user,
                                            ActivityTypes.valueOf(className),
                                            AnaliseActivityService.CommentCUEventsFieldsNames.valueOf(event),startDate,endDate)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
            case TICKET -> ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User activity chart by class in period")
                            .data(Map.of("chart", activityService
                                    .chartOfCountsByCreatorAndClassNameAndEventInPeriod(user,
                                            ActivityTypes.valueOf(className),
                                            AnaliseActivityService.TicketCUEventsFieldsNames.valueOf(event),startDate,endDate)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
            case USER -> ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User activity chart by class in period")
                            .data(Map.of("chart", activityService
                                    .chartOfCountsByCreatorAndClassNameAndEventInPeriod(user,
                                            ActivityTypes.valueOf(className),
                                            AnaliseActivityService.UserCUEventsFieldsNames.valueOf(event),startDate,endDate)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
            case ALL -> null;
        };
    }

    @GetMapping("/chart/{date}/user/{userUuid}/{className}/event/{event}/{minutes}")
    public ResponseEntity<Response> getChartOfCountsClassNameInPeriod(
            @NonNull @Validated @PathVariable Integer minutes,
            @NonNull @Validated @PathVariable LocalDate date,
            @NonNull @Validated @PathVariable String event,
            @NonNull @Validated @PathVariable String className,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return switch (ActivityTypes.valueOf(className)){
            case COMMENT -> ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User activity chart by class in period")
                            .data(Map.of("chart", activityService
                                    .chartOfCountsByCreatorAndClassNameAndEventInTimePeriod(user,
                                            ActivityTypes.valueOf(className),
                                            AnaliseActivityService.CommentCUEventsFieldsNames.valueOf(event),minutes)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
            case TICKET -> ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User activity chart by class in period")
                            .data(Map.of("chart", activityService
                                    .chartOfCountsByCreatorAndClassNameAndEventInTimePeriod(user,
                                            ActivityTypes.valueOf(className),
                                            AnaliseActivityService.TicketCUEventsFieldsNames.valueOf(event),minutes)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
            case USER -> ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User activity chart by class in period")
                            .data(Map.of("chart", activityService
                                    .chartOfCountsByCreatorAndClassNameAndEventInTimePeriod(user,
                                            ActivityTypes.valueOf(className),
                                            AnaliseActivityService.UserCUEventsFieldsNames.valueOf(event),minutes)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
            case ALL -> null;
        };
    }

    @GetMapping("/chart/{date}/user/{userUuid}/{minutes}")
    public ResponseEntity<Response> getChartOfCountsInTime(
            @NonNull @Validated @PathVariable LocalDate date,
            @NonNull @Validated @PathVariable Integer minutes,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in time period")
                        .data(Map.of("chart", activityService.chartOfCountsByCreatorInTimePeriod(user,minutes)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/chart/{date}/user/{userUuid}/{className}/{minutes}")
    public ResponseEntity<Response> getChartOfCountsInTime(
            @NonNull @Validated @PathVariable LocalDate date,
            @NonNull @Validated @PathVariable Integer minutes,
            @NonNull @Validated @PathVariable String className,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in time period")
                        .data(Map.of("chart", activityService.chartOfCountsByCreatorAndClassNameInTimePeriod(ActivityTypes.valueOf(className),user,minutes)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/chart/{date}/user/{userUuid}/class/{minutes}")
    public ResponseEntity<Response> getChartOfCountsInTimeMulti(
            @NonNull @Validated @PathVariable LocalDate date,
            @NonNull @Validated @PathVariable Integer minutes,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in time period")
                        .data(Map.of("chart", activityService.multiSetChartOfRatesByClassNameAndCreatorInTimePeriod(user,minutes)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/chart/user/{userUuid}/detailed/{className}")
    public ResponseEntity<Response> getChartOfCountsDetailed(
            @NonNull @Validated @PathVariable String className,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in time period")
                        .data(Map.of("chart", activityService.chartOfCountsByCreatorAndClassNameDetailed(user,ActivityTypes.valueOf(className))))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
