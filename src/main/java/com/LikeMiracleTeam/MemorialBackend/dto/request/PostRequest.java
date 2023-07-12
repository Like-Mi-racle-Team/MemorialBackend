package com.LikeMiracleTeam.MemorialBackend.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    @ApiModelProperty(value = "게시글 내용", required = true)
    private String content;
    @ApiModelProperty(value = "공개여부",  required = true)
    private Boolean is_public;
}
