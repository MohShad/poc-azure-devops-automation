package com.azure.devops.dto.createProject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "versioncontrol",
        "processTemplate"
})
public class RequestCapabilitiesDTO {

    @JsonProperty("versioncontrol")
    private RequestVersionControlDTO versioncontrol;
    @JsonProperty("processTemplate")
    private RequestProcessTemplateDTO processTemplate;

}