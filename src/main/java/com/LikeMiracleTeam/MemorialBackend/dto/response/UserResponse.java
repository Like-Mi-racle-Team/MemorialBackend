package com.LikeMiracleTeam.MemorialBackend.dto.response;

import com.LikeMiracleTeam.MemorialBackend.dto.request.UserRequest;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String username;
    private String userId;
    private String introduce;

    public UserResponse(User user) {
        this.username = user.getName();
        this.userId = user.getUserId();
        this.introduce = user.getIntroduce();
    }
}
