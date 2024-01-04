package com.dish.perfect.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequestDto;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @AfterEach
    void clearServiceTest() {
        memberService.clear();
    }

    @Test
    @DisplayName("유저 생성")
    void createAccount() {
        Member saveMember = fixtureA();
        Member findMember = memberService.findById(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    @DisplayName("휴대폰 번호 뒷자리 4개 출력 확인")
    void extractFourDigits() {
        Member saveMember = fixtureA();
        String fourDigits = memberService.extractLastFourDigits(saveMember.getPhoneNumber());

        log.info("digits={}", fourDigits);

        assertThat(fourDigits).isEqualTo("3333");

    }

    @Test
    @DisplayName("뒷번호 4자리로 중복 유저")
    void findByPhoneNumber() {
        Member saveMemberA = fixtureA();
        Member saveMemberB = fixtureB();

        List<Member> findByphoneNum = memberService.findByphoneNum(saveMemberA.getPhoneNumber());
        log.info("findMember={}", findByphoneNum.toString());

        assertThat(findByphoneNum).contains(saveMemberA, saveMemberB);
    }

    @Test
    @DisplayName("폰 번호로 확인된 유저들 중 이름이 같은 한 유저")
    void findByNameInList() {
        Member saveMemberA = fixtureA();
        Member saveMemberB = fixtureB();

        List<Member> findByphoneNum = memberService.findByphoneNum(saveMemberA.getPhoneNumber());
        Optional<Member> expectMember = memberService.findByName(findByphoneNum, "김가가");
        log.info("expectMember={}", expectMember.toString());
        
        assertThat(expectMember).contains(saveMemberB);
    }

    private Member fixtureA() {
        MemberRequestDto memberDto = MemberRequestDto.builder()
                                                    .userName("이나나")
                                                    .phoneNumber("22223333")
                                                    .build();
        Member saveMember = memberService.save(memberDto);
        return saveMember;
    }

    private Member fixtureB() {
        MemberRequestDto memberDto = MemberRequestDto.builder()
                                                    .userName("김가가")
                                                    .phoneNumber("22223333")
                                                    .build();
        Member saveMember = memberService.save(memberDto);
        return saveMember;
    }
}
