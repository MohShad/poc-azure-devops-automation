package com.azure.devops.dto.createRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateRepositoryDTO {

    private String name;
    private String parentRepository;
    private String project;

}
