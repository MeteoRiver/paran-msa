package com.paranmanzang.gatewayserver.service.Impl;

import com.paranmanzang.gatewayserver.Enum.Role;
import com.paranmanzang.gatewayserver.model.RegisterModel;
import com.paranmanzang.gatewayserver.model.entity.User;
import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import com.paranmanzang.gatewayserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static com.paranmanzang.gatewayserver.Enum.Role.ROLE_ADMIN;
import static com.paranmanzang.gatewayserver.Enum.Role.ROLE_USER;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Mono<Void> insert(RegisterModel registerModel) {
        return userRepository.existsByUsername(registerModel.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("이미 존재하는 사용자입니다."));
                    }
                    return userRepository.existsByNickname(registerModel.getNickname());
                })
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("이미 존재하는 닉네임입니다."));
                    }
                    if (!registerModel.getPassword().equals(registerModel.getPasswordcheck())) {
                        return Mono.error(new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다."));
                    }
                    if (!Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", registerModel.getUsername())) {
                        return Mono.error(new IllegalArgumentException("이메일 형식이 올바르지 않습니다."));
                    }
                    if (!Pattern.matches("^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,128}$", registerModel.getPassword())) {
                        return Mono.error(new IllegalArgumentException("비밀번호가 올바르지 않습니다. 최소 8자 이상이며, 알파벳 대소문자, 숫자 또는 특수 문자가 포함시켜주세요."));
                    }
                    if (!Pattern.matches("^010-\\d{3,4}-\\d{4}$", registerModel.getTel())) {
                        return Mono.error(new IllegalArgumentException("휴대폰 번호가 올바르지 않습니다."));
                    }

                    // 비밀번호 암호화
                    String encodedPassword = passwordEncoder.encode(registerModel.getPassword());

                    // 새로운 UserModel 생성
                    User user = new User();
                    user.setUsername(registerModel.getUsername());
                    user.setPassword(encodedPassword);
                    user.setName(registerModel.getName());
                    user.setTel(registerModel.getTel());
                    user.setNickname(registerModel.getNickname());
                    user.setState(true);
                    user.setRole(ROLE_USER); // 기본 역할 설정

                    // 사용자 정보 저장
                    return userRepository.save(user).then(); // 저장 후 void 반환
                })
                .onErrorResume(DataAccessException.class, e -> {
                    // 데이터베이스 접근 예외 처리
                    return Mono.error(new RuntimeException("회원가입 중 데이터베이스 오류 발생: " + e.getMessage(), e));
                });
    }

    public Mono<Boolean> remove(String nickname){
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다.")))
                .flatMap(user-> {
                    if (!user.isState()) {
                        return Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다."));
                    }
                    user.setState(false);
                    return userRepository.save(user).then(Mono.just(true));
                })
                .onErrorResume(DataAccessException.class, e ->{
                    return Mono.error(new RuntimeException("사용자 삭제 중 오류 발생: " + e.getMessage(), e));
                })
                .onErrorResume(IllegalArgumentException.class, e->{
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }

    public Mono<Boolean> updateLogoutTime(String nickname){
        System.out.println(nickname);
        log.info("이거 이상하다 : {}", userRepository.findByUsername(nickname));
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다,")))
                .flatMap(user->{
                    log.info("이거 이상하다 : {}", user);
                    user.setLogoutAt(LocalDateTime.now());
                    return userRepository.save(user).then(Mono.just(true));
                })
                .onErrorResume(IllegalArgumentException.class, e->{
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }

    public Mono<Boolean> updatePassword(String nickname, String newPassword){
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다,")))
                .flatMap(user -> {
                    if(user.getPassword().equals(newPassword)){
                        return Mono.error(new IllegalArgumentException("기존의 비밀번호와 동일합니다"));
                    }
                    user.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(user).then(Mono.just(true));
                })
                .onErrorResume(DataAccessException.class, e ->{
                    return Mono.error(new RuntimeException("비밀번호 변경 중 오류 발생: " + e.getMessage(), e));
                })
                .onErrorResume(IllegalArgumentException.class, e->{
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }
    public Mono<Boolean> updateRole(String nickname, Role newRole) {
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다.")))
                .flatMap(user -> {
                    if (user.getRole().equals(newRole)) {
                        return Mono.error(new IllegalArgumentException("기존의 권한과 동일합니다."));
                    }
                    user.setRole(newRole);
                    return userRepository.save(user).then(Mono.just(true));
                })
                .onErrorResume(DataAccessException.class, e -> {
                    return Mono.error(new RuntimeException("권한 변경 중 오류 발생: " + e.getMessage(), e));
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }

    public Mono<Boolean> updateDeclaration(String nickname){
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다,")))
                .flatMap(user -> {
                    user.setDeclarationCount(user.getDeclarationCount()+1);
                    if(user.getDeclarationCount()==5){
                        return remove(nickname);
                    }
                    return userRepository.save(user).then(Mono.just(true));
                })
                .onErrorResume(DataAccessException.class, e ->{
                    return Mono.error(new RuntimeException("신고 횟수 업데이트 중 오류 발생: " + e.getMessage(), e));
                })
                .onErrorResume(IllegalArgumentException.class, e->{
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }
    public Mono<Boolean> checkNickname(RegisterModel registerModel){
        return userRepository.existsByNickname(registerModel.getNickname())
                .map(exists -> !exists) // 존재하지 않으면 True
                .onErrorResume(DataAccessException.class, e ->{
                    return Mono.error(new RuntimeException("닉네임 중복 확인 중 데이터베이스 오류 발생: " + e.getMessage()));
                });
    }

    public Mono<Boolean> checkUsername(RegisterModel registerModel){
        return userRepository.existsByUsername(registerModel.getUsername())
                .map(exists -> !exists) // 존재하지 않으면 True
                .onErrorResume(DataAccessException.class, e ->{
                    return Mono.error(new RuntimeException("닉네임 중복 확인 중 데이터베이스 오류 발생: " + e.getMessage()));
                });
    }

    public Mono<Boolean> checkPassword(RegisterModel registerModel){
        if(registerModel.getPassword().equals(registerModel.getPasswordcheck())){
            return Mono.just(true);
        }
        return Mono.just(false);
    }

    public Mono<List<User>> findAllByNickname(String nickname){
        return userRepository.findByNickname(nickname)
                .flatMap(user -> {
                    if (user.getRole().equals(ROLE_ADMIN)) {
                        return userRepository.findAll().collectList(); // 모든 사용자 조회
                    } else {
                        return Mono.error(new IllegalArgumentException("관리자만 접근 가능합니다."));
                    }
                })
                .onErrorResume(DataAccessException.class, e -> {
                    // 데이터베이스 접근 예외 처리
                    return Mono.error(new RuntimeException("모든 사용자 조회 중 데이터베이스 오류 발생: " + e.getMessage(), e));
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    // 비즈니스 로직 예외 처리
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }
    public Mono<User> findByNickname(String nickname){
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다."))) // 사용자 존재 여부 확인
                .onErrorResume(DataAccessException.class, e -> {
                    // 데이터베이스 접근 예외 처리
                    return Mono.error(new RuntimeException("사용자 조회 중 데이터베이스 오류 발생: " + e.getMessage(), e));
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    // 비즈니스 로직 예외 처리
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }

    public Mono<Role> checkRole(String nickname){
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("사용자가 존재하지 않습니다."))) // 사용자 존재 여부 확인
                .map(user -> user.getRole()) // 사용자의 ROLE을 반환
                .onErrorResume(IllegalArgumentException.class, e -> {
                    return Mono.error(new IllegalArgumentException(e.getMessage(), e));
                });
    }

}

