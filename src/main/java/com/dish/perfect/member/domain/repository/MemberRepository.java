package com.dish.perfect.member.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dish.perfect.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.userName = :userName")
    List<Member> findByName(String userName);

    @Query("select m from Member m where m.phoneNumber = :phoneNumber")
    Member findByNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

}
