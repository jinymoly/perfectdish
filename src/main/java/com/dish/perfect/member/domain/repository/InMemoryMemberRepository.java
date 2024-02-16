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
import com.dish.perfect.member.domain.MemberStatus;
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

    // 01 - TODO : 이 둘의 성능 비교
    @Override
    public Optional<Member> findByName(List<Member> members, String name) {
        return members.stream()
                .filter(m -> m.getUserName().equals(name))
                .findFirst();
    }

    // 02 - TODO : 이 둘의 성능 비교
    @Override
    public Optional<Member> findByName(String name) {
        String resultName = getMemberNameFromMap();
        if(resultName != null && resultName.equals(name)){
            return memberMap.values().stream()
                                    .filter(m -> m.getUserName().equals(name))
                                    .findFirst();
        } else {
            return Optional.empty();
        }
        
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
    public Member update(MemberRequest memberRequestDto) {
        Member member = Member.builder()
                                .userName(memberRequestDto.getUserName())
                                .phoneNumber(memberRequestDto.getPhoneNumber())
                                .status(MemberStatus.ACTIVE)
                                .build();
        memberMap.put(member.getId(), member);
        return member;
    }

    @Override
    public Member deleteMember( MemberRequest memberRequest) {
            Long memberId = getIdFromMemberMap();
            Member member = memberMap.get(memberId);
            Member updateMember = Member.builder()
                                            .id(member.getId())
                                            .userName(memberRequest.getUserName())
                                            .phoneNumber(memberRequest.getPhoneNumber())
                                            .status(MemberStatus.DELETED)
                                            .build();
            memberMap.put(member.getId(), updateMember);
            return updateMember;
            
    }

    /**
     * memberMap에서 key(id)
     * @return
     */
    private Long getIdFromMemberMap(){
        if(!memberMap.isEmpty()){
            for(Map.Entry<Long, Member> entry : memberMap.entrySet()){
                Long id = entry.getKey();
                return id;
            }
        }
        return null;
    }

    /**
     * memberMap에서 value의 userName 
     * @return
     */
    private String getMemberNameFromMap(){
        for(Map.Entry<Long, Member> entry : memberMap.entrySet()){
            Member member = entry.getValue();
            return member.getUserName();
        }
        return null;
    }

    
}
