package com.azure.devops.controller;

import com.azure.devops.dto.createRepository.RequestCreateRepositoryDTO;
import com.azure.devops.service.CreateRepositoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/azure-devops/api")
public class CreateRepositoryController {

    @Autowired
    private CreateRepositoryServiceImpl createRepositoryServiceImpl;

    @PostMapping
    @RequestMapping(value = "/createRepository", method = RequestMethod.POST)
    public String createRepository(@RequestBody RequestCreateRepositoryDTO requestCreateRepositoryDTO, String organizationName, String token) {

        ResponseEntity<?> response = null;
        try {

            response = (ResponseEntity<String>) createRepositoryServiceImpl.createRepository(requestCreateRepositoryDTO, organizationName, token);

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return response.toString();
    }

}
