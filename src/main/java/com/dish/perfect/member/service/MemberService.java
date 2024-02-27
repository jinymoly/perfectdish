package com.dish.perfect.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;

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

    public List<Member> findAll() {
        if(memberRepository.findAll().isEmpty()){
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "회원 목록이 비어있습니다.");
        } else {
            return memberRepository.findAll();
        }
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    public void deleteMember(Long id) {
        if(id != null){
            Member member = memberRepository.findById(id);
            memberRepository.deleteMember(member);
            Member result = memberRepository.findById(member.getId());
            log.info("deleted complete:{}[{}]", result.getUserName(), result.getStatus());
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원을 찾을 수 없습니다.");
        }
    }

    public void updateMemberInfo(Long id, MemberUpdateRequest memberRequest){
            memberRepository.findById(id);
        if(memberRequest.getId().equals(id)){
            memberRepository.update(id, memberRequest);
            log.info("update complete:{}/{}", memberRequest.getUserName(), memberRequest.getPhoneNumber());
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원을 찾을 수 없습니다.");
        }
    }

    public void clear() {
        memberRepository.clear();
    }

}
