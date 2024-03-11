package com.dish.perfect.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.response.MemberResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPresentationService {
    
    final MemberRepository memberRepository;

    public MemberResponse getMemberInfo(final Long memberId){
        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원이 존재하지 않습니다."));
        return MemberResponse.fromResponse(member);
    }

    /**
     * 삭제(휴면)회원을 제외한 모든 회원 조회
     * @return
     */
    public List<MemberResponse> findAllWithActive(){
        return memberRepository.findAll()
                                .stream()
                                .filter(Member::isActive)
                                .map(MemberResponse::fromResponse)
                                .toList();
    }

    public List<Member> findMemberByName(String name){
        List<Member> findByName = memberRepository.findByName(name);
        if(findByName.isEmpty()){
            new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 이름의 회원이 존재하지 않습니다.");
        }
        return findByName;
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    public Member findByphoneNumber(String phoneNumber){
        return memberRepository.findByNumber(phoneNumber);
    }

}
