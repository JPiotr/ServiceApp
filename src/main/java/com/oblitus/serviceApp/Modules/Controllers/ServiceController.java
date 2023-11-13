package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.PageInfo;
import com.oblitus.serviceApp.Common.PageSortUtil;
import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.MappersWrapper;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.*;
import com.oblitus.serviceApp.Modules.Service.Responses.TicketResponse;
import com.oblitus.serviceApp.Modules.Service.Ticket;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serviceModule")
@RequiredArgsConstructor
public class ServiceController {
    private final ModulesWrapper modulesWrapper;
    private final MappersWrapper mappersWrapper;

    @GetMapping("/clients/{id}")
    public ResponseEntity<Response> getClient(@PathVariable @Validated UUID id){
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Client with ID = " + id + ".")
                            .data(Map.of("client", mappersWrapper.clientMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getClientService()
                                            .get(new ClientDTO(id)))))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Client " + id + " not found.")
                            .data(Map.of("client", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .devMessage(e.getMessage())
                            .build()
            );
        }
    }
    @GetMapping("/clients")
    public ResponseEntity<Response> getClients(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing clients.")
                        .data(Map.of("clients",modulesWrapper.serviceModule.getServiceDAO().getClientService()
                                .getAll().stream().map(mappersWrapper.clientMapper).toList())
                        )
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @PostMapping("/clients")
    public ResponseEntity<Response> addClient(@RequestBody @Validated ClientDTO clientDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Client added to Database.")
                        .data(Map.of("client", mappersWrapper.clientMapper.apply(
                                modulesWrapper.serviceModule.getServiceDAO().getClientService().add(clientDTO))
                                )
                        )
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }
    @PutMapping("/client")
    public ResponseEntity<Response> updateOrAddClient(@RequestBody @Validated ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Client updated.")
                            .data(Map.of("client", mappersWrapper.clientMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getClientService().update(clientDTO))
                                    )
                            )
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .status(HttpStatus.ACCEPTED)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("New Client added to Database.")
                            .data(Map.of("client",mappersWrapper.clientMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getClientService().add(clientDTO))
                                    )
                            )
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .devMessage(e.getMessage())
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
                        .data(Map.of("result", modulesWrapper.serviceModule.getServiceDAO().getClientService().delete(clientDTO)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }


    @GetMapping("/comments/{id}")
    public ResponseEntity<Response> getComment(@PathVariable @Validated UUID id) {
        try{
            return ResponseEntity.ok(
                    Response.builder().timestamp(LocalDateTime.now())
                            .message("Comment with ID = " + id + ".")
                            .data(Map.of("comment", mappersWrapper.commentMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getCommentService()
                                            .get(new CommentDTO(id)))
                                    )
                            )
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder().timestamp(LocalDateTime.now())
                            .message("Comment with ID = " + id + ".")
                            .data(Map.of("comment", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .devMessage(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<Response> getComments(@Nullable @RequestParam @Validated String sortField,
                                                @Nullable @RequestParam @Validated Boolean desc,
                                                @Nullable @RequestParam @Validated Integer page,
                                                @Nullable @RequestParam @Validated Integer size){
        PageSortUtil.preparePaginationAndSorting(sortField,desc,page,size);
        if(PageSortUtil.pageable.isPaged()){
            var commentPage = modulesWrapper.serviceModule.getServiceDAO().getCommentService()
                    .getAll(PageSortUtil.pageable);
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("All existing comments.")
                            .data(Map.of("comments",commentPage.stream().map(mappersWrapper.commentMapper).toList()
                                    )
                            )
                            .meta(Map.of("pageInfo",new PageInfo(commentPage)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing comments.")
                        .data(Map.of("comments",modulesWrapper.serviceModule.getServiceDAO().getCommentService()
                                .getAll(PageSortUtil.sort).stream().map(mappersWrapper.commentMapper).toList()
                                )
                        )
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("/comments")
    public ResponseEntity<Response> addComment(@RequestBody @Validated CommentDTO commentDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Comment added to Database.")
                        .data(Map.of("comment",mappersWrapper.commentMapper.apply(
                                modulesWrapper.serviceModule.getServiceDAO()
                                .getCommentService().add(commentDTO))
                                )
                        )
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PutMapping("/comment")
    public ResponseEntity<Response> updateOrAddComment(@RequestBody @Validated CommentDTO commentDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Comment updated.")
                            .data(Map.of("comment", mappersWrapper.commentMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getCommentService().update(commentDTO))
                                    )
                            )
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .status(HttpStatus.ACCEPTED)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("New Comment added to Database.")
                            .data(Map.of("comment",mappersWrapper.commentMapper.apply(modulesWrapper.serviceModule.getServiceDAO()
                                    .getCommentService().add(commentDTO))
                                    )
                            )
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .devMessage(e.getMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Response> deleteComment(@RequestBody @Validated CommentDTO commentDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Comment")
                        .data(Map.of("result", modulesWrapper.serviceModule.getServiceDAO().getCommentService()
                                .delete(commentDTO))
                        )
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/comments/")
    public ResponseEntity<Response> getTicketsComments(@Nonnull @RequestParam @Validated UUID ticketId,
                                                       @Nullable @RequestParam @Validated String sortField,
                                                       @Nullable @RequestParam @Validated Boolean desc,
                                                       @Nullable @RequestParam @Validated Integer page,
                                                       @Nullable @RequestParam @Validated Integer size){
        PageSortUtil.preparePaginationAndSorting(sortField,desc,size,page);
        if(PageSortUtil.pageable.isPaged()) {
            var commentPage = modulesWrapper.serviceModule.getServiceDAO().getCommentService()
                    .getAll(PageSortUtil.pageable);
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("All ticket's comments.")
                            .data(Map.of("comments",commentPage.stream().filter(
                                    commentResponse -> ticketId.equals(commentResponse.getTicket().getUuid())
                            ).map(mappersWrapper.commentMapper).collect(Collectors.toList())))
                            .meta(Map.of("pageInfo",new PageInfo(commentPage)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All ticket's comments.")
                        .data(Map.of("comments",modulesWrapper.serviceModule.getServiceDAO().getCommentService()
                                .getAll(PageSortUtil.sort).stream().filter(
                                commentResponse -> ticketId.equals(commentResponse.getTicket().getUuid())
                        ).map(mappersWrapper.commentMapper).collect(Collectors.toList())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }


    @GetMapping("/ticket/{id}")
    public ResponseEntity<Response> getTicket(@PathVariable @Validated UUID id) {
        try{
            return ResponseEntity.ok(
                    Response.builder().timestamp(LocalDateTime.now())
                            .message("Ticket with ID = " + id + ".")
                            .data(Map.of("ticket", mappersWrapper.ticketMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getTicketService()
                                            .get(new TicketDTO(id))
                                            )
                                    )
                            )
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Ticket " + id + " not found.")
                            .data(Map.of("ticket", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .devMessage(e.getMessage())
                            .build()
            );
        }
    }
    @GetMapping("/tickets")
    public ResponseEntity<Response> getTicketsPage(@Nullable @RequestParam @Validated String sortField,
                                                   @Nullable @RequestParam @Validated Boolean desc,
                                                   @Nullable @RequestParam @Validated Integer page,
                                                   @Nullable @RequestParam @Validated Integer size){
        PageSortUtil.preparePaginationAndSorting(sortField,desc,size,page);
        if(PageSortUtil.pageable.isPaged()){
            var ticketsPage = modulesWrapper.serviceModule.getServiceDAO().getTicketService()
                    .getAll(PageSortUtil.pageable);
            var ticketList = ticketsPage.stream().map(mappersWrapper.ticketMapper).toList();
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("All existing tickets.")
                            .data(Map.of("tickets", ticketList))
                            .meta(Map.of("pageInfo", new PageInfo(ticketsPage)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing tickets.")
                        .data(Map.of("tickets",
                                modulesWrapper.serviceModule.getServiceDAO().getTicketService()
                                        .getAll(PageSortUtil.sort).stream().map(mappersWrapper.ticketMapper).toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("/tickets")
    public ResponseEntity<Response> addTicket(@RequestBody @Validated TicketDTO ticketDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New Ticket added to Database.")
                        .data(Map.of("ticket",mappersWrapper.ticketMapper.apply(modulesWrapper.serviceModule.getServiceDAO()
                                .getTicketService().add(ticketDTO))))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @GetMapping("/tickets/{userId}")
    public ResponseEntity<Response> getUserTickets(@PathVariable @Validated UUID userId,
                                                   @Nullable @RequestParam @Validated String sortField,
                                                   @Nullable @RequestParam @Validated Boolean desc,
                                                   @Nullable @RequestParam @Validated Integer page,
                                                   @Nullable @RequestParam @Validated Integer size){
        PageSortUtil.preparePaginationAndSorting(sortField,desc,size,page);
        //todo: filtrowanie -> paginacja
        //  System user do wyjebania z response
        //  Wyjebać modules z rules Response -> stworzyć RuleResponse
        //  poprawic zortowanie po id
        //
        if(PageSortUtil.pageable.isPaged()){
            var ticketsPage = modulesWrapper.serviceModule.getServiceDAO().getTicketService()
                    .getAll(PageSortUtil.pageable);
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("All user tickets.")
                            .data(Map.of("tickets", ticketsPage.stream().filter(
                                    ticket -> userId.equals(ticket.getAssigned().getUuid())
                                            || userId.equals(ticket.getCreator().getUuid())
                            ).map(mappersWrapper.ticketMapper).toList()))
                            .meta(Map.of("pageInfo", new PageInfo(ticketsPage)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All user tickets.")
                        .data(Map.of("tickets",modulesWrapper.serviceModule.getServiceDAO().getTicketService()
                                .getAll(PageSortUtil.sort).stream().filter(
                                ticket -> userId.equals(ticket.getAssigned().getUuid())
                                        || userId.equals(ticket.getCreator().getUuid())
                        ).map(mappersWrapper.ticketMapper)
                                .toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/ticket")
    public ResponseEntity<Response> updateOrAddTicket(@RequestBody @Validated TicketDTO ticketDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Ticket updated.")
                            .data(Map.of("ticket",mappersWrapper.ticketMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO().getTicketService().update(ticketDTO))
                                    )
                            )
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("New Ticket added to Database.")
                            .data(Map.of("ticket",mappersWrapper.ticketMapper.apply(modulesWrapper.serviceModule.getServiceDAO()
                                    .getTicketService().add(ticketDTO))
                                    )
                            )
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
    }

    @DeleteMapping("/ticket")
    public ResponseEntity<Response> deleteTicket(@RequestBody @Validated TicketDTO ticketDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Try to drop Ticket")
                        .data(Map.of("result", modulesWrapper.serviceModule.getServiceDAO().getTicketService()
                                .delete(ticketDTO)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<Response> getActivity(@PathVariable @Validated UUID id) {
        try{
            return ResponseEntity.ok(
                    Response.builder().timestamp(LocalDateTime.now())
                            .message("Activity with ID = " + id + ".")
                            .data(Map.of("activity", mappersWrapper.activityMapper.apply(
                                    modulesWrapper.serviceModule.getServiceDAO()
                                    .getActivityService().get(new ActivityDTO(id)))
                                    )
                            )
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Activity " + id + " not found.")
                            .data(Map.of("activity", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }
    }

    @GetMapping("/activities")
    public ResponseEntity<Response> getActivities(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing activities.")
                        .data(Map.of("activities",modulesWrapper.serviceModule.getServiceDAO()
                                .getActivityService().getAll().stream().map(mappersWrapper.activityMapper)
                                .toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/activities/{objectId}")
    public ResponseEntity<Response> getObjectActivities(@PathVariable @Validated UUID objectId){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All object activity.")
                        .data(Map.of("activities",modulesWrapper.serviceModule.getServiceDAO()
                                .getActivityService().getAll().stream().filter(
                                activity -> objectId.equals(activity.getObjectActivity())
                        ).map(mappersWrapper.activityMapper).collect(Collectors.toList())))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

}
