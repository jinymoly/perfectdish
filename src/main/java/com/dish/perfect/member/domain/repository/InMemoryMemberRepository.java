package com.dish.perfect.member.domain.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequestDto;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> memberMap = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Member save(MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setId(getNextId());
        member.setUserName(memberRequestDto.getUserName());
        member.setPhoneNumber(memberRequestDto.getPhoneNumber());
        member.setCreateAt(LocalDateTime.now());
        memberMap.put(member.getId(), member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return memberMap.get(id);
    }

    @Override
    public List<Member> findByphoneNum(String phoneNumber) {
        List<Member> result = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (member.getPhoneNumber().equals(phoneNumber)) {
                result.add(member);
            }
        }
        return result;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberMap.values());
    }

    @Override
    public Optional<Member> findByName(List<Member> members, String name) {
        return members.stream()
                .filter(m -> m.getUserName().equals(name))
                .findFirst();
    }

    @Override
    public void clear() {
        memberMap.clear();
    }

    @Override
    public Long getNextId() {
        return idSequence.incrementAndGet();
    }

    @Override
    public String extractLastFourDigits(String phoneNumber) {
        return phoneNumber.substring(4, 8);

    }

}
