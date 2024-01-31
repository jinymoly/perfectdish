package com.dish.perfect.member.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        this.status = status;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                "  userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status='" + status + '\'' +
                ", createAt=" + timeFomatter(createAt);
    }

    private String timeFomatter(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-ss/hh:MM:ss"));
    }

}
