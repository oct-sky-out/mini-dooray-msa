package com.nhnacademy.minidoorayprojectmanagementapi.tag.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TagRequest {
    @NotBlank
    @Length(max = 30)
    private String tagName;
}
