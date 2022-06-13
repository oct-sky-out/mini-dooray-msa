package com.nhnacademy.minidooraygateway.project.respone;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectStatusModifyResponse {
    @NotNull
    Long projectNo;

    @NotBlank
    String projectStatus;
}
