package com.nhnacademy.minidooraygateway.user.service;

import com.nhnacademy.minidooraygateway.user.adptor.FindUserAdaptor;
import com.nhnacademy.minidooraygateway.user.response.UserFindAllResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final FindUserAdaptor findUserAdaptor;

    public UserService(FindUserAdaptor findUserAdaptor) {
        this.findUserAdaptor = findUserAdaptor;
    }

    public UserFindAllResponse findMiniDoorayMembers(Long userNo, Integer size, Integer page){
        return findUserAdaptor.findMiniDoorayMembers(userNo, size, page);
    }
}
