package com.LikeMiracleTeam.MemorialBackend.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @ApiModelProperty(value = "유저 아이디", example = "example1234", required = false)
    private String user_id;
    @ApiModelProperty(value = "유저 비밀번호", example = "dueWsj483#", required = false)
    private String user_password;
    @ApiModelProperty(value = "유저 이름", example = "성의근", required = false)
    private String user_name;
    @ApiModelProperty(value = "유저 자기소개", example = "저는 대구소프트웨어고에 재학 중입니다.", required = false)
    private String user_introduce;
}