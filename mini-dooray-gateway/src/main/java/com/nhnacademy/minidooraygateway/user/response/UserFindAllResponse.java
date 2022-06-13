package com.nhnacademy.minidooraygateway.user.response;

import com.nhnacademy.minidooraygateway.user.dto.UserBasicDto;
import java.util.List;
import lombok.Data;

@Data
public class UserFindAllResponse {
    private List<UserBasicDto> users;

    private Boolean hasNext;

    private Boolean hasPrevious;

    private Long currentPage;
}
