package com.paranmanzang.gatewayserver.model.entity;


import com.paranmanzang.gatewayserver.Enum.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String username;
    private String nickname;
    private String password;
    private String name;
    private Role role;
    private String tel;
    private int declarationCount = 0;
    private boolean state =true;
    @CreatedDate
    @Field(write = Field.Write.ALWAYS)
    private LocalDateTime logoutAt;


}
