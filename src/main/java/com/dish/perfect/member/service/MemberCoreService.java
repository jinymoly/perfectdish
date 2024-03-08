package com.dish.perfect.member.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.request.MemberChangeStatusRequest;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCoreService {

    private final MemberRepository memberRepository;
    
    public void join(Member member){
        if(member.isNewPhoneNumber(member.getPhoneNumber())){
            memberRepository.save(member);
        } throw new GlobalException(ErrorCode.FAIL_CREATE_MEMBER, "회원 생성에 실패하였습니다.");
    }

    public void updateMemberInfo(final Long memberId, @Valid MemberUpdateRequest updateRequest){
        Member member = findMemberById(memberId);
        if(member.isNewUserName(updateRequest.getUserName())){
            validPhoneNumberDuplicated(updateRequest.getPhoneNumber());
        }
            member.updateMemberInfo(updateRequest.getUserName(), 
                                    updateRequest.getPhoneNumber(), 
                                    LocalDateTime.now());
    }

    public void deleteMemberByStatus(final Long memberId, MemberChangeStatusRequest statusRequest){
        Member member = findMemberById(memberId);
        if(!member.getStatus().equals(MemberStatus.DELETED)){
            member.updateMemberStatus(statusRequest.getStatus(), LocalDateTime.now());
        }
        new GlobalException(ErrorCode.ALREADY_DELETED_MEMBER, "이미 탈퇴한 사용자입니다.");
    }
    

    public Member findMemberById(Long memberId){
        return memberRepository.findById(memberId)
                                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER, memberId + "해당 회원이 존재하지 않습니다."));
    }

    private void validPhoneNumberDuplicated(String phoneNumber){
        if(memberRepository.existsByPhoneNumber(phoneNumber)){
            throw new GlobalException(ErrorCode.DUPLICATED_MEMBER_INFO, "해당 폰번호가 이미 존재합니다.");
        }
    }
    
}
