package com.dish.perfect.member.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.member.MemberFixture;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    private InMemoryMemberRepository memberRepository;

    private MemberFixture fixtureM = new MemberFixture();

    @AfterEach
    void clear(){
        memberRepository.clear();
    }
    @Test
    @DisplayName("유저 등록시 status가 ACTIVE")
    void save() {
        Member member = memberRepository.save(fixtureM.fixtureA());
        assertEquals(MemberStatus.ACTIVE, member.getStatus());
    }

    @Test
    @DisplayName("저장 - ConcurrentHashMap 동작")
    void savesametime() {
        memberRepository.save(fixtureM.fixtureC());
        memberRepository.save(fixtureM.fixtureB());
        memberRepository.save(fixtureM.fixtureD());
        memberRepository.save(fixtureM.fixtureE());
        memberRepository.save(fixtureM.fixtureF());
        List<Member> findAll = memberRepository.findAll();
        log.info("{}", findAll);

    }

    @Test
    @DisplayName("회원 조회 흐름 - filter : 이름 -> filter : 번호")
    void findMemberByNameFirst() {
        Member memberA = memberRepository.save(fixtureM.fixtureA());
        Member memberB = memberRepository.save(fixtureM.fixtureAD());
        List<Member> findByNameList = memberRepository.findByName(memberB.getUserName());

        Member result = memberRepository.findByPhoneNumberWithList(findByNameList, memberB.getPhoneNumber());
        
        assertSame(memberA.getUserName(), memberB.getUserName());
        assertNotSame(memberA.getPhoneNumber(), memberB.getPhoneNumber());
        assertEquals(memberB.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    @DisplayName("회원 삭제시 status가 DELETED로 변경")
    void deleteMember() {
        Member newMemA = memberRepository.save(fixtureM.fixtureA());

        List<Member> membersBysameName = memberRepository.findByName(newMemA.getUserName());
        Member findMember = memberRepository.findByPhoneNumberWithList(membersBysameName, "22223333");
        
        memberRepository.deleteMember(findMember);

        List<Member> findByName = memberRepository.findByName(findMember.getUserName());
        Member result = memberRepository.findByPhoneNumberWithList(findByName, findMember.getPhoneNumber());

        assertEquals(result.getStatus(), MemberStatus.DELETED);

        
    }
    
    @Test
    @DisplayName("회원 정보 변경")
    void updateMemberInfo(){
        Member newMemF = memberRepository.save(fixtureM.fixtureF());
        List<Member> findByName = memberRepository.findByName(newMemF.getUserName());
        Member member = memberRepository.findByPhoneNumberWithList(findByName, newMemF.getPhoneNumber());
        MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
                                                        .userName("update")
                                                        .phoneNumber("99999999")
                                                        .status(member.getStatus())
                                                        .build();
        memberRepository.update(newMemF.getId(), memberUpdateRequest);
        
        assertNotEquals(newMemF.getUserName(), "update");
    }

}
