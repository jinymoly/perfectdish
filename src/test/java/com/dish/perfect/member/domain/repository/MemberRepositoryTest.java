package com.dish.perfect.member.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.member.MemberFixture;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private MemberFixture fixtureM = new MemberFixture();

    @AfterEach
    void repositoryClear() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("유저 등록시 status가 ACTIVE")
    void save() {
        Member member = memberRepository.save(fixtureM.fixtureMB());
        assertEquals(member.getStatus(), MemberStatus.ACTIVE);
    }

    @Test
    @DisplayName("Member 저장")
    void savesametime() {
        memberRepository.save(fixtureM.fixtureMC());
        memberRepository.save(fixtureM.fixtureMD());
        List<Member> findAll = memberRepository.findAll();

        assertEquals(findAll.size(), 2);

    }

    @Test
    @DisplayName("같은 이름의 회원 등록 가능")
    void findMemberByNameFirst() {
        Member memberA = memberRepository.save(fixtureM.fixtureMA());
        Member memberB = memberRepository.save(fixtureM.fixtureMA_D());

        assertEquals(memberA.getUserName(), memberB.getUserName());
    }



}
