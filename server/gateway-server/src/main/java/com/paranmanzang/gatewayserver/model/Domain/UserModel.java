package com.paranmanzang.gatewayserver.model.Domain;

import com.paranmanzang.gatewayserver.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title="유저 모델")
public class UserModel {
    @Schema(title="로그인용 이메일")
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "이메일 주소의 형식은 ~~@~~.~~입니다.")
    private String username;

    @Schema(title="비밀번호")
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,128}$",
            message = "비밀번호는 8자리 이상 대소문자+숫자+특수문자 조합으로 구성되어야합니다.")
    private String password;

    @Schema(title="전화번호")
    @NotBlank
    @Pattern(regexp = "^01//d{1}-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 01으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String tel;

    @Schema(title="닉네임 중복불가")
    @NotBlank
    private String nickname;

    @Schema(title="이름")
    @NotBlank
    private String name;

    @Schema(title="권한", defaultValue = "ROLE_USER")
    private Role role = Role.ROLE_USER; // 기본값 설정

    @Schema(title="회원 탈퇴 여부 탈퇴시 false")
    private boolean state;
}
