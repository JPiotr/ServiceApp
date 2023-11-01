package com.oblitus.serviceApp.Modules.Service.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse extends BaseResponse {
    private String content;
    private UUID subject;
    private ProfileResponse creator;
}
