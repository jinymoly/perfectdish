package com.dish.perfect.member.dto.response;

import java.time.LocalDateTime;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MemberDetailResponse {

    private final String phoneNumber;
    private final String userName;
    private final MemberStatus status;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static MemberDetailResponse fromResponse(final Member member) {
        return MemberDetailResponse.builder()
                .userName(member.getUserName())
                .phoneNumber(member.getPhoneNumber())
                .status(member.getStatus())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
