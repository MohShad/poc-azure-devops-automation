package com.azure.devops.dto.branchPolicy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isEnabled",
        "isBlocking",
        "type",
        "settings"
})
public class RequestBranchPolicyDTO {

    @JsonProperty("isEnabled")
    private Boolean isEnabled;
    @JsonProperty("isBlocking")
    private Boolean isBlocking;
    @JsonProperty("type")
    private TypeDTO typeDTO;
    @JsonProperty("settings")
    private SettingsDTO settingsDTO;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}