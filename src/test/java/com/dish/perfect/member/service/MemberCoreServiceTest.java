package com.dish.perfect.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.member.MemberFixture;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.request.MemberChangeStatusRequest;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;
import com.dish.perfect.member.dto.response.MemberDetailResponse;
import com.dish.perfect.member.dto.response.MemberCommonResponse;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class MemberCoreServiceTest {

    @Autowired
    private MemberCoreService memberCoreService;

    @Autowired
    private MemberPresentationService memberPresentationService;

    @Autowired
    private MemberRepository memberRepository;

    private MemberFixture memberFixture = new MemberFixture();

    // TO-DO
    // springBoot Test
    // testContainer -> serviceTest

    @Test
    @DisplayName("Member 객체 생성")
    void createMember() {
        MemberRequest memberA1 = memberFixture.fixtureA();
        MemberRequest memberA2 = memberFixture.fixtureAD();
        memberCoreService.join(memberA1);
        memberCoreService.join(memberA2);

        List<MemberCommonResponse> findAll = memberPresentationService.findAllWithActive();
        assertEquals(findAll.size(), 2);
    }

    @Test
    @DisplayName("duplicate phoneNumber 오류")
    void failByDuplicatedPhoneNumber() {
        MemberRequest memberB1 = memberFixture.fixtureB();
        MemberRequest memberB2 = memberFixture.fixtureBD_PN();

        memberCoreService.join(memberB1);

        assertEquals(memberB1.getPhoneNumber(), memberB2.getPhoneNumber());
        assertThrows(GlobalException.class, () -> memberCoreService.join(memberB2));

    }

    @Test
    @DisplayName("phonenumber로 조회")
    void findByphoneNumber() {
        MemberRequest memberC = memberFixture.fixtureC();
        memberCoreService.join(memberC);

        memberRepository.findByPhoneNumber(memberC.getPhoneNumber());
        MemberDetailResponse findByPhoneNumber = memberPresentationService.findByPhoneNumber(memberC.getPhoneNumber());
        assertEquals(memberC.getPhoneNumber(), findByPhoneNumber.getPhoneNumber());
    }

    @Test
    @DisplayName("MemberInfo를 update")
    void updateMemberInfo() {
        MemberRequest memberD = memberFixture.fixtureD();
        Member joinD = memberCoreService.join(memberD);
        MemberUpdateRequest updateInfo = MemberUpdateRequest.builder()
                .userName("새로운 이름")
                .phoneNumber("95959595")
                .build();

        Member fMember = memberRepository.findByPhoneNumber(memberD.getPhoneNumber());
        Long update = memberCoreService.updateMemberInfo(fMember.getId(), updateInfo);

        assertTrue(joinD.getId().equals(update));
        assertTrue(joinD.getPhoneNumber().equals("95959595"));

    }

    @Test
    @DisplayName("Member 삭제(상태 변경)")
    void deleteMemberButUpdateStatus() {
        MemberRequest memberE = memberFixture.fixtureE();
        Member joinE = memberCoreService.join(memberE);
        MemberChangeStatusRequest deleteInfo = MemberChangeStatusRequest.builder()
                .status(MemberStatus.DELETED)
                .build();
        Long deletedMemberId = memberCoreService.deleteMemberByStatus(joinE.getId(), deleteInfo);
        assertEquals(joinE.getId(), deletedMemberId);
        Member result = memberPresentationService.findById(deletedMemberId);
        assertTrue(result.getStatus().equals(MemberStatus.DELETED));
    }

    @Test
    @DisplayName("userName 으로 List 조회")
    void findByName() {
        MemberRequest memberE = memberFixture.fixtureA();
        MemberRequest memberF = memberFixture.fixtureAD();
        memberCoreService.join(memberE);
        memberCoreService.join(memberF);
        List<MemberCommonResponse> result = memberPresentationService.findByUserName(memberE.getUserName());

        assertEquals(result.size(), 2);
    }

}
