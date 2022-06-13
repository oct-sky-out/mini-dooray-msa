package com.nhnacademy.minidooraygateway.project.adaptor;

import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.respone.ProjectRegisterResponse;
import com.nhnacademy.minidooraygateway.project.respone.ProjectCreationSuccessResponse;
import com.nhnacademy.minidooraygateway.project.respone.ProjectStatusModifyResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProjectAdaptor {
    private final RestTemplate restTemplate;

    public ProjectAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MyProjectsPageRequest findMyProjects(Long size, Long page, Long myUserNo){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
            "http://localhost:8081/projects/{userNo}?size={size}&page={page}",
            HttpMethod.GET,
            httpEntity,
            MyProjectsPageRequest.class,
            myUserNo, size, page
        ).getBody();
    }

    public ProjectCreationSuccessResponse createProject(ProjectRegisterResponse registerRequest) {
        return restTemplate.postForEntity(
            "http://localhost:8081/projects",
            registerRequest,
            ProjectCreationSuccessResponse.class)
            .getBody();
    }

    public ProjectCreationSuccessResponse modifyStatus(ProjectStatusModifyResponse statusModifyResponse) {
        return restTemplate.patchForObject(
                "http://localhost:8081/projects/{projectNo}/status",
                statusModifyResponse,
                ProjectCreationSuccessResponse.class,
                statusModifyResponse.getProjectNo()
                );
    }
}
