package com.azure.devops.dto.createPipeline.responseProject;

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
        "value",
        "count"
})
public class ResponseDTO {

    @JsonProperty("value")
    private List<ResponseValueDTO> value = null;
    @JsonProperty("count")
    private Integer count;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
