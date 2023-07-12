package com.LikeMiracleTeam.MemorialBackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String userId;
    private String userPassword;
    private String userName;
    private String userIntroduce;
}
