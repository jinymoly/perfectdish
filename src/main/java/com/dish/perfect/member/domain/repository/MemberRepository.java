package com.dish.perfect.member.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequestDto;

public interface MemberRepository {

    Member save(MemberRequestDto memberRequestDto);

    Member findById(Long id);

    List<Member> findByphoneNum(String phoneNumber);

    List<Member> findAll();

    Optional<Member> findByName(List<Member> members, String name);

    String extractLastFourDigits(String phoneNumber);

    Long getNextId();
    
    void clear();


}
