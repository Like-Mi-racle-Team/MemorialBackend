package com.LikeMiracleTeam.MemorialBackend.entity;

import com.LikeMiracleTeam.MemorialBackend.entity.baseEntity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Integer userPk;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_introduce")
    private String introduce;

    @Override
    public boolean equals(Object obj) {
        User user;
        if (obj instanceof User) {
            user = (User) obj;
        } else {
            return false;
        }
        return this.userId.equals(user.getUserId());
    }

}
