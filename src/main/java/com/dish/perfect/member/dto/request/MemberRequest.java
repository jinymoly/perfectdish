package com.dish.perfect.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRequest {

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Size(max = 8, message = "010을 제외한 나머지 8자리만 입력할 수 있습니다.")
    private final String phoneNumber;

    @NotBlank(message = "이름은 필수입니다.")
    private final String userName;

    @Builder
    private MemberRequest(String phoneNumber, String userName) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

}
