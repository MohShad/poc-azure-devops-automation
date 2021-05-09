package com.azure.devops.dto.createPipeline.responseProject;

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
        "id",
        "name",
        "url",
        "project",
        "defaultBranch",
        "size",
        "remoteUrl",
        "sshUrl",
        "webUrl"
})
public class ResponseValueDTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("project")
    private ResponseProjectDTO project;
    @JsonProperty("defaultBranch")
    private String defaultBranch;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("remoteUrl")
    private String remoteUrl;
    @JsonProperty("sshUrl")
    private String sshUrl;
    @JsonProperty("webUrl")
    private String webUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
