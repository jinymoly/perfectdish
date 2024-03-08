package com.dish.perfect.member.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.member.MemberFixture;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberCoreServiceTest {

    @Autowired
    private MemberCoreService memberCoreService;
    @Autowired
    private MemberRepository memberRepository;

    private MemberFixture memberFixture = new MemberFixture();

    // @AfterEach
    // void clear(){
    //     memberRepository.deleteAll();
    // }

    // TO-DO
    //springBoot Test
    // testContainer -> serviceTest 
    
    @Test
    @DisplayName("Member 객체 생성")
    void createMember(){
        memberRepository.save(memberFixture.fixtureA().toEntity());
        memberRepository.save(memberFixture.fixtureAD().toEntity());

        List<Member> result = memberRepository.findAll();

        log.info("{}", result);
    }

    @Test
    @DisplayName("duplicate phoneNumber 오류")
    void failByDuplicatedPhoneNumber(){
        Member member1 = memberRepository.save(memberFixture.fixtureB().toEntity());
        Member member2 = memberRepository.save(memberFixture.fixtureBD_PN().toEntity());

        memberCoreService.join(member1);
        memberCoreService.join(member2);

    }

}
