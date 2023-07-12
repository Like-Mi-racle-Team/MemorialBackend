package com.LikeMiracleTeam.MemorialBackend.entity.primaryKey;

import com.LikeMiracleTeam.MemorialBackend.entity.User;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikePostId implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLikePostId that = (UserLikePostId) o;
        return userPk.equals(that.userPk) && postPk.equals(that.postPk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPk, postPk);
    }

    Integer userPk;
    Long postPk;

}
