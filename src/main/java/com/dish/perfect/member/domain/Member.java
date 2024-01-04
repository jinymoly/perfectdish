package com.dish.perfect.member.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Member {

    private Long id;

    private String userName;

    private String phoneNumber; // 8자리

    private LocalDateTime createAt;

    @Builder
    private Member(String name, String phoneNumber) {
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.createAt = LocalDateTime.now();
    }

}
