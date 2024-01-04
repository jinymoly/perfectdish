package com.dish.perfect.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.request.MemberRequestDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long getNextId() {
        return memberRepository.getNextId();
    }

    public Member save(MemberRequestDto memberDto) {
        Member member = memberRepository.save(memberDto);
        log.info("save : member={}", member);
        return member;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    /**
     * 폰번호 4자리로 찾은 member(중복 가능성)
     * 
     * @param phoneNumber
     * @return
     */
    public List<Member> findByphoneNum(String phoneNumber) {
        List<Member> usersWithDuplicateNumber = new ArrayList<>();

        String digits = extractLastFourDigits(phoneNumber);

        for (Member member : findAll()) {
            if (extractLastFourDigits(member.getPhoneNumber()).equals(digits)) {
                usersWithDuplicateNumber.add(member);
            }
        }
        log.info("findByphoneNum : result={}", usersWithDuplicateNumber.toString());
        return usersWithDuplicateNumber;

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
     * memberList에서 이름이 같은 member 추출하기
     * 
     * @param members
     * @param name
     * @return
     */
    public Optional<Member> findByName(List<Member> members, String name) {
        return members.stream()
                .filter(m -> m.getUserName().equals(name))
                .findFirst();
    }

    public void clear() {
        memberRepository.clear();
    }

}
