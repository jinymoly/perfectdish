package com.dish.perfect.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.response.MemberDetailResponse;
import com.dish.perfect.member.dto.response.MemberResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPresentationService {
    
    private final MemberRepository memberRepository;

    public MemberDetailResponse getMemberInfo(final Long memberId){
        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원이 존재하지 않습니다."));
        return MemberDetailResponse.fromResponse(member);
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

    public List<MemberResponse> findAll(){
        return memberRepository.findAll().stream().map(MemberResponse::fromResponse).toList();
    }

    public List<MemberResponse> findByUserName(String userName){
        List<Member> findMembers = memberRepository.findByUserName(userName);
        List<MemberResponse> result = findMembers.stream()
                                                .map(MemberResponse::fromResponse)
                                                .toList();
        if(result.isEmpty()){
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 이름의 회원이 존재하지 않습니다.");
        } else{
            return result;
        }
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    public MemberDetailResponse findByPhoneNumber(String phoneNumber){
        Member findMember = memberRepository.findByPhoneNumber(phoneNumber);
        return MemberDetailResponse.fromResponse(findMember);
    }

}
