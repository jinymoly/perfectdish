package com.dish.perfect.member.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberRepository {

    private static Map<Long, Member> accountMap = new HashMap<>();
    private static long idSequence = 0L;

    public Member save(Member member) {
        member.setId(++idSequence);
        log.info("save : member={}", member);
        accountMap.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return accountMap.get(id);
    }
    /**
     * 폰번호 4자리로 찾은 member(중복 가능성)
     * 
     * @param phoneNumber
     * @return
     */
    public List<Member> findByphoneNum(String phoneNumber) {
        List<Member> usersWithDuplicateNumber = new ArrayList<>();

        String digits = extractLastFourDigits(phoneNumber);

        for (Member member : findAll()) {
            if (extractLastFourDigits(member.getPhoneNumber()).equals(digits)) {
                usersWithDuplicateNumber.add(member);
            }
        }
        log.info("findByphoneNum : result={}", usersWithDuplicateNumber.toString());
        return usersWithDuplicateNumber;

    }

    public List<Member> findAll() {
        return new ArrayList<>(accountMap.values()); // account 객체 조작 방지
    }

    /**
     * 폰 번호 마지막 4자리 반환
     * 
     * @param phoneNumber
     * @return
     */
    public String extractLastFourDigits(String phoneNumber) {
        return phoneNumber.substring(4, 8);
    }

    /**
     * memberList에서 이름이 같은 member 추출하기
     * 
     * @param members
     * @param name
     * @return
     */
    public Optional<Member> findByName(List<Member> members, String name) {
        return members.stream()
                .filter(m -> m.getUserName().equals(name))
                .findFirst();
    }

    public void clear() {
        accountMap.clear();
    }

}
