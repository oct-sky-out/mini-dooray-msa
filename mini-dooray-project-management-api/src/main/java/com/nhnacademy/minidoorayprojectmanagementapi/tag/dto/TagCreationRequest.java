package com.nhnacademy.minidoorayprojectmanagementapi.tag.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TagCreationRequest {
    @NotBlank
    @Length(max = 10)
    private String tagName;
}
