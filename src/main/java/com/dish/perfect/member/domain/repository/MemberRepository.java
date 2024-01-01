package com.dish.perfect.member.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dish.perfect.member.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberRepository {
    
    private final Map<Long, Member> account = new HashMap<>();
    private long idNum = 0L;

    public Member save(Member member){
        member.setId(++idNum);
        log.info("save:user={}", member);
        account.put(member.getId(), member);
        return member;
    }

    public Optional<Member> findByphoneNumAndUserName(String phoneNumber, String name){
        return findAll().stream()
                        .filter(m -> m.getPhoneNumber().equals(phoneNumber) && m.getUserName().equals(name) )
                        .findFirst();
    }

    private List<Member> findAll() {
        return new ArrayList<>(account.values()); // account 객체 조작 방지 
    }
    
}
