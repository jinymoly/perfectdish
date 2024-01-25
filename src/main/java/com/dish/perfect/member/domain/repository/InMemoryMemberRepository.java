package com.dish.perfect.member.domain.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequest;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> memberMap = new ConcurrentHashMap<>();

    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Member save(MemberRequest memberRequestDto) {
        Member member = Member.builder()
                .id(getNextId())
                .userName(memberRequestDto.getUserName())
                .phoneNumber(memberRequestDto.getPhoneNumber())
                .status(memberRequestDto.getStatus())
                .createAt(LocalDateTime.now())
                .build();
        memberMap.put(member.getId(), member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return memberMap.get(id);
    }

    @Override
    public List<Member> findMembersBySameLastFourDigits(String phoneNumber) {
        List<Member> fourdigitsDuplicatedMember = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (extractLastFourDigits(member.getPhoneNumber()).equals(extractLastFourDigits(phoneNumber))) {
                fourdigitsDuplicatedMember.add(member);
            }
        }
        return fourdigitsDuplicatedMember;
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
    public Long getNextId() {
        return idSequence.incrementAndGet();
    }
    
    @Override
    public String extractLastFourDigits(String phoneNumber) {
        return phoneNumber.substring(4, 8);
    }
    
    @Override
    public void clear() {
        memberMap.clear();
    }
}
