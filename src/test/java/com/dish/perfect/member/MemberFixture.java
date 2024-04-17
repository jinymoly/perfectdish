package com.dish.perfect.member;

import java.time.LocalDateTime;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.dto.request.MemberRequest;

public class MemberFixture {

    public Member fixtureMA(){
        return Member.builder()
                    .id(1L)
                    .userName("userA")
                    .phoneNumber("77768887")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
    }

    public Member fixtureMA_D(){
        return Member.builder()
                    .id(2L)
                    .userName("userA")
                    .phoneNumber("44454446")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
    }

    public Member fixtureMB(){
        return Member.builder()
                    .id(3L)
                    .userName("userB")
                    .phoneNumber("55545553")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
    }

    public Member fixtureMC(){
        return Member.builder()
                    .id(4L)
                    .userName("userC")
                    .phoneNumber("33323331")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
    }

    public Member fixtureMD(){
        return Member.builder()
                    .id(5L)
                    .userName("userD")
                    .phoneNumber("99989997")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
    }


    public MemberRequest fixtureA() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("이나나")
                .phoneNumber("22223333")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureAD() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("이나나")
                .phoneNumber("11112222")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureB() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("김가가")
                .phoneNumber("99998888")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureBD_PN() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("홍이나")
                .phoneNumber("99998888")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureC() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("유리")
                .phoneNumber("66667777")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureD() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("박다다")
                .phoneNumber("33334444")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureE() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("최라라")
                .phoneNumber("44445555")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }

    public MemberRequest fixtureF() {
        MemberRequest memberDto = MemberRequest.builder()
                .userName("김마마")
                .phoneNumber("55556666")
                .status(MemberStatus.ACTIVE)
                .build();
        return memberDto;
    }


}
