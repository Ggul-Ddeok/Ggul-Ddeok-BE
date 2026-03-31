package com.example.ggulddeokbe.domain.user.service;

import com.example.ggulddeokbe.domain.user.domain.User;
import com.example.ggulddeokbe.domain.user.dto.UserInfoResponse;
import com.example.ggulddeokbe.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserFacade userFacade;

    public UserInfoResponse getMe() {
        User user = userFacade.getCurrentUser();
        return new UserInfoResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}