package com.dish.perfect.member.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.member.MemberFixture;
import com.dish.perfect.member.domain.Member;

public class MemberResponseTest {
    
    private MemberFixture fixtureM = new MemberFixture();

    @Test
    @DisplayName("Member -> memberResponse 생성 플로우")
    void testFromResponse() {
        Member member = fixtureM.fixtureMA();

        MemberResponse response = MemberResponse.fromResponse(member);

        Assertions.assertThat(response.getUserName()).isEqualTo(member.getUserName());
    }
}
