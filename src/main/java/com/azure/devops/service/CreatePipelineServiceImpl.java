package com.azure.devops.service;

import com.azure.devops.dto.createPipeline.ConfigurationPipelineDTO;
import com.azure.devops.dto.createPipeline.CreatePipelineDTO;
import com.azure.devops.dto.createPipeline.RepositoryPipelineDTO;
import com.azure.devops.dto.createPipeline.responseProject.ResponseDTO;
import com.azure.devops.dto.createProject.RequestCreateProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CreatePipelineServiceImpl {

    public ResponseEntity<?> createPipeline(RequestCreateProjectDTO requestCreateProjectDTO) {

        log.info("Starting pipeline creation.");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {

            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor("Basic", requestCreateProjectDTO.getToken()));
            final String baseUri = "https://dev.azure.com/" + requestCreateProjectDTO.getOrganizationName() + "/" + requestCreateProjectDTO.getName() + "/_apis/pipelines?api-version=6.0-preview.1";
            URI uri = new URI(baseUri);

            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor("Basic", requestCreateProjectDTO.getToken()));
            final String baseUriRepository = "https://dev.azure.com/" + requestCreateProjectDTO.getOrganizationName() + "/" + requestCreateProjectDTO.getName() + "/_apis/git/repositories?api-version=6.0";
            URI uriRepository = new URI(baseUriRepository);

            ResponseEntity<ResponseDTO> responseEntity = restTemplate.getForEntity(uriRepository, ResponseDTO.class);
            List<ResponseDTO> repositoryList = Collections.singletonList(responseEntity.getBody());

            String id = null;
            for (ResponseDTO repo : repositoryList) {
                int size = repo.getCount();
                for (int i = 0; i < size; i++)
                    if (repo.getValue().get(i).getName().equals(requestCreateProjectDTO.getTemplateName()))
                        id = repo.getValue().get(i).getId();
            }

            CreatePipelineDTO createPipelineDTO = new CreatePipelineDTO();
            ConfigurationPipelineDTO configurationPipelineDTO = new ConfigurationPipelineDTO();
            RepositoryPipelineDTO repositoryPipelineDTO = new RepositoryPipelineDTO();

            repositoryPipelineDTO.setName(requestCreateProjectDTO.getTemplateName());
            repositoryPipelineDTO.setType("azureReposGit");
            repositoryPipelineDTO.setId(id);

            configurationPipelineDTO.setRepository(repositoryPipelineDTO);
            configurationPipelineDTO.setPath("azure-pipelines.yml");
            configurationPipelineDTO.setType("yaml");

            createPipelineDTO.setConfiguration(configurationPipelineDTO);
            createPipelineDTO.setFolder(requestCreateProjectDTO.getTemplateName());
            createPipelineDTO.setName("Build");

            response = restTemplate.postForEntity(uri, createPipelineDTO, String.class);
            log.info("Finalizing the creation of the pipeline.");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
