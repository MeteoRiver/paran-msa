package com.paranmanzang.gatewayserver.model.repository;

import com.paranmanzang.gatewayserver.model.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {


    Mono<User> findByUsername(String username);
    Mono<User> findByNickname(String nickname);
    Mono<Boolean> existsByUsername(String username);
    Mono<Boolean> existsByNickname(String nickname);


}
