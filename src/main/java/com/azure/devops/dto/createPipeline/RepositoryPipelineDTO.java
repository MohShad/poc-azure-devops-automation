package com.azure.devops.dto.createPipeline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryPipelineDTO {

    private String name;
    private String type;
    private String id;

}
