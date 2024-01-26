package com.dish.perfect.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.request.MemberRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long getNextId() {
        return memberRepository.getNextId();
    }

    public Member save(MemberRequest memberDto) {
        Member member = memberRepository.save(memberDto);
        log.info("save : {}", member);
        return member;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    /**
     * 폰번호 뒤 4자리로 찾은 member(중복 가능성)
     * 
     * @param phoneNumber
     * @return
     */
    public List<Member> findByphoneNum(String phoneNumber) {
        List<Member> membersWithDuplicateNumber = memberRepository.findMembersBySameLastFourDigits(phoneNumber);
        log.info("findByphoneNum : result={}", membersWithDuplicateNumber.toString());
        return membersWithDuplicateNumber;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 폰 번호 마지막 4자리 반환
     * 
     * @param phoneNumber
     * @return
     */
    public String extractLastFourDigits(String phoneNumber) {
        return memberRepository.extractLastFourDigits(phoneNumber);
    }

    /**
     * 번호 같은 memberList에서 이름으로 member 추출하기
     * 
     * @param members
     * @param name
     * @return
     */
    public Optional<Member> findByName(List<Member> members, String name) {
        return memberRepository.findByName(members, name);
    }

    public void clear() {
        memberRepository.clear();
    }

}
