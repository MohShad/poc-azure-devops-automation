package com.azure.devops.dto.createProject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateProjectDTO {

    @NotNull(message = "Enter the Name of organization.")
    private String organizationName;
    @NotNull(message = "Enter the Project Name.")
    private String name;
    @NotNull(message = "Enter the Project Description.")
    private String description;
    @NotNull(message = "Enter the name of the project template.")
    private String templateName;
    private String token;
    @NotNull
    private RequestCapabilitiesDTO capabilities;

}
