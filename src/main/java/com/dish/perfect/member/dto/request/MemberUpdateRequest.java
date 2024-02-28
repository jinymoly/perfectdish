package com.dish.perfect.member.dto.request;

import com.dish.perfect.member.domain.MemberStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{8}$", message = "010을 제외한 나머지 8자리만 입력할 수 있습니다.")
    private String phoneNumber;

    @NotBlank(message = "이름은 필수입니다.")
    private String userName;

    private MemberStatus status;


    @Builder
    private MemberUpdateRequest(String phoneNumber, String userName, MemberStatus status) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.status = status;
    }

}
