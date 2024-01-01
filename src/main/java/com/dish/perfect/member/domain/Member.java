package com.dish.perfect.member.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Member {

    private Long id;

    private String userName;
    private String phoneNumber; // 8자리

    private LocalDateTime createAt;

    public Member(Long id, String name, String phoneNumber, LocalDateTime createAt) {
        this.id = id;
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.createAt = LocalDateTime.now();
    }

    /*
     * TO-DO 
     * add field list
     *  birth 쿠폰
     *  포인트 적립 
     *  방문 횟수 
     */
    
}
