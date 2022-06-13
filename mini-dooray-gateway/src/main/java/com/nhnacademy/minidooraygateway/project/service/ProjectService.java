package com.nhnacademy.minidooraygateway.project.service;

import com.nhnacademy.minidooraygateway.project.adaptor.ProjectAdaptor;
import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.request.ProjectRegisterRequest;
import com.nhnacademy.minidooraygateway.project.respone.ProjectCreationSuccessResponse;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectAdaptor projectAdaptor;

    public ProjectService(ProjectAdaptor projectAdaptor) {
        this.projectAdaptor = projectAdaptor;
    }

    public MyProjectsPageRequest findMyProjects(Long myUserNo){
        return projectAdaptor.findMyProjects(myUserNo);
    }

    public ProjectCreationSuccessResponse createProject(ProjectRegisterRequest registerRequest) {
        return projectAdaptor.createProject(registerRequest);
    }
}
