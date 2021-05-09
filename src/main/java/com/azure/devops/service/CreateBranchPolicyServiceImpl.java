package com.azure.devops.service;

import com.azure.devops.dto.branchPolicy.RequestBranchPolicyDTO;
import com.azure.devops.dto.branchPolicy.ScopeDTO;
import com.azure.devops.dto.branchPolicy.SettingsDTO;
import com.azure.devops.dto.branchPolicy.TypeDTO;
import com.azure.devops.dto.createProject.RequestCreateProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CreateBranchPolicyServiceImpl {

    public ResponseEntity<?> createBranchPolicy(RequestCreateProjectDTO requestCreateProjectDTO, String repositoryId) {

        log.info("Applying branch policy(Minimum number of reviewers).");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {

            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor("Basic", requestCreateProjectDTO.getToken()));
            final String baseUri = "https://dev.azure.com/" + requestCreateProjectDTO.getOrganizationName() + "/" + requestCreateProjectDTO.getName() + "/_apis/policy/configurations?api-version=6.0";
            URI uri = new URI(baseUri);

            RequestBranchPolicyDTO requestBranchPolicyDTO = new RequestBranchPolicyDTO();
            TypeDTO typeDTO = new TypeDTO();
            SettingsDTO settingsDTO = new SettingsDTO();
            ScopeDTO scopeDTO = new ScopeDTO();
            List<ScopeDTO> listScopeDTO = new ArrayList<>();

            scopeDTO.setRepositoryId(repositoryId);
            scopeDTO.setRefName("refs/heads/master");
            scopeDTO.setMatchKind("exact");
            listScopeDTO.add(scopeDTO);

            settingsDTO.setMinimumApproverCount(1);
            settingsDTO.setCreatorVoteCounts(false);
            settingsDTO.setScopeDTO(listScopeDTO);
            typeDTO.setId("fa4e907d-c16b-4a4c-9dfa-4906e5d171dd");

            requestBranchPolicyDTO.setIsEnabled(true);
            requestBranchPolicyDTO.setIsBlocking(false);
            requestBranchPolicyDTO.setTypeDTO(typeDTO);
            requestBranchPolicyDTO.setSettingsDTO(settingsDTO);

            response = restTemplate.postForEntity(uri, requestBranchPolicyDTO, String.class);
            log.info("Finalize branch policy(Minimum number of reviewers).");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
