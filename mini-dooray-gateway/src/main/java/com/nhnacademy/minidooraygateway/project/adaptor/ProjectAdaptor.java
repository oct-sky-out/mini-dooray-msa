package com.nhnacademy.minidooraygateway.project.adaptor;

import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.request.ProjectRegisterRequest;
import com.nhnacademy.minidooraygateway.project.respone.ProjectCreationSuccessResponse;
import java.util.List;
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

    public MyProjectsPageRequest findMyProjects(Long myUserNo){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
            "http://localhost:8081/projects/{userNo}",
            HttpMethod.GET,
            httpEntity,
            MyProjectsPageRequest.class,
            myUserNo
        ).getBody();
    }

    public ProjectCreationSuccessResponse createProject(ProjectRegisterRequest registerRequest) {
        return restTemplate.postForEntity(
            "http://localhost:8081/projects",
            registerRequest,
            ProjectCreationSuccessResponse.class)
            .getBody();
    }
}
