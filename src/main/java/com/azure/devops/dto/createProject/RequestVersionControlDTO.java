package com.azure.devops.dto.createProject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sourceControlType"
})
public class RequestVersionControlDTO {

    @NotNull(message = "Enter the source control type.")
    private String sourceControlType;

}