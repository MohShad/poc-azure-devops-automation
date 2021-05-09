package com.azure.devops.service;

import com.azure.devops.dto.createRepository.RequestCreateRepositoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class CreateRepositoryServiceImpl {

    public ResponseEntity<?> createRepository(RequestCreateRepositoryDTO requestCreateRepositoryDTO, String organizationName, String token) throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = null;
        try {
            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor("Basic", token));
            final String baseUrl = "https://dev.azure.com/" + organizationName + "/" + requestCreateRepositoryDTO.getProject() + "/_apis/git/repositories?sourceRef=&api-version=6.1-preview.1";
            URI uri = new URI(baseUrl);

            responseEntity = restTemplate.postForEntity(uri, requestCreateRepositoryDTO, String.class);

            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
