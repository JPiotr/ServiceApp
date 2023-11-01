package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Modules.Admin.DTOs.ProfileResponse;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketResponse extends BaseResponse {

    private String title;
    private String description;
    private UUID client;
    private ProfileResponse assigned;
    private TicketState state;
    private TicketPriority priority;
    private ProfileResponse creator;
    private String note;
    private Collection<FileResponse> files;
}
