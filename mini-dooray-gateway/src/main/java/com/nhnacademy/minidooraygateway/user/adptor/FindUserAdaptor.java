package com.nhnacademy.minidooraygateway.user.adptor;

import com.nhnacademy.minidooraygateway.user.response.UserFindAllResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FindUserAdaptor {

    private final RestTemplate restTemplate;

    public FindUserAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserFindAllResponse findMiniDoorayMembers(Long userNo, Integer size,  Integer page){
        return restTemplate.getForEntity(
            "http://localhost:8082/users?userNo={userNo}&size={size}&page={page}",
            UserFindAllResponse.class,
            userNo,
            size,
            page
        ).getBody();
    }
}
