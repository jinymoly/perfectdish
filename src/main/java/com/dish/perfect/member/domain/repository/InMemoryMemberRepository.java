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
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> memberMap = new ConcurrentHashMap<>();

    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Member save(MemberRequest memberRequestDto) {
        String savedMemName = getMemberNameFromMap();
        String savedPhoneNum = getMemberPhoneNumberFromMap();

        if (memberRequestDto.getUserName().equals(savedMemName)
                && memberRequestDto.getPhoneNumber().equals(savedPhoneNum)) {
            throw new GlobalException(ErrorCode.DUPLICATED_MEMBER, "이미 존재하는 회원입니다.");
        } else {
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
        String savedphoneNumber = getMemberPhoneNumberFromMap();
        if (savedphoneNumber != null && savedphoneNumber.equals(phoneNumber)) {
            return memberMap.values().stream()
                    .filter(m -> m.getPhoneNumber().equals(phoneNumber))
                    .findFirst();
        } else {
            return Optional.empty();
        }
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
    public void update(Long id, MemberUpdateRequest memberRequestDto) {
        Member findById = findById(id);
        if(memberMap.containsValue(findById)){

            Member member = Member.builder()
            .id(findById.getId())
            .userName(memberRequestDto.getUserName())
            .phoneNumber(memberRequestDto.getPhoneNumber())
            .status(MemberStatus.ACTIVE)
            .build();
            memberMap.put(findById.getId(), member);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER, "해당 회원이 존재하지 않습니다.");
        }
    }

    @Override
    public void deleteMember(Member member) {
        Long id = member.getId();
        Member findById = findById(id);
        if(memberMap.containsValue(findById)){
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

    /**
     * memberMap에서 value의 userName
     * 
     * @return
     */
    private String getMemberNameFromMap() {
        for (Map.Entry<Long, Member> entry : memberMap.entrySet()) {
            Member member = entry.getValue();
            return member.getUserName();
        }
        return null;
    }

    /**
     * memberMap에서 value의 phoneNumber
     * 
     * @return
     */
    private String getMemberPhoneNumberFromMap() {
        for (Map.Entry<Long, Member> entry : memberMap.entrySet()) {
            Member member = entry.getValue();
            return member.getPhoneNumber();
        }
        return null;
    }
}
