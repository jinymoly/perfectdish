package com.dish.perfect.member.dto.request;

import com.dish.perfect.member.domain.MemberStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberChangeStatusRequest {
    
    private MemberStatus status;

    @Builder
    private MemberChangeStatusRequest(MemberStatus status){
        this.status = MemberStatus.DELETED;
    }
    
}
