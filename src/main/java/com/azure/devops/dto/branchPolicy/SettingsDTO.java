package com.azure.devops.dto.branchPolicy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "minimumApproverCount",
        "creatorVoteCounts",
        "scope"
})
public class SettingsDTO {

    @JsonProperty("minimumApproverCount")
    private Integer minimumApproverCount;
    @JsonProperty("creatorVoteCounts")
    private Boolean creatorVoteCounts;
    @JsonProperty("scope")
    private List<ScopeDTO> scopeDTO = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}