package com.dish.perfect.member.dto.response;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MemberCommonResponse {

    private final String phoneNumber;
    private final String userName;
    private final MemberStatus status;

    // TODO : private final Integer visitCount;

    // TODO : private final List<Menu> orderRecode; // 주문 기록
    
    public static MemberCommonResponse fromResponse(final Member member) {
        return MemberCommonResponse.builder()
                .userName(member.getUserName())
                .phoneNumber(member.getPhoneNumber())
                .status(member.getStatus())
                .build();
    }
}
