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
import com.dish.perfect.member.dto.request.MemberUpdateRequest;
import com.dish.perfect.member.dto.response.MemberResponse;


@SpringBootTest
@Transactional
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
        Member memberA1 = memberFixture.fixtureA().toEntity();
        Member memberA2 = memberFixture.fixtureAD().toEntity();
        memberCoreService.join(memberA1);
        memberCoreService.join(memberA2);

        List<MemberResponse> findAll = memberPresentationService.findAllWithActive();
        assertEquals(findAll.size(), 2);
    }

    @Test
    @DisplayName("duplicate phoneNumber 오류")
    void failByDuplicatedPhoneNumber() {
        Member memberB1 = memberFixture.fixtureB().toEntity();
        Member memberB2 = memberFixture.fixtureBD_PN().toEntity();

        memberCoreService.join(memberB1);

        assertEquals(memberB1.getPhoneNumber(), memberB2.getPhoneNumber());
        assertThrows(GlobalException.class, () -> memberCoreService.join(memberB2));

    }

    @Test
    @DisplayName("phonenumber로 조회")
    void findByphoneNumber() {
        Member memberC = memberFixture.fixtureC().toEntity();
        memberCoreService.join(memberC);

        Member result = memberRepository.findByNumber(memberC.getPhoneNumber());
        assertEquals(memberC.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    @DisplayName("MemberInfo 수정")
    void updateMemberInfo() {
        Member memberD = memberFixture.fixtureD().toEntity();
        memberCoreService.join(memberD);
        MemberUpdateRequest updateInfo = MemberUpdateRequest.builder()
                .userName("새로운 이름")
                .phoneNumber("95959595")
                .build();

        Member fMember = memberRepository.findByNumber(memberD.getPhoneNumber());
        Long update = memberCoreService.updateMemberInfo(fMember.getId(), updateInfo);

        assertTrue(memberD.getId().equals(update));
        assertTrue(memberD.getPhoneNumber().equals("95959595"));

    }

    @Test
    @DisplayName("Member 삭제(상태 변경)")
    void deleteMemberButUpdateStatus() {
        Member memberE = memberFixture.fixtureE().toEntity();
        memberCoreService.join(memberE);
        MemberChangeStatusRequest deleteInfo = MemberChangeStatusRequest.builder()
                .status(MemberStatus.DELETED)
                .build();
        Long deletedMemberId = memberCoreService.deleteMemberByStatus(memberE.getId(), deleteInfo);
        assertEquals(memberE.getId(), deletedMemberId);
        Member result = memberPresentationService.findById(deletedMemberId);
        assertTrue(result.getStatus().equals(MemberStatus.DELETED));
    }

    @Test
    @DisplayName("userName 으로 List 조회")
    void findByName() {
        Member memberE = memberFixture.fixtureA().toEntity();
        Member memberF = memberFixture.fixtureAD().toEntity();
        memberCoreService.join(memberE);
        memberCoreService.join(memberF);
        List<Member> result = memberPresentationService.findMemberByName(memberE.getUserName());

        assertEquals(result.size(), 2);
    }

}
