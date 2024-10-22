package com.paranmanzang.gatewayserver.oauth;

import com.paranmanzang.gatewayserver.Enum.Role;
import com.paranmanzang.gatewayserver.model.Domain.oauth.CustomOAuth2User;
import com.paranmanzang.gatewayserver.model.Domain.oauth.NaverResponse;
import com.paranmanzang.gatewayserver.model.Domain.oauth.OAuth2Response;
import com.paranmanzang.gatewayserver.model.Domain.UserModel;
import com.paranmanzang.gatewayserver.model.entity.User;
import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultReactiveOAuth2UserService {

    private final UserRepository userRepository;


    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return super.loadUser(userRequest)
                .flatMap(oAuth2User -> {
                    String registrationId = userRequest.getClientRegistration().getRegistrationId();
                    OAuth2Response oAuth2Response;
                    if (registrationId.equals("naver")) {
                        oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
                    } else {
                        return Mono.error(new OAuth2AuthenticationException("Unsupported registration id"));
                    }
                    String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

                    // Mono를 반환하는 방식으로 변경
                    return userRepository.findByUsername(username)
                            .flatMap(existingUser -> {
                                existingUser.setName(oAuth2Response.getName());
                                existingUser.setTel(oAuth2Response.getMobile());
                                return userRepository.save(existingUser)
                                        .map(updatedUser -> new CustomOAuth2User(convertToUserModel(updatedUser)));
                            })
                            .switchIfEmpty(Mono.defer(() -> {
                                User newUser = new User();
                                newUser.setUsername(username);
                                newUser.setName(oAuth2Response.getName());
                                newUser.setTel(oAuth2Response.getMobile());
                                newUser.setState(true);
                                newUser.setPassword(RandomStringUtils.randomAlphanumeric(10));
                                newUser.setRole(Role.ROLE_USER);

                                return createRandomNickname() // 랜덤 닉네임 생성
                                        .flatMap(nickname -> {
                                            log.info("닉네임 생성됨: {}", nickname);
                                            newUser.setNickname(nickname);
                                            return userRepository.save(newUser)
                                                    .map(savedUser -> new CustomOAuth2User(convertToUserModel(savedUser)));
                                        });
                            }));
                });

    }

    // User 엔티티를 UserModel DTO로 변환
    private UserModel convertToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setName(user.getName());
        userModel.setNickname(user.getNickname());
        userModel.setTel(user.getTel());
        userModel.setRole(user.getRole());
        userModel.setPassword(user.getPassword());
        return userModel;
    }

    public Mono<String> createRandomNickname() {
        String randomNickname = "paran " + RandomStringUtils.randomAlphanumeric(10);

        return userRepository.existsByNickname(randomNickname)
                .flatMap(exists -> {
                    if (exists) {
                        return createRandomNickname(); // 중복된 경우 재귀 호출
                    } else {
                        log.info("닉네임 췤{} : ", randomNickname);
                        return Mono.just(randomNickname); // 유효한 닉네임 반환
                    }
                });
    }


}