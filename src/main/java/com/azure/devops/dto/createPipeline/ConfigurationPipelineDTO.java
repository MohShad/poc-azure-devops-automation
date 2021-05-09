package com.azure.devops.dto.createPipeline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationPipelineDTO {

    private RepositoryPipelineDTO repository;
    private String path;
    private String type;

}
