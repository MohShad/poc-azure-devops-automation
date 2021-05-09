package com.azure.devops.dto.createPipeline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePipelineDTO {

    private ConfigurationPipelineDTO configuration;
    private String folder;
    private String name;

}
