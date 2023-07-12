package com.LikeMiracleTeam.MemorialBackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String user_id;
    private String user_password;
    private String user_name;
    private String user_introduce;
}
