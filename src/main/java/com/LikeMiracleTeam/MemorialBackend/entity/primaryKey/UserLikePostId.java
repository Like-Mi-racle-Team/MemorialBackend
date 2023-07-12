package com.LikeMiracleTeam.MemorialBackend.entity.primaryKey;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Setter
@Getter
public class UserLikePostId implements Serializable {
    private static final long serialVersionUID = -1276703271000754940L;
    String userPk;
    String postPk;
}
