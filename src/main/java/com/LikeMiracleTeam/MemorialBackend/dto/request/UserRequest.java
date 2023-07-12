package com.LikeMiracleTeam.MemorialBackend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {
    private String userId;
    private String userPassword;
    private String userName;
    private String userIntroduce;
}
