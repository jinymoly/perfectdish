package com.dish.perfect.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private MemberFixture fixtureM = new MemberFixture();

    
    @AfterEach
    void clearServiceTest() {
        memberService.clear();
    }

    @Test
    @DisplayName("유저 생성")
    void createAccount() {
        Member saveMember = memberService.save(fixtureM.fixtureA());
        Member findMember = memberService.findById(saveMember.getId());
        log.info("createdMember={}", findMember);
        assertThat(findMember).isEqualTo(saveMember);
    }


    @Test
    @DisplayName("이름으로 회원 리스트 조회")
    void findMemberA(){
        Member saveMemberA = memberService.save(fixtureM.fixtureA());
        Member saveMemberAD = memberService.save(fixtureM.fixtureAD());

        List<Member> names = memberService.findMemberWithName(saveMemberA.getUserName());
        assertSame(saveMemberA.getUserName(), saveMemberAD.getUserName());
        assertEquals(names.size(), 2);
    }

    @Test
    @DisplayName("findByName 중 번호로 회원 조회")
    void findMemberB(){
        Member saveMemberA = memberService.save(fixtureM.fixtureA());
        Member saveMemberAD = memberService.save(fixtureM.fixtureAD());

        List<Member> names = memberService.findMemberWithName(saveMemberA.getUserName());
        Member result = memberService.findMemberWithPhoneNumber(names, saveMemberAD.getPhoneNumber());
        
        assertSame(saveMemberA.getUserName(), saveMemberAD.getUserName());
        assertNotEquals(saveMemberA.getPhoneNumber(), saveMemberAD.getPhoneNumber());

    }
    
    
    @Test
    @DisplayName("회원 삭제(soft delete) 플로우")
    void deleteMemberWithFindAll(){
        Member saveMemberA = memberService.save(fixtureM.fixtureA());

        memberService.deleteMember(saveMemberA.getId());
        MemberStatus status = memberService.findById(saveMemberA.getId()).getStatus();
        assertEquals(status, MemberStatus.DELETED);
    }

    @Test
    @DisplayName("회원 정보 변경")
    void updateMemberInfo(){
        Member saveMemberB = memberService.save(fixtureM.fixtureB());

        MemberUpdateRequest update = MemberUpdateRequest.builder()
                                            .userName("new_name")
                                            .phoneNumber("56785678")
                                            .status(MemberStatus.ACTIVE)
                                            .build();

        memberService.updateMemberInfo(saveMemberB.getId(),update);

        assertNotEquals(saveMemberB.getUserName(), "new_name");
    }
    
}
