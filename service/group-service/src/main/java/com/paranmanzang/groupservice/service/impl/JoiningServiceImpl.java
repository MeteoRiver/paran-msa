package com.paranmanzang.groupservice.service.impl;

import com.paranmanzang.groupservice.model.domain.ErrorField;
import com.paranmanzang.groupservice.model.domain.JoiningModel;
import com.paranmanzang.groupservice.model.entity.Joining;
import com.paranmanzang.groupservice.model.repository.GroupRepository;
import com.paranmanzang.groupservice.model.repository.JoiningRepository;
import com.paranmanzang.groupservice.service.JoiningService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoiningServiceImpl implements JoiningService {
    private final JoiningRepository joiningRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public Object addMember(JoiningModel joiningModel) {
        return joiningRepository.findJoiningByGroupIdAndNickname(joiningModel.getGroupId(), joiningModel.getNickname())
                .map(joining -> (Object) new ErrorField(joiningModel.getNickname(), "이미 가입되어있는 멤버입니다."))
                .orElseGet(() -> groupRepository.findById(joiningModel.getGroupId())
                        .map(group -> (Object) JoiningModel.fromEntity(joiningRepository.save(Joining.builder()
                                .nickname(joiningModel.getNickname())
                                .group(groupRepository.findById(joiningModel.getGroupId()).get())
                                .build())))
                        .orElseGet(() -> (Object) new ErrorField(joiningModel.getNickname(), "그룹을 찾을 수 없습니다.")));
    }

    public Object enableMember(Long groupId, String nickname) {
        return joiningRepository.findByGroupIdAndNickname(groupId, nickname)
                .map(joiningToEnable -> {
                    if (!joiningToEnable.isEnabled()) {
                        joiningToEnable.setEnabled(true);
                        return JoiningModel.fromEntity(joiningRepository.save(joiningToEnable));
                    } else {
                        return (Object) new ErrorField(nickname, "이미 승인된 멤버입니다.");
                    }
                })
                .orElseGet(() -> new ErrorField(nickname, "가입정보가 없습니다."));
    }


    public Object disableMember(Long groupId, String nickname) {
        return joiningRepository.findByGroupIdAndNickname(groupId, nickname)
                .map(joiningToEnable -> {
                    if (joiningToEnable.isEnabled()) {
                        joiningToEnable.setEnabled(false);
                        return JoiningModel.fromEntity(joiningRepository.save(joiningToEnable));
                    } else {
                        return (Object) new ErrorField(nickname, "미승인 멤버입니다.");
                    }
                })
                .orElseGet(() -> new ErrorField(nickname, "가입정보가 없습니다."));
    }


    @Override
    public Object getUserListById(Long groupId) {
        return joiningRepository.findByGroupId(groupId).stream()
                .map(JoiningModel::fromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Object deleteUser(String nickname, Long groupId) {
        joiningRepository.findByGroupId(groupId).stream()
                .filter(joining -> joining.getNickname().equals(nickname))
                .forEach(joining -> joiningRepository.deleteById(joining.getId()));

        return true;
    }
}
