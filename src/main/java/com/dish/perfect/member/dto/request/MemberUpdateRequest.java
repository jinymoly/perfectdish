package com.dish.perfect.member.dto.request;

import com.dish.perfect.member.domain.MemberStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {


    private Long id;

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Size(min = 8, max = 8, message = "010을 제외한 나머지 8자리만 입력할 수 있습니다.")
    private String phoneNumber;

    @NotBlank(message = "이름은 필수입니다.")
    private String userName;

    private MemberStatus status;

    @Builder
    private MemberUpdateRequest(Long id, String phoneNumber, String userName, MemberStatus status) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.status = status;
    }

}
