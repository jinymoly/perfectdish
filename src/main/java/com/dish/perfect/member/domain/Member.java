package com.dish.perfect.member.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Member {

    private Long id;

    private String userName;

    private String phoneNumber; // 8자리

    private LocalDateTime createAt;

    private MemberStatus status;

    @Builder
    private Member(Long id, String userName, String phoneNumber, LocalDateTime createAt, MemberStatus status) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.status = MemberStatus.ACTIVE;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "[new] id='" + id + '\'' +
                "  userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status='" + status + '\'' +
                ", createAt=" + createAt;
    }

}
