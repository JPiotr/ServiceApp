package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Project.DTOs.FunctionalityDTO;
import com.oblitus.serviceApp.Modules.Project.Functionality;
import com.oblitus.serviceApp.Modules.Service.*;
import com.oblitus.serviceApp.Modules.Service.DAOs.ClientDAO;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
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

@RestController
@RequestMapping("/serviceModule")
@RequiredArgsConstructor
public class ServiceController {
    private final ClientDAO clientDAO;
    private final CommentService commentService;
    private final TicketService ticketService;

    @GetMapping("/clients/client//{id}")
    public ResponseEntity<Response> getClient(@PathVariable @Validated UUID id){
        Optional<ClientDTO> opt = clientDAO.get(id);
        return opt.<ResponseEntity<Response>>map(userDTO -> ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Client with ID = " + id + ".")
                        .data(Map.of("client", userDTO))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        )).orElseGet(() -> ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Client " + id + " not found.")
                        .data(Map.of("client", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
                        .build()
        ));
    }

    @GetMapping("/clients")
    public ResponseEntity<Response> getClients(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing clients.")
                        .data(Map.of("clients",clientDAO.getAll()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/clients")
    public ResponseEntity<Response> putClient(@RequestBody @Validated ClientDTO clientDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Client putted to Database.")
                        .data(Map.of("client",clientDAO.save(clientDTO)))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/clients")
    public ResponseEntity<Response> updateClient(@RequestBody @Validated ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Client updated.")
                            .data(Map.of("client", clientDAO.update(clientDTO)))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (AccountLockedException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .data(Map.of("client",null))
                            .statusCode(HttpStatus.LOCKED.value())
                            .status(HttpStatus.LOCKED)
                            .build()
            );
        }
    }

    @DeleteMapping("/client")
    public ResponseEntity<Response> deleteClient(@RequestBody @Validated ClientDTO clientDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Client")
                        .data(Map.of("result", clientDAO.delete(clientDTO)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/comments/comment/{id}")
    public ResponseEntity<Response> getFunc(@PathVariable @Validated UUID id) {
        Optional<Comment> opt = commentService.getComment(id);
        return opt.<ResponseEntity<Response>>map(task -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Comment with ID = " + id + ".")
                        .data(Map.of("comment", task))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        )).orElseGet(() -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Comment with ID = " + id + ".")
                        .data(Map.of("comment", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
                        .build()
        ));
    }

    @GetMapping("/comments")
    public ResponseEntity<Response> getComment(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing comments.")
                        .data(Map.of("comments",commentService.getAllComments()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/comments")
    public ResponseEntity<Response> putFunctionality(@RequestBody @Validated CommentDTO commentDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Comment putted to Database.")
                        .data(Map.of("comment",commentService.addComment(commentDTO.content())))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/comments")
    public ResponseEntity<Response> updateFunctionality(@RequestBody @Validated CommentDTO commentDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Comment updated.")
                            .data(Map.of("comment", commentService.updateComment(commentDTO.id(),commentDTO.content())))
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
                            .data(Map.of("comment",null))
                            .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                            .status(HttpStatus.EXPECTATION_FAILED)
                            .build()
            );
        }
    }

    @DeleteMapping("/comments")
    public ResponseEntity<Response> deleteUser(@RequestBody @Validated CommentDTO commentDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Comment")
                        .data(Map.of("result", commentService.deleteComment(commentDTO.id())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/tickets/ticket//{id}")
    public ResponseEntity<Response> getTicket(@PathVariable @Validated UUID id) {
        Optional<Ticket> opt = ticketService.getTicket(id);
        return opt.<ResponseEntity<Response>>map(task -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Ticket with ID = " + id + ".")
                        .data(Map.of("ticket", task))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        )).orElseGet(() -> ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .message("Ticket with ID = " + id + ".")
                        .data(Map.of("ticket", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
                        .build()
        ));
    }

    @GetMapping("/tickets")
    public ResponseEntity<Response> getTicket(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing tickets.")
                        .data(Map.of("tickets",commentService.getAllComments()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/tickets")
    public ResponseEntity<Response> putTicket(@RequestBody @Validated TicketDTO ticketDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Ticket putted to Database.")
                        .data(Map.of("ticket",ticketService.addTicket(ticketDTO.title(),ticketDTO.description(),null)))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/tickets")
    public ResponseEntity<Response> updateTicket(@RequestBody @Validated TicketDTO ticketDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Ticket updated.")
                            .data(Map.of("ticket", ticketService.updateClient(ticketDTO.id(),ticketDTO.description(),ticketDTO.description())))
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
                            .data(Map.of("ticket",null))
                            .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                            .status(HttpStatus.EXPECTATION_FAILED)
                            .build()
            );
        }
    }

    @DeleteMapping("/tickets")
    public ResponseEntity<Response> deleteTicket(@RequestBody @Validated TicketDTO ticketDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Ticket")
                        .data(Map.of("result", ticketService.deleteTicket(ticketDTO.id())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
