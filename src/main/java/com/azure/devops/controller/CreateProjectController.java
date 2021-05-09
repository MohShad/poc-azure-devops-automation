package com.azure.devops.controller;

import com.azure.devops.dto.ApiResponse;
import com.azure.devops.dto.createProject.RequestCreateProjectDTO;
import com.azure.devops.service.CreateProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/azure-devops/api")
public class CreateProjectController {

    @Autowired
    private CreateProjectServiceImpl createProjectServiceImpl;

    @PostMapping
    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    public ApiResponse createProject(@Valid @RequestBody RequestCreateProjectDTO requestCreateProjectDTO, HttpServletRequest request) {

        ResponseEntity<?> response = null;
        try {

            response = (ResponseEntity<?>) createProjectServiceImpl.createProjectAndTemplate(requestCreateProjectDTO, request);

            return new ApiResponse(true, response.getBody().toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            if(response == null)
                return new ApiResponse(false, "null pointer");
            return new ApiResponse(false, "Duplicate project name");
        }

    }

}
