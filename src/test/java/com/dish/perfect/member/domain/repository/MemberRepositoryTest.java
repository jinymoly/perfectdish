package com.dish.perfect.member.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.dto.request.MemberRequest;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
public class MemberRepositoryTest {
    
    @Autowired
    private InMemoryMemberRepository memberRepository;


    @Test
    @DisplayName("유저 등록시 status가 ACTIVE")
    void save(){
        Member member = fixtureA();
        assertEquals(MemberStatus.ACTIVE, member.getStatus());
    }

    @Test
    @DisplayName("회원 조회 흐름 - filter : 번호 -> filter : 이름")
    void findMember(){
        Member memberA = fixtureA();
        Member memberB = memberRepository.save(fixtureB());
        List<Member> allMem = memberRepository.findAll();
        for(Member m : allMem){
            log.info("member={}", m.getUserName());
        }

        String digits = memberRepository.extractLastFourDigits(memberB.getPhoneNumber());
        log.info("끝 번호 네 자리={}", digits);
        List<Member> memberList = memberRepository.findMembersBySameLastFourDigits(digits);
        Optional<Member> findOne = memberRepository.findByName(memberList, memberB.getUserName());
        log.info("findMember={}", findOne);
        
        assertEquals("이나나", memberB.getUserName());
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteMember(){
        MemberRequest newMem = fixtureC();
        memberRepository.save(newMem);

        Member findMember = memberRepository.findByName(newMem.getUserName()).orElseThrow(()-> new NoSuchElementException("해당 유저가 존재하지 않음!"));
        log.info("findMember={}", findMember.getUserName());

        MemberRequest request = MemberRequest.builder()
                                            .userName(findMember.getUserName())
                                            .phoneNumber(findMember.getPhoneNumber())
                                            .status(MemberStatus.DELETED)
                                            .build();

        Member deleteMember = memberRepository.deleteMember(request);
        log.info("삭제 완료={}/{}", deleteMember.getUserName(), deleteMember.getStatus());

        assertEquals(MemberStatus.DELETED, deleteMember.getStatus());
    }

    @Test
    @DisplayName("회원 삭제 에러")
    void deleteError(){
        MemberRequest newMem = fixtureC();
        memberRepository.save(newMem);

        assertThrows(NoSuchElementException.class,
                        ()-> {memberRepository.findByName("잘못된 이름")
                                .orElseThrow(()-> new NoSuchElementException("해당 유저가 존재하지 않음!"));
                            }
                    ); 
    }


    private Member fixtureA() {
        MemberRequest memberDto = MemberRequest.builder()
                                                    .userName("김가가")
                                                    .phoneNumber("22223333")
                                                    .status(MemberStatus.ACTIVE)
                                                    .build();
        Member saveMember = memberRepository.save(memberDto);
        return saveMember;
    }
    
    private MemberRequest fixtureB() {
        MemberRequest memberDto = MemberRequest.builder()
                                                    .userName("이나나")
                                                    .phoneNumber("22223333")
                                                    .status(MemberStatus.ACTIVE)
                                                    .build();
        return memberDto;
    }

    private MemberRequest fixtureC() {
        MemberRequest memberDto = MemberRequest.builder()
                                                    .userName("유리")
                                                    .phoneNumber("66667777")
                                                    .status(MemberStatus.ACTIVE)
                                                    .build();
        return memberDto;
    }

}
