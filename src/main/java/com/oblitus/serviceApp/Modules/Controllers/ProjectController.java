package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Project.*;
import com.oblitus.serviceApp.Modules.Project.DTOs.FunctionalityDTO;
import com.oblitus.serviceApp.Modules.Project.DTOs.ProjectDTO;
import com.oblitus.serviceApp.Modules.Project.DTOs.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/projectModule")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final FunctionalityService functionalityService;

    @GetMapping("/projects/project/{id}")
    public ResponseEntity<Response> getProject(@PathVariable @Validated UUID id) {
        Optional<Project> opt = projectService.getProject(id);
        return opt.<ResponseEntity<Response>>map(project -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Project with ID = " + id + ".")
                        .data(Map.of("project", project))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        )).orElseGet(() -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Project with ID = " + id + ".")
                        .data(Map.of("project", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
                        .build()
        ));
    }

    @GetMapping("/projects")
    public ResponseEntity<Response> getProjects(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing projects.")
                        .data(Map.of("projects",projectService.getAllProjects()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/projects")
    public ResponseEntity<Response> putProject(@RequestBody @Validated ProjectDTO projectDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Project putted to Database.")
                        .data(Map.of("project",projectService.addProject(projectDTO.name(),projectDTO.description(),projectDTO.owner().id())))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/projects")
    public ResponseEntity<Response> updateProject(@RequestBody @Validated ProjectDTO projectDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Project updated.")
                            .data(Map.of("project", projectService.updateProject(projectDTO.id(),projectDTO.name(),projectDTO.description())))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (Exception e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .data(Map.of("project",null))
                            .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                            .status(HttpStatus.EXPECTATION_FAILED)
                            .build()
            );
        }
    }

    @DeleteMapping("/projects")
    public ResponseEntity<Response> deleteProject(@RequestBody @Validated ProjectDTO projectDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Project")
                        .data(Map.of("result", projectService.deleteProject(projectDTO.id())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/tasks/task/{id}")
    public ResponseEntity<Response> getTask(@PathVariable @Validated UUID id) {
        Optional<Task> opt = taskService.getTask(id);
        return opt.<ResponseEntity<Response>>map(task -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Task with ID = " + id + ".")
                        .data(Map.of("task", task))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        )).orElseGet(() -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Task with ID = " + id + ".")
                        .data(Map.of("task", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
                        .build()
        ));
    }

    @GetMapping("/tasks")
    public ResponseEntity<Response> getTasks(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing tasks.")
                        .data(Map.of("tasks",taskService.getAllTasks()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/tasks")
    public ResponseEntity<Response> putUser(@RequestBody @Validated TaskDTO taskDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Task putted to Database.")
                        .data(Map.of("task",taskService.addTask(taskDTO.title(),taskDTO.description(),taskDTO.user().id())))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/tasks")
    public ResponseEntity<Response> updateUser(@RequestBody @Validated TaskDTO taskDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Task updated.")
                            .data(Map.of("task", taskService.updateTask(taskDTO.id(),taskDTO.title(),taskDTO.description())))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (Exception e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .data(Map.of("task",null))
                            .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                            .status(HttpStatus.EXPECTATION_FAILED)
                            .build()
            );
        }
    }

    @DeleteMapping("/tasks")
    public ResponseEntity<Response> deleteUser(@RequestBody @Validated TaskDTO taskDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Task")
                        .data(Map.of("result", taskService.deleteTask(taskDTO.id())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/functionalities/func/{id}")
    public ResponseEntity<Response> getFunc(@PathVariable @Validated UUID id) {
        Optional<Functionality> opt = functionalityService.getFunctionality(id);
        return opt.<ResponseEntity<Response>>map(task -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Functionality with ID = " + id + ".")
                        .data(Map.of("functionality", task))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        )).orElseGet(() -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Functionality with ID = " + id + ".")
                        .data(Map.of("functionality", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
                        .build()
        ));
    }

    @GetMapping("/functionalities")
    public ResponseEntity<Response> getFunctionality(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing functionalities.")
                        .data(Map.of("functionalities",functionalityService.getAllFunctionalities()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/functionalities")
    public ResponseEntity<Response> putFunctionality(@RequestBody @Validated FunctionalityDTO functionalityDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Functionality putted to Database.")
                        .data(Map.of("functionality",functionalityService.addFunctionality(functionalityDTO.title())))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/functionalities")
    public ResponseEntity<Response> updateFunctionality(@RequestBody @Validated FunctionalityDTO functionalityDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Functionality updated.")
                            .data(Map.of("functionality", functionalityService
                                    .updateFunctionality(functionalityDTO.id(),functionalityDTO.title(),functionalityDTO.priority(),functionalityDTO.estimate())))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (Exception e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .data(Map.of("functionality",null))
                            .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                            .status(HttpStatus.EXPECTATION_FAILED)
                            .build()
            );
        }
    }

    @DeleteMapping("/functionalities")
    public ResponseEntity<Response> deleteUser(@RequestBody @Validated FunctionalityDTO functionalityDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Functionality")
                        .data(Map.of("result", functionalityService.deleteFunctionality(functionalityDTO.id())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
