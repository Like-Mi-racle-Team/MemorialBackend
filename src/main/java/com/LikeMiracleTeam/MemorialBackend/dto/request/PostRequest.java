package com.LikeMiracleTeam.MemorialBackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String content;
    private Boolean is_public;
}
