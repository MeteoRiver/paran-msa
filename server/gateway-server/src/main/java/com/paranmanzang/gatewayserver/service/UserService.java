package com.paranmanzang.gatewayserver.service;

import com.paranmanzang.gatewayserver.Enum.Role;
import com.paranmanzang.gatewayserver.model.RegisterModel;
import com.paranmanzang.gatewayserver.model.entity.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;


@Service
public interface UserService {
        Mono<Void> insert(RegisterModel registerModel);

        Mono<Boolean> remove(String nickname);

        Mono<Boolean> updateRole(String nickname, Role newRole);

        Mono<Boolean> updateLogoutTime(String nickname);

        Mono<Boolean> updatePassword(String nickname, String newPassword);

        Mono<Boolean> checkNickname(RegisterModel registerModel);

        Mono<Boolean> checkPassword(RegisterModel registerModel);

        Mono<List<User>> findAllByNickname(String nickname);

        Mono<User> findByNickname(String nickname);

}
