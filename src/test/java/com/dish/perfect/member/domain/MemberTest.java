package com.dish.perfect.member.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberTest {
    @Test
    @DisplayName("Member toString 확인")
    void testToString(){
        Member memberA = Member.builder()
                                .id(19L)
                                .userName("유바바")
                                .phoneNumber("23453456")
                                .status(MemberStatus.ACTIVE)
                                .createAt(LocalDateTime.now())
                                .build();
        String savedText = memberA.toString();

        log.info("{}", savedText.toString());
                                
    }
}
