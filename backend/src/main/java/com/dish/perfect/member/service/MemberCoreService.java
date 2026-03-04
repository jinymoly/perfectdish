package com.dish.perfect.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.domain.repository.MemberRepository;
import com.dish.perfect.member.dto.request.MemberRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCoreService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(MemberRequest memberRequest) {
        Member member = memberRequest.toMemberEntity();
        member.changePassword(passwordEncoder.encode(member.getPassword()));
        validPhoneNumberDuplicatedByMember(member);
        Member savedMember = memberRepository.save(member);
        log.info("saved member :{}/{} {}", savedMember.getId(), savedMember.getUserName(),
                savedMember.getPhoneNumber());
        return savedMember;
    }

    private void validPhoneNumberDuplicatedByMember(Member member) {
        if (memberRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent()) {
            throw new GlobalException(ErrorCode.DUPLICATED_MEMBER_INFO, "해당 번호의 회원이 이미 존재합니다.");
        }
    }

}
