package com.dish.perfect.member.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequest;

public interface MemberRepository {

    Member save(MemberRequest memberRequestDto);
    Member update(MemberRequest memberRequestDto);

    List<Member> findAll();
    Member findById(Long id);
    List<Member> findMembersBySameLastFourDigits(String phoneNumber);

    // TODO 성능 비교 
    Optional<Member> findByName(List<Member> members, String name);
    Optional<Member> findByName(String name);

    String extractLastFourDigits(String phoneNumber);
    Long getNextId();

    // soft delete
    Member deleteMember(MemberRequest memberRequest);
    
    void clear();
}
