package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.PageInfo;
import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.User;
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
        double rate = activityService.getAllTimeRateByCreator(user);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity Rate")
                        .data(Map.of("rate", new RateResponse("User activity Rate", rate)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/rate/all/object/{objectUuid}")
    public ResponseEntity<Response> getAllTimeObjectActivityRate(@NonNull @Validated @PathVariable UUID objectUuid){

        double rate = activityService.getAllTimeRateOnObject(objectUuid);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time on object activity Rate")
                        .data(Map.of("rate", new RateResponse("Object activity Rate", rate)))
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
        double rate = activityService.getRateOnAllActivitiesByClassNameAndCreator(className,user);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity Rate on Object")
                        .data(Map.of("rate", new RateResponse("User activity Rate on Object", rate)))
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
        var headers = activityService.getHeaders(startDate,endDate);
        var collection = activityService.getCollectionOfRatesByCreatorInPeriod(user,startDate,endDate);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart in period")
                        .data(Map.of("chart", new ChartResponse("User activity in period", headers, collection)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/all/user/rates/{userUuid}")
    public ResponseEntity<Response> getChartOfRatesAll(
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        var headers = activityService.getHeaders();
        var collection = activityService.getCollectionOfRatesByClassNameAndCreator(user);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity chart rates by class")
                        .data(Map.of("chart", new ChartResponse("User activity by class", headers, collection)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/all/user/{userUuid}")
    public ResponseEntity<Response> getChartOfCountsAll(
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        var headers = activityService.getHeaders();
        var collection = activityService.getCollectionOfCountsByClassNameAndCreator(user);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All time user activity chart by class")
                        .data(Map.of("chart", new ChartResponse("User activity by class", headers, collection)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/{startDate}/{endDate}/user/{userUuid}/{className}")
    public ResponseEntity<Response> getChartOfCountsInPeriod(
            @NonNull @Validated @PathVariable LocalDate endDate,
            @NonNull @Validated @PathVariable LocalDate startDate,
            @NonNull @Validated @PathVariable String className,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        var headers = activityService.getHeaders(startDate,endDate);
        var collection = activityService.getCollectionOfRatesByClassNameAndCreatorInPeriod(className,user,startDate,endDate);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in period")
                        .data(Map.of("chart", new ChartResponse("User activity by class in period", headers, collection)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/chart/{date}/user/{userUuid}/{minutes}")
    public ResponseEntity<Response> getChartOfCountsInTime(
            @NonNull @Validated @PathVariable LocalDate date,
            @NonNull @Validated @PathVariable Integer minutes,
            @NonNull @Validated @PathVariable UUID userUuid){
        User user = modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(userUuid));
        var headers = activityService.getHeaders(date,minutes);
        var collection = activityService.getCollectionOfCountsByCreatorInTimePeriod(user,minutes);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User activity chart by class in time period")
                        .data(Map.of("chart", new ChartResponse("User activity by class in time period", headers, collection)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
