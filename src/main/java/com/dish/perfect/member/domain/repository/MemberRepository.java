package com.dish.perfect.member.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dish.perfect.member.domain.Member;

@Repository
public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);

    List<Member> findByphoneNum(String phoneNumber);

    List<Member> findAll();

    Optional<Member> findByName(List<Member> members, String name);

    String extractLastFourDigits(String phoneNumber);

    void clear();

}
