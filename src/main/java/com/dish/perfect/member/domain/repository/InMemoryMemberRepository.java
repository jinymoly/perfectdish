package com.dish.perfect.member.domain.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> memberMap = new ConcurrentHashMap<>();

    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Member save(Member member) {
        for (Member mem : memberMap.values()) {
            if (mem.getPhoneNumber().equals(member.getPhoneNumber())) {
                throw new GlobalException(ErrorCode.DUPLICATED_MEMBER, "이미 등록된 회원입니다.");
            }
        }
        Member newMem = Member.builder()
                                .id(getNextId())
                                .userName(member.getUserName())
                                .phoneNumber(member.getPhoneNumber())
                                .status(member.getStatus())
                                .createAt(LocalDateTime.now())
                                .build();
        memberMap.put(member.getId(), newMem);
        return newMem;
    }

    @Override
    public Member findById(Long id) {
        return memberMap.get(id);
    }

    @Override
    public List<Member> findMembersBySameLastFourDigits(String phoneNumber) {
        List<Member> fourdigitsDuplicatedMember = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (extractLastFourDigits(member.getPhoneNumber()).equals(phoneNumber)) {
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
    public List<Member> findByName(String name) {
        List<Member> mems = new ArrayList<>();
        if (memberMap != null) {
            for (Map.Entry<Long, Member> mem : memberMap.entrySet()) {
                String userName = mem.getValue().getUserName();
                if (userName.equals(name)) {
                    mems.add(mem.getValue());
                }
            }
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원이 존재하지 않습니다.");
        }
        return mems;
    }

    @Override
    public Optional<Member> findByPhoneNumberOp(List<Member> members, String phoneNumber) {
        for (Member member : members) {
            if (member.getPhoneNumber() != null && member.getPhoneNumber().equals(phoneNumber)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Member> findMemberByPhoneNumber(String phoneNumber) {
        for(Member member : memberMap.values()){
            if(member.getPhoneNumber().equals(phoneNumber)){
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    @Override
    public Member findByPhoneNumberWithList(List<Member> members, String phoneNumber) {
        for (Member mem : members) {
            if (mem.getPhoneNumber().equals(phoneNumber)) {
                return mem;
            }
        }
        throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원이 존재하지 않습니다.");
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

    @Override
    public void update(Long id, Member member) {
        Member findById = findById(id);
        if (memberMap.containsValue(findById)) {
            Member newMem = Member.builder()
                                    .id(findById.getId())
                                    .userName(member.getUserName())
                                    .phoneNumber(member.getPhoneNumber())
                                    .createAt(findById.getCreateAt())
                                    .status(MemberStatus.ACTIVE)
                                    .build();
            memberMap.put(findById.getId(), newMem);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원이 존재하지 않습니다.");
        }
    }

    @Override
    public void deleteMember(Member member) {
        Long id = member.getId();
        Member findById = findById(id);
        if (memberMap.containsValue(findById)) {
            Member deleteMember = Member.builder()
                                        .id(member.getId())
                                        .userName(member.getUserName())
                                        .phoneNumber(member.getPhoneNumber())
                                        .createAt(member.getCreateAt())
                                        .status(MemberStatus.DELETED).build();

            memberMap.put(id, deleteMember);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "삭제할 회원이 존재하지 않습니다.");
        }
    }

}
