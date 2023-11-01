package com.oblitus.serviceApp.Modules.Service.Responses;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;

import java.util.Collection;
import java.util.UUID;

public class TicketResponseBuilder extends BaseBuilder<TicketResponse> {
    @Override
    public void setEntity() {
        entity = new TicketResponse();
    }

    public TicketResponseBuilder setTitle(String title) {
        entity.setTitle(title);
        return this;
    }

    public TicketResponseBuilder setDescription(String description) {
        entity.setDescription(description);
        return this;
    }

    public TicketResponseBuilder setClient(UUID client) {
        entity.setClient(client);
        return this;
    }

    public TicketResponseBuilder setAssigned(ProfileResponse assigned) {
        entity.setAssigned(assigned);
        return this;
    }

    public TicketResponseBuilder setState(TicketState state) {
        entity.setState(state);
        return this;
    }

    public TicketResponseBuilder setPriority(TicketPriority priority) {
        entity.setPriority(priority);
        return this;
    }

    public TicketResponseBuilder setCreator(ProfileResponse creator) {
        entity.setCreator(creator);
        return this;
    }

    public TicketResponseBuilder setNote(String note) {
        entity.setNote(note);
        return this;
    }

    public TicketResponseBuilder setFiles(Collection<FileResponse> files) {
        entity.setFiles(files);
        return this;
    }
}
