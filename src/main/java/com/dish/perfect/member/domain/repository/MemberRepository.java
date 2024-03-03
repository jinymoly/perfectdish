package com.dish.perfect.member.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.member.domain.Member;

public interface MemberRepository {

    Member save(Member member);
    void update(Long id, Member member);

    List<Member> findAll();
    Member findById(Long id);
    List<Member> findMembersBySameLastFourDigits(String phoneNumber);

    List<Member> findByName(String name);

    Optional<Member> findByPhoneNumberOp(List<Member> members, String phoneNumber);
    Member findByPhoneNumberWithList(List<Member> members, String phoneNumber);
    Optional<Member> findMemberByPhoneNumber(String phoneNumber);

    String extractLastFourDigits(String phoneNumber);
    Long getNextId();

    // soft delete
    void deleteMember(Member member);
    
    void clear();
}
