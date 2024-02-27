package com.dish.perfect.member.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;

public interface MemberRepository {

    Member save(MemberRequest memberRequestDto);
    void update(Long id, MemberUpdateRequest memberRequestDto);

    List<Member> findAll();
    Member findById(Long id);
    List<Member> findMembersBySameLastFourDigits(String phoneNumber);

    List<Member> findByName(String name);

    Optional<Member> findByPhoneNumberOp(List<Member> members, String phoneNumber);
    Member findByPhoneNumberWithList(List<Member> members, String phoneNumber);
    //List<Member> findByPhoneNumberByList(String phoneNumber);

    String extractLastFourDigits(String phoneNumber);
    Long getNextId();

    // soft delete
    void deleteMember(Member member);
    
    void clear();
}
