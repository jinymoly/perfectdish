package com.dish.perfect.member.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.service.MemberCoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class MemberController {

    private final MemberCoreService memberCoreService;

    @GetMapping("/join")
    public String addMemberRequest() {
        return "memberRequest 페이지로 이동";
    }

    @PostMapping("/join")
    public ResponseEntity<Member> addMember(@RequestBody @Valid MemberRequest memberRequest) {
        log.info("Signup attempt for user: {}", memberRequest.getUserName());
        Member newMember = memberCoreService.join(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }
}
}
