package com.dish.perfect.member.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequest;

public interface MemberRepository {

    Member save(MemberRequest memberRequestDto);

    List<Member> findAll();
    Member findById(Long id);
    List<Member> findMembersBySameLastFourDigits(String phoneNumber);
    Optional<Member> findByName(List<Member> members, String name);

    String extractLastFourDigits(String phoneNumber);
    Long getNextId();
    
    void clear();
}
