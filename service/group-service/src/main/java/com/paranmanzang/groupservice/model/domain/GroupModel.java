package com.paranmanzang.groupservice.model.domain;

import com.paranmanzang.groupservice.model.entity.Group;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class GroupModel {
    //    private Long groupId; //A.I 컬럼이라 api 수정 필요해보임
    @NotBlank(message = "소모임명은 필수값입니다.")
    private String name;
    @NotBlank(message = "카테고리명은 필수값입니다.")
    private String categoryName;
    private String nickname;



    public Group toEntity() {
        return Group.builder()
                .name(this.name)
                .categoryName(this.categoryName)
                .nickname(this.nickname)
                .build();
    }
}
