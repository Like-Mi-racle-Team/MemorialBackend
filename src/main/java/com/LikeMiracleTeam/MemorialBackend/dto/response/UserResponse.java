package com.LikeMiracleTeam.MemorialBackend.dto.response;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String user_name;
    private String user_id;
    private String introduce;

    public UserResponse(User user) {
        this.user_name = user.getName();
        this.user_id = user.getUserId();
        this.introduce = user.getIntroduce();
    }
}
