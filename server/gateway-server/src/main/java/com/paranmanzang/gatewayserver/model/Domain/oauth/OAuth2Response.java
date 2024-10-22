package com.paranmanzang.gatewayserver.model.Domain.oauth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="OAUTH 가입정보")
public interface OAuth2Response {

    //제공자 (Ex. naver, google, ...)
    @Schema(title="제공자")
    String getProvider();
    //제공자에서 발급해주는 아이디(번호)
    @Schema(title="제공 ID")
    String getProviderId();
    //사용자 실명 (설정한 이름)
    @Schema(title="사용자 이름")
    String getName();
    //전화번호
    @Schema(title="사용자 전화번호")
    String getMobile();

}
