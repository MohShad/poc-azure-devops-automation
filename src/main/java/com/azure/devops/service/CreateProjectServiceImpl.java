package com.azure.devops.service;

import com.azure.devops.dto.createProject.RequestCreateProjectDTO;
import com.azure.devops.dto.createRepository.RequestCreateRepositoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@Slf4j
@Service
public class CreateProjectServiceImpl {

    @Autowired
    private CreatePipelineServiceImpl createPipelineServiceImpl;

    @Autowired
    private CreatePRPipelineServiceImpl createPRPipelineServiceImpl;

    @Autowired
    private CreateSonarPipelineMasterServiceImpl createSonarPipelineMasterServiceImpl;

    @Autowired
    private CreateSonarPipelineDevelopServiceImpl createSonarPipelineDevelopServiceImpl;

    @Autowired
    private CreateRepositoryServiceImpl createRepositoryServiceImpl;

    public ResponseEntity<?> createProjectAndTemplate(RequestCreateProjectDTO requestCreateProjectDTO, HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            requestCreateProjectDTO.setToken(request.getHeader("Basic"));
            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor("Basic", requestCreateProjectDTO.getToken()));
            final String baseUrl = "https://dev.azure.com/" + requestCreateProjectDTO.getOrganizationName() + "/_apis/projects?api-version=6.0";
            URI uri = new URI(baseUrl);

            response = restTemplate.postForEntity(uri, requestCreateProjectDTO, String.class);

            RequestCreateRepositoryDTO requestCreateRepositoryDTO = new RequestCreateRepositoryDTO();
            requestCreateRepositoryDTO.setName(requestCreateProjectDTO.getTemplateName());
            requestCreateRepositoryDTO.setParentRepository(requestCreateProjectDTO.getName());
            requestCreateRepositoryDTO.setProject(requestCreateProjectDTO.getName());

            if (response != null && response.getStatusCodeValue() == 202) {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    createRepositoryServiceImpl.createRepository(requestCreateRepositoryDTO, requestCreateProjectDTO.getOrganizationName(), requestCreateProjectDTO.getToken());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                    log.error(e.getMessage());
                                    throw new RuntimeException();
                                }
                                sendSampleCodeToRepository(requestCreateProjectDTO);
                            }
                        },
                        5000
                );
            }

            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    public void sendSampleCodeToRepository(RequestCreateProjectDTO requestCreateProjectDTO) {

        log.info("Preparing the repository to commit the sample code.");

        File localPath = new File("new-template");

        try (Git git = Git.init().setDirectory(localPath).call()) {
            log.info("Created repository: " + git.getRepository().getDirectory());

            String REMOTE_URL =
                    "https://" + requestCreateProjectDTO.getOrganizationName() +
                            "@dev.azure.com/" + requestCreateProjectDTO.getOrganizationName() +
                            "/" + requestCreateProjectDTO.getName() +
                            "/_git/" + requestCreateProjectDTO.getTemplateName();
            StoredConfig config = git.getRepository().getConfig();
            config.setString("remote", "origin", "url", REMOTE_URL);
            config.save();

            File source = new File(requestCreateProjectDTO.getTemplateName());
            File dest = new File("new-template");

            if (!localPath.exists()) {
                throw new IOException("Could not create repository " + localPath);
            }

            try {
                FileUtils.copyDirectory(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }

            DirCache index =
                    git.add()
                            .addFilepattern(".")
                            .call();
            RevCommit commit =
                    git.commit()
                            .setMessage("Initial Commit")
                            .setCommitter("MohShad", "mohammad.shadnik@gmail.com")
                            .call();
            log.info("Committed file from " + source + " to repository at " + git.getRepository().getDirectory());

            Status status = git.status().call();
            Set<String> added = status.getAdded();
            for (String add : added) {
                System.out.println("Added: " + add);
            }
            Set<String> uncommittedChanges = status.getUncommittedChanges();
            for (String uncommitted : uncommittedChanges) {
                System.out.println("Uncommitted: " + uncommitted);
            }
            Set<String> untracked = status.getUntracked();
            for (String untrack : untracked) {
                System.out.println("Untracked: " + untrack);
            }

            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            CredentialsProvider cp = new UsernamePasswordCredentialsProvider("Basic", requestCreateProjectDTO.getToken());
            try (Repository repo = builder.setGitDir(localPath)
                    .readEnvironment()
                    .findGitDir()
                    .build()) {

                git.push().setCredentialsProvider(cp).call();

                Repository repository = git.getRepository();
                log.info("Pushed from repository: " + repository.getDirectory() + " to remote repository at " + REMOTE_URL);
                File file = new File(String.valueOf(localPath));
                FileDeleteStrategy.FORCE.delete(file);
                //FileUtils.deleteDirectory(localPath);

                log.info("Finalizing initial commit in repository.");

                createPipelineServiceImpl.createPipeline(requestCreateProjectDTO);
                createPRPipelineServiceImpl.createPRPipeline(requestCreateProjectDTO);
                createSonarPipelineMasterServiceImpl.createSonarMasterPipeline(requestCreateProjectDTO);
                createSonarPipelineDevelopServiceImpl.createSonarDevelopPipeline(requestCreateProjectDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
