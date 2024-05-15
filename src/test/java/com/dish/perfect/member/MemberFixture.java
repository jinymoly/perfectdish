package com.dish.perfect.member;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.dto.request.MemberRequest;

public class MemberFixture {

    public Member fixtureMA(){
        return Member.builder()
                    .userName("userA")
                    .phoneNumber("77768887")
                    .status(MemberStatus.ACTIVE)
                    .build();
    }

    public Member fixtureMA_D(){
        return Member.builder()
                    .userName("userA")
                    .phoneNumber("44454446")
                    .status(MemberStatus.ACTIVE)
                    .build();
    }

    public Member fixtureMB(){
        return Member.builder()
                    .userName("userB")
                    .phoneNumber("55545553")
                    .status(MemberStatus.ACTIVE)
                    .build();
    }

    public Member fixtureMC(){
        return Member.builder()
                    .userName("userC")
                    .phoneNumber("33323331")
                    .status(MemberStatus.ACTIVE)
                    .build();
    }

    public Member fixtureMD(){
        return Member.builder()
                    .userName("userD")
                    .phoneNumber("99989997")
                    .status(MemberStatus.ACTIVE)
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
