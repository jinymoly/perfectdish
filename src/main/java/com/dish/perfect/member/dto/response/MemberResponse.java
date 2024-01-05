package com.dish.perfect.member.dto.response;

import com.dish.perfect.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MemberResponse {

    private final Long id;
    private final String phoneNumber;
    private final String userName;

    // TODO : private final Integer visitCount;

    // TODO : private final List<Menu> orderRecode; // 주문 기록

    public static MemberResponse toResponse(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .phoneNumber(member.getPhoneNumber())
                .userName(member.getUserName())
                .build();
    }

}
