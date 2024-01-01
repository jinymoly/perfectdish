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

    public Member save(Member member) {
        member.setId(++idNum);
        log.info("save:user={}", member);
        account.put(member.getId(), member);
        return member;
    }

    /**
     * 폰번호 4자리로 찾은 member(중복 가능성)
     * 
     * @param phoneNumber
     * @return
     */
    public List<Member> findByphoneNum(String phoneNumber) {
        List<Member> resultList = new ArrayList<>();

        String digits = extractLastFourDigits(phoneNumber);

        for (Member member : findAll()) {
            if (extractLastFourDigits(member.getPhoneNumber()).equals(digits)) {
                resultList.add(member);
            }
        }
        return resultList;

    }

    public List<Member> findAll() {
        return new ArrayList<>(account.values()); // account 객체 조작 방지
    }

    /**
     * 폰 번호 마지막 4자리 반환
     * 
     * @param phoneNumber
     * @return
     */
    private String extractLastFourDigits(String phoneNumber) {
        return phoneNumber.substring(4, 8);
    }

    /**
     * findAll에서 이름이 같은 member 추출하기
     * 
     * @param members
     * @param name
     * @return
     */
    public Optional<Member> findByName(String name) {
        return findAll().stream()
                .filter(m -> m.getUserName().equals(name))
                .findFirst();
    }

}
