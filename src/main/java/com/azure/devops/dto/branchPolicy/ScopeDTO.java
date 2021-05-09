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
        "repositoryId",
        "refName",
        "matchKind"
})
public class ScopeDTO {

    @JsonProperty("repositoryId")
    private Object repositoryId;
    @JsonProperty("refName")
    private String refName;
    @JsonProperty("matchKind")
    private String matchKind;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}