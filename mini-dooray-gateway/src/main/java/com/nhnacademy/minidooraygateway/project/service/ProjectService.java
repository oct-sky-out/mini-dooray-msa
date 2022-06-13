package com.nhnacademy.minidooraygateway.project.service;

import com.nhnacademy.minidooraygateway.project.adaptor.ProjectAdaptor;
import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.respone.ProjectRegisterResponse;
import com.nhnacademy.minidooraygateway.project.respone.ProjectCreationSuccessResponse;
import com.nhnacademy.minidooraygateway.project.respone.ProjectStatusModifyResponse;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectAdaptor projectAdaptor;

    public ProjectService(ProjectAdaptor projectAdaptor) {
        this.projectAdaptor = projectAdaptor;
    }

    public MyProjectsPageRequest findMyProjects(Long size, Long page, Long myUserNo){
        return projectAdaptor.findMyProjects(size, page, myUserNo);
    }

    public ProjectCreationSuccessResponse createProject(ProjectRegisterResponse registerRequest) {
        return projectAdaptor.createProject(registerRequest);
    }

    public ProjectCreationSuccessResponse modifyStatus(ProjectStatusModifyResponse statusModifyResponse) {
        return projectAdaptor.modifyStatus(statusModifyResponse);
    }
}
