package com.nhnacademy.minidoorayuserapi.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFindAllResponse {
    List<UserBasicDto> users;

    Boolean hasNext;

    Boolean hasPrevious;

    Integer currentPage;
}
