package com.dish.perfect.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();

    @AfterEach
    void clearTestRepository() {
        memberRepository.clear();
    }

    @Test
    @DisplayName("유저 생성")
    void createAccount() {
        Member saveMember = fixtureA();
        Member findMember = memberRepository.findById(saveMember.getId());

        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    @DisplayName("휴대폰 번호 뒷자리 4개 출력 확인")
    void extractFourDigits() {
        Member saveMember = fixtureA();
        String fourDigits = memberRepository.extractLastFourDigits(saveMember.getPhoneNumber());

        log.info("digits={}", fourDigits);

        assertThat(fourDigits).isEqualTo("3333");

    }

    @Test
    @DisplayName("뒷번호 4자리로 중복 유저")
    void findByPhoneNumber() {
        Member saveMemberA = fixtureA();
        Member saveMemberB = fixtureB();

        List<Member> findByphoneNum = memberRepository.findByphoneNum(saveMemberA.getPhoneNumber());
        log.info("findMember={}", findByphoneNum.toString());

        assertThat(findByphoneNum).contains(saveMemberA, saveMemberB);
    }

    @Test
    @DisplayName("폰 번호로 확인된 유저들 중 이름이 같은 한 유저")
    void findByNameInList() {
        Member saveMemberA = fixtureA();
        Member saveMemberB = fixtureB();

        List<Member> findByphoneNum = memberRepository.findByphoneNum(saveMemberA.getPhoneNumber());
        Optional<Member> expectMember = memberRepository.findByName(findByphoneNum, "김가가");
        log.info("expectMember={}", expectMember.toString());
        
        assertThat(expectMember).contains(saveMemberB);
    }

    private Member fixtureA() {
        Member member = new Member("이나나", "22223333");
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    private Member fixtureB() {
        Member member = new Member("김가가", "22223333");
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }
}
